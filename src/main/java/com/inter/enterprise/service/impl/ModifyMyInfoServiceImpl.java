package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.ModifyMyInfoDao;
import com.inter.enterprise.service.ModifyMyInfoService;
import com.inter.util.ResultMessageUtil;

@Service
public class ModifyMyInfoServiceImpl implements ModifyMyInfoService {

	@Autowired
	private ModifyMyInfoDao modifyMyInfoDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String modifyMyInfo(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> appEnterpriseUser = modifyMyInfoDao.queryAppEnterpriseUserByToken(param);
		
		if (appEnterpriseUser != null) {
			String passwordDB = (String) appEnterpriseUser.get("password");
			String passwordParam = param.get("password");

			if (passwordParam == null) {
				param.put("password", passwordDB);
			}
			
			try {
				modifyMyInfoDao.updateAppEnterpriseUser(param);
				result.put("resultCode", 200);	
			} catch (Exception e) {
				result.put("resultCode", 500);	
			}
		} else {
			result.put("resultCode", 403);	
		}
		
		messageUtil.addResultMsg(param, result);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}

}
