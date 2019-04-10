package com.inter.enterprise.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
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

import com.google.gson.Gson;
import com.inter.enterprise.dao.RegisterDeliveryInfoDao;
import com.inter.enterprise.service.RegisterDeliveryInfoService;
import com.inter.util.ResultMessageUtil;

import net.sf.json.JSONObject;

@Service
public class RegisterDeliveryInfoServiceImpl implements RegisterDeliveryInfoService {

	@Autowired
	private RegisterDeliveryInfoDao registerDeliveryInfoDao;

	@Autowired
	private ResultMessageUtil messageUtil;

	@Override
	public String registerDeliveryInfo(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();

		try {
			Map<String, Object> appEnterpriseUserByToken = registerDeliveryInfoDao.queryAppEnterpriseUserByToken(param);

			if (appEnterpriseUserByToken != null) {

				registerDeliveryInfoDao.insertDeliveryInfo(param);
				result.put("resultCode", 200);

				/* Invoke Block Chain */
				// Get enterprise info
				String enterpriseUserKey = param.get("enterpriseUserKey");
				Map<String, Object> enterpriseInfo = registerDeliveryInfoDao.getEnterpriseInfo(enterpriseUserKey);

				Map<String, Object> paramObj = new HashMap<>();
				paramObj.putAll(param);
				paramObj.putAll(enterpriseInfo);

				invokeBlockChain(paramObj);
			} else {
				result.put("resultCode", 403);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("resultCode", 500);
		}

		messageUtil.addResultMsg(param, result);

		return gson.toJson(result);
	}

	private String makeJsonString(Map<String, Object> paramObj) {

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

	private String getString(Object obj) {
		return obj == null ? "" : String.valueOf(obj);
	}

	private void invokeBlockChain(Map<String, Object> paramObj) throws Exception {

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
}
