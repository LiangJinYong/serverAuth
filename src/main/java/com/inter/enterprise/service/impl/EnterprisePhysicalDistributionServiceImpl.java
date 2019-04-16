package com.inter.enterprise.service.impl;

import java.io.UnsupportedEncodingException;
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
	private final String[] typesDeleteParentRel = { "WH", "RL", "SL", "TB", "DV", "UL" };

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
				
				String typeParam = param.get("type");
				// get all the sequeces that need to update sequence state, insert logistics data and invoke block chain
				if (Arrays.asList(typeArr).contains(typeParam)) {
					
					Map<String, Object> paramObj = new HashMap<>();
					paramObj.putAll(param);
					
					if ("PG".equals(typeParam) || "LO".equals(typeParam)) {
						String childrenSequence = param.get("childrenSequence");
						String[] childSeqArr = childrenSequence.split("-");
						
						paramObj.put("childSeqList", childSeqArr);
						enterprisePhysicalDistributionDao.insertParentChildrenRelation(paramObj);
					} else if (Arrays.asList(typesDeleteParentRel).contains(typeParam)) {
						enterprisePhysicalDistributionDao.removeParentRelation(paramObj);
					}
					
					// Get sequences that only exist in app_seq table
					List<String> involvedSeqs = getInvolvedSeqs(sequence, typeParam);
					
					boolean blockChainYn = false;
					
					if (involvedSeqs.size() > 0) {
						String firstSeq = involvedSeqs.get(0);
						String isInvokingBlockChain = enterprisePhysicalDistributionDao.isInvokingBlockChain(firstSeq);
						if ("Y".equals(isInvokingBlockChain)) {
							blockChainYn = true;
						}
					}
					
					updateSequenceStatus(involvedSeqs, typeParam);
					
					registerLogisticsData(paramObj, involvedSeqs, blockChainYn);
					
					if ("DV".equals(typeParam)) {
						int deliveryId = getSpecifiedSequenceDao.getSpecifiedSequence("dv_id");
						param.put("deliveryId", String.valueOf(deliveryId));
						paramObj.put("deliveryId", String.valueOf(deliveryId));
						
						registerDeliveryInfoDao.insertDeliveryInfo(param);
						
						if (blockChainYn) {
							Map<String, Object> enterpriseInfo = registerDeliveryInfoDao
									.getEnterpriseInfo(param.get("enterpriseUserKey"));
							paramObj.putAll(enterpriseInfo);
							try {
								invokeBlockChain4Delivery(paramObj);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						result.put("deliveryId", deliveryId);
					}
					
					if ("UP".equals(typeParam)) {
						List<String> unpackingChildSeqs = new ArrayList<>();
						unpackingChildSeqs.add(sequence);
						List<String> allDescendentSeqs = enterprisePhysicalDistributionDao.getAllDescendentSeqs(sequence);
						unpackingChildSeqs.addAll(allDescendentSeqs);
						paramObj.put("unpackingChildSeqs", unpackingChildSeqs);
						enterprisePhysicalDistributionDao.removeParentAndDescendentRelation(paramObj);
					}
					
					result.put("resultCode", 200);
				} else {
					
					result.put("resultCode", 419);
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

	private void registerLogisticsData(Map<String, Object> paramObj, List<String> involvedSeqs, boolean blockChainYn) {
		
		for(String seq: involvedSeqs) {
			paramObj.put("singleSeq", seq);
			enterprisePhysicalDistributionDao.insertLogisticsData(paramObj);
			
			if (blockChainYn) {
				try {
					invokeBlockChain(paramObj);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateSequenceStatus(List<String> involvedSeqs, String typeParam) {
		if (involvedSeqs.size() > 0) {
			Map<String, Object> param = new HashMap<>();
			param.put("type", typeParam);
			param.put("seqList", involvedSeqs);
			enterprisePhysicalDistributionDao.updateSequenceStatus(param);
		}
	}

	private List<String> getInvolvedSeqs(String sequence, String typeParam) {
		List<String> result = new ArrayList<>();
		
		if ("PG".equals(typeParam)) {
			List<String> allChildren = enterprisePhysicalDistributionDao.selectChildSeqsInclusive(sequence);
			result.addAll(allChildren);
		} else {
			List<String> allDescendents = enterprisePhysicalDistributionDao.selectDescendentSeqsInclusive(sequence);
			result.addAll(allDescendents);
		}
		
		return result;
	}

	private String makeJsonString(Map<String, Object> paramObj) {

		JSONObject jsonObj = new JSONObject();
		Map<String, String> distInfo = enterprisePhysicalDistributionDao.getDistInfo(paramObj);

		jsonObj.put("dstrb_id", getString(paramObj.get("physicalDistributionKey")));
		jsonObj.put("seq", getString(paramObj.get("singleSeq")));
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

	private void invokeBlockChain(Map<String, Object> paramObj) throws UnsupportedEncodingException {

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

		URI requestURL = null;
		try {
			requestURL = builder.build();
		} catch (URISyntaxException use) {
		}

		ExecutorService threadpool = Executors.newFixedThreadPool(2);
		Async async = Async.newInstance().use(threadpool);
		final Request request = Request.Post(requestURL);
		request.setHeader("Content-Type", "application/json");

		String jsonString = makeJsonString(paramObj);

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