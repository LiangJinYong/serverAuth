package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.EnterprisePhysicalDistributionHistoryListDao;
import com.inter.enterprise.service.EnterprisePhysicalDistributionHistoryListService;
import com.inter.util.ResultMessageUtil;

@Service
public class EnterprisePhysicalDistributionHistoryListServiceImpl implements EnterprisePhysicalDistributionHistoryListService {

	@Autowired
	private EnterprisePhysicalDistributionHistoryListDao enterprisePhysicalDistributionHistoryListDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String physicalDistributionHistoryList(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> appEnterpriseUser = enterprisePhysicalDistributionHistoryListDao.queryAppEnterpriseUserByToken(param);
		
		if (appEnterpriseUser != null) {
			
			int currentEnterpriseUserKey = (Integer) appEnterpriseUser.get("enterprise_user_key");
			String enterpriseUserName = (String) appEnterpriseUser.get("name");
			String auth = (String) appEnterpriseUser.get("auth");
			
			if ("AU01".equals(auth) || "AU02".equals(auth)) {
				param.put("currentEnterpriseUserKey", String.valueOf(currentEnterpriseUserKey));
				
				List<Map<String, Object>> historyList = enterprisePhysicalDistributionHistoryListDao.queryPhysicalDistributionHistoryList(param);
				
				result.put("data", historyList);
				result.put("enterpriseUserName", enterpriseUserName);
				result.put("resultCode", 200);
			} else {
				result.put("resultCode", 401);
			}
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}

}
