package com.inter.enterprise.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Async;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ByteArrayEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.GetSpecifiedSequenceDao;
import com.inter.enterprise.dao.EnterprisePhysicalDistributionDao;
import com.inter.enterprise.dao.RegisterDeliveryInfoDao;
import com.inter.enterprise.service.EnterprisePhysicalDistributionService;
import com.inter.util.ResultMessageUtil;

import net.sf.json.JSONObject;

@Service
public class EnterprisePhysicalDistributionServiceImpl implements EnterprisePhysicalDistributionService {

	@Autowired
	private EnterprisePhysicalDistributionDao enterprisePhysicalDistributionDao;

	@Autowired
	private GetSpecifiedSequenceDao getSpecifiedSequenceDao;

	@Autowired
	private RegisterDeliveryInfoDao registerDeliveryInfoDao;

	@Autowired
	private ResultMessageUtil messageUtil;

	// all types
	private final String[] typeArr = { "WH", "RL", "SL", "TB", "DV", "DF", "LO", "UL", "PG", "UP" };

	// package related types
	private final String[] packageTypeArr = { "LO", "UL", "PG", "UP" };

	// types do not handle relationships
	private final String[] noRelationTypeArr = { "DV", "DF", "TB", "PG", "LO" };

	@Transactional
	public String physicalDistribution(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> appEnterpriseUser = enterprisePhysicalDistributionDao.queryAppEnterpriseUserByToken(param);

		Gson gson = new Gson();
		
		boolean isFMS = param.containsKey("FMS");

		if (appEnterpriseUser != null || isFMS) {
			int enterpriseUserKey = (Integer) appEnterpriseUser.get("enterprise_user_key");
			param.put("enterpriseUserKey", String.valueOf(enterpriseUserKey));
			String auth = (String) appEnterpriseUser.get("auth");

			if ("AU01".equals(auth) || "AU02".equals(auth) || isFMS) {
				String sequence = param.get("sequence");
				Map<String, Integer> orderMap = enterprisePhysicalDistributionDao.queryOrderBySequence(sequence);
				if (orderMap != null) {
					int orderNumber = orderMap.get("orderNumber");
					Map<String, String> bizServiceInfo = enterprisePhysicalDistributionDao
							.queryBizServiceInfo(orderNumber);
					if (bizServiceInfo != null) {
						param.put("bizNm", bizServiceInfo.get("bizNm"));
						param.put("svcNm", bizServiceInfo.get("svcNm"));
					}

					Map<String, String> appPhysicalDistributionTypeMap = enterprisePhysicalDistributionDao.queryAppPhysicalDistributionType(sequence);

					String typeParam = param.get("type");

					if (appPhysicalDistributionTypeMap != null) {

						String typeDb = appPhysicalDistributionTypeMap.get("type");

						if (!"PG".equals(typeParam) && typeDb.equals(typeParam)) {
							result.put("resultCode", 418);
							messageUtil.addResultMsg(param, result);
							return gson.toJson(result);
						}
					}

					if (Arrays.asList(typeArr).contains(typeParam)) {

						Map<String, Object> paramObj = new HashMap<String, Object>();
						paramObj.putAll(param);

						List<String> sequenceList = new ArrayList<String>();
						sequenceList.add(sequence);
						paramObj.put("sequenceList", sequenceList);

						try {
							// Delivery
							if ("DV".equals(typeParam)) {
								int deliveryId = getSpecifiedSequenceDao.getSpecifiedSequence("dv_id");
								param.put("deliveryId", String.valueOf(deliveryId));
								paramObj.put("deliveryId", String.valueOf(deliveryId));

								registerDeliveryInfoDao.insertDeliveryInfo(param);
								
								Map<String, Object> enterpriseInfo = registerDeliveryInfoDao.getEnterpriseInfo(param.get("enterpriseUserKey"));
								paramObj.putAll(enterpriseInfo);
								invokeBlockChain4Delivery(paramObj);

								result.put("deliveryId", deliveryId);
							} else if ("LO".equals(typeParam) || "PG".equals(typeParam)) {
								String childrenSequence = param.get("childrenSequence");
								String[] childSequenceArr = childrenSequence.split("-");

								for (String childSequence : childSequenceArr) {
									Map<String, Object> childSequenceMap = enterprisePhysicalDistributionDao
											.selectRelationBySequence(childSequence);
									if (childSequenceMap != null) {
										Map<String, Object> packMap = new HashMap<>();
//										paramObj.put("physicalDistributionKey", 0);
										// Logistics data for CURRENTLY PACKED SEQUENCE
										packMap.putAll(paramObj);
										packMap.put("type", "UP");
										
										Long currentlyPackedChildSeq = (Long) childSequenceMap.get("childSequence");
										packMap.put("singleSequence", currentlyPackedChildSeq);
										enterprisePhysicalDistributionDao.insertSingleAppPhysicalDistribution(packMap);
				
										// Logistics data for current parent of CURRENTLY PACKED SEQUENCE
										Long currentlyPackingParentSeq = (Long) childSequenceMap.get("parentSequence");
										packMap.put("singleSequence", currentlyPackingParentSeq);
										enterprisePhysicalDistributionDao.insertSingleAppPhysicalDistribution(packMap);
										
										// Remove current parent-child relationship
										packMap.put("sequence", currentlyPackedChildSeq);
										enterprisePhysicalDistributionDao.updateSequenceRelationByChildren(packMap);
									}
								}

								paramObj.put("childSequenceList", Arrays.asList(childSequenceArr));

								enterprisePhysicalDistributionDao.insertParentChildrenRelation(paramObj);
							}

							// Logistics and sequence status for the CURRENT SEQUENCE
							paramObj.put("physicalDistributionKey", 0);
							enterprisePhysicalDistributionDao.insertAppPhysicalDistribution(paramObj);
							enterprisePhysicalDistributionDao.updateSequenceStatus(paramObj);

							invokeBlockChain(paramObj, false);

							// Logistics and sequence status for all the children of the CURRENT SEQUENCE
							if (!("PG".equals(typeParam) || "LO".equals(typeParam))) {
								sequenceList = enterprisePhysicalDistributionDao.selectChildrenSequence(sequence);
								
								for (int i = 0; sequenceList != null && i < sequenceList.size(); i++) {
									paramObj.put("singleSequence", sequenceList.get(i));
									enterprisePhysicalDistributionDao.insertSingleAppPhysicalDistribution(paramObj);
									invokeBlockChain(paramObj, true);
								}
								
								if (sequenceList != null && sequenceList.size() > 0) {
									paramObj.put("sequenceList", sequenceList);
									enterprisePhysicalDistributionDao.updateSequenceStatus(paramObj);
								}
							} else {
								String childrenSequence = param.get("childrenSequence");
								String[] childSequenceArr = childrenSequence.split("-");
								
								for(int i=0; childSequenceArr!=null && i<childSequenceArr.length; i++) {
									
									paramObj.put("singleSequence", childSequenceArr[i]);
									enterprisePhysicalDistributionDao.insertSingleAppPhysicalDistribution(paramObj);
									invokeBlockChain(paramObj, true);
								}
								
								Map<String, Object> packMap = new HashMap<>();
								packMap.putAll(paramObj);
								packMap.put("sequenceList", Arrays.asList(childSequenceArr));
								enterprisePhysicalDistributionDao.updateSequenceStatus(packMap);
							}

							if ("UP".equals(typeParam)) {
								// Unpackage for the CURRENT SEQUENCE
								enterprisePhysicalDistributionDao.updateSequenceRelationByChildren(paramObj);

								// Unpackage for all the children of the CURRENT SEQUENCE
								if (sequenceList != null && sequenceList.size() > 0) {
									enterprisePhysicalDistributionDao.updateSequenceRelationByChildList(paramObj);
								}
							} else if (!Arrays.asList(noRelationTypeArr).contains(typeParam)) {
								// Unpackage for the CURRENT SEQUENCE
								enterprisePhysicalDistributionDao.updateSequenceRelationByChildren(paramObj);
							}

							String manufactureType = enterprisePhysicalDistributionDao.queryManufactureType(paramObj);
							if ("MF".equals(manufactureType) && "RL".equals(typeParam)) {
								enterprisePhysicalDistributionDao.updateSequenceDates(paramObj);
								if (sequenceList != null && sequenceList.size() > 0) {
									enterprisePhysicalDistributionDao.updateChildrenSequenceDate(paramObj);
								}
							}
							result.put("resultCode", 200);
						} catch (Exception e) {
							e.printStackTrace();
							result.put("resultCode", 500);
						}
					} else {
						result.put("resultCode", 419);
					}
				} else {
					result.put("resultCode", 405);
				}
			} else {
				result.put("resultCode", 401);
			}
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

	private String makeJsonString(Map<String, Object> paramObj, boolean isChild) {

		JSONObject jsonObj = new JSONObject();
		paramObj.put("isChild", isChild);
		Map<String, String> distInfo = enterprisePhysicalDistributionDao.getDistInfo(paramObj);

		jsonObj.put("dstrb_id", getString(paramObj.get("physicalDistributionKey")));
		if (isChild) {
			jsonObj.put("seq", getString(paramObj.get("singleSequence")));
		} else {
			jsonObj.put("seq", getString(paramObj.get("sequence")));
		}
		jsonObj.put("lat", getString(paramObj.get("latitude")));
		jsonObj.put("lng", getString(paramObj.get("longitude")));
		jsonObj.put("full_addr", getString(paramObj.get("fullAddress")));
		jsonObj.put("prv_nm", getString(paramObj.get("province")));
		jsonObj.put("city_nm", getString(paramObj.get("city")));
		jsonObj.put("dstr_nm", getString(paramObj.get("district")));
		String deliveryId = getString(paramObj.get("deliveryId"));
		jsonObj.put("dv_id", "".equals(deliveryId) ? "0" : deliveryId);
		jsonObj.put("dstrb_typ_cd", getString(paramObj.get("type")));
		jsonObj.put("dstrb_typ_nm", getString(distInfo.get("distTypeNm")));
		jsonObj.put("corp_id", getString(distInfo.get("enterpriseKey")));
		jsonObj.put("corp_nm", getString(distInfo.get("enterpriseNm")));
		jsonObj.put("corp_user_id", getString(paramObj.get("enterpriseUserKey")));
		jsonObj.put("corp_user_nm", getString(distInfo.get("enterpriseUserNm")));
		jsonObj.put("prod_nm", getString(distInfo.get("prodNm")));
		jsonObj.put("rgst_dtm", getString(distInfo.get("time")));
		jsonObj.put("dash_enter_type", getString(distInfo.get("enterpriseType")));
		jsonObj.put("dash_enter_type_nm", getString(distInfo.get("enterpriseTypeName")));

		return jsonObj.toString();
	}

	private String getString(Object obj) {
		return obj == null ? "" : String.valueOf(obj);
	}

	private void invokeBlockChain(Map<String, Object> paramObj, boolean isChild) throws Exception {

		URIBuilder builder = new URIBuilder();
		
		Map<String, String> commCodeMap = new HashMap<>();
		commCodeMap.put("codeId", "CHAIN_URL");
		commCodeMap.put("codeValue", "REGISTER_DIST_DATA_IP");
		
		String ip = messageUtil.getCommonCodeValueName(commCodeMap);
		
		commCodeMap.put("codeValue", "REGISTER_DIST_DATA_PORT");
		String port = messageUtil.getCommonCodeValueName(commCodeMap);
		
		commCodeMap.put("codeValue", "REGISTER_DIST_DATA_PATH");
		String path = messageUtil.getCommonCodeValueName(commCodeMap);
		
		builder.setScheme("http").setHost(ip).setPort(Integer.parseInt(port)).setPath(path);
		
		builder.setScheme("http").setHost(ip).setPort(Integer.parseInt(port)).setPath(path);
		URI requestURL = null;
		try {
			requestURL = builder.build();
		} catch (URISyntaxException use) {
		}

		ExecutorService threadpool = Executors.newFixedThreadPool(2);
		Async async = Async.newInstance().use(threadpool);
		final Request request = Request.Post(requestURL);
		request.setHeader("Content-Type", "application/json");

		String jsonString = makeJsonString(paramObj, isChild);
		
		HttpEntity entity = new ByteArrayEntity(jsonString.getBytes("UTF-8"));
		request.body(entity);
		Future<Content> future = async.execute(request, new FutureCallback<Content>() {
			public void failed(final Exception e) {
				System.out.println(e.getMessage() + ": " + request);
			}

			public void completed(final Content content) {
				System.out.println("Request completed: " + request);
				System.out.println(content.asString());
			}
			
			public void cancelled() {
			}
		});
	}
	
	private void invokeBlockChain4Delivery(Map<String, Object> paramObj) throws Exception {

		URIBuilder builder = new URIBuilder();
		
		Map<String, String> commCodeMap = new HashMap<>();
		commCodeMap.put("codeId", "CHAIN_URL");
		commCodeMap.put("codeValue", "REGISTER_DELIVERY_DATA_IP");
		
		String ip = messageUtil.getCommonCodeValueName(commCodeMap);
		
		commCodeMap.put("codeValue", "REGISTER_DELIVERY_DATA_PORT");
		String port = messageUtil.getCommonCodeValueName(commCodeMap);
		
		commCodeMap.put("codeValue", "REGISTER_DELIVERY_DATA_PATH");
		String path = messageUtil.getCommonCodeValueName(commCodeMap);
		
		builder.setScheme("http").setHost(ip).setPort(Integer.parseInt(port)).setPath(path);
		URI requestURL = null;
		try {
			requestURL = builder.build();
		} catch (URISyntaxException use) {
		}

		ExecutorService threadpool = Executors.newFixedThreadPool(2);
		Async async = Async.newInstance().use(threadpool);
		final Request request = Request.Post(requestURL);
		request.setHeader("Content-Type", "application/json");

		String jsonString = makeJsonString4Delivery(paramObj);
		HttpEntity entity = new ByteArrayEntity(jsonString.getBytes("UTF-8"));
		request.body(entity);
		Future<Content> future = async.execute(request, new FutureCallback<Content>() {
			public void failed(final Exception e) {
				System.out.println(e.getMessage() + ": " + request);
			}

			public void completed(final Content content) {
				System.out.println("Request completed: " + request);
				System.out.println(content.asString());
			}

			public void cancelled() {
			}
		});
	}
	
	private String makeJsonString4Delivery(Map<String, Object> paramObj) {

		JSONObject jsonObj = new JSONObject();

		jsonObj.put("dv_id", getString(paramObj.get("deliveryId")));
		jsonObj.put("dv_dtm", getString(paramObj.get("dateTime")));
		jsonObj.put("corp_id", getString(paramObj.get("enterpriseKey")));
		jsonObj.put("corp_nm", getString(paramObj.get("enterpriseNm")));
		jsonObj.put("lat", getString(paramObj.get("latitude")));
		jsonObj.put("lng", getString(paramObj.get("longitude")));
		jsonObj.put("corp_user_id", getString(paramObj.get("enterpriseUserKey")));
		jsonObj.put("corp_user_nm", getString(paramObj.get("enterpriseUserNm")));
		return jsonObj.toString();
	}
}