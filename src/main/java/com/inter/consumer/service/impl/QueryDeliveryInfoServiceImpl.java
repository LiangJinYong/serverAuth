package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.QueryDeliveryInfoDao;
import com.inter.consumer.service.QueryDeliveryInfoService;
import com.inter.util.ResultMessageUtil;

@Service
public class QueryDeliveryInfoServiceImpl implements QueryDeliveryInfoService {

	@Autowired
	private QueryDeliveryInfoDao queryDeliveryInfoDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Override
	@Transactional
	public String queryDeliveryInfo(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		int appUserCount = queryDeliveryInfoDao.getUserCountByIdToken(param);
		
		if (appUserCount != 0) {
			List<Map<String, Object>> deliveryInfo = queryDeliveryInfoDao.selectDeliveryInfo(param);
			
			result.put("data", deliveryInfo);
			result.put("resultCode", 200);
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
