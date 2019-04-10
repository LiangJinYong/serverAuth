package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.InventoryDao;
import com.inter.enterprise.service.InventoryService;
import com.inter.util.ResultMessageUtil;

@Service
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryDao inventoryDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String inventory(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Map<String, Object> appEnterpriseUserByToken = inventoryDao.queryAppEnterpriseUserByToken(param);
		
		if (appEnterpriseUserByToken != null) {
			String userId = (String) appEnterpriseUserByToken.get("id");
			
			List<Map<String, Object>> inventoryList = inventoryDao.queryInventoryList(userId);
			
			result.put("data", inventoryList);
			
			result.put("resultCode", 200);
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
