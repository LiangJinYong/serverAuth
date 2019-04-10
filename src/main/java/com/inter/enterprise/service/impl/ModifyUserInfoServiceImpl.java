package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.ModifyUserInfoDao;
import com.inter.enterprise.service.ModifyUserInfoService;
import com.inter.util.ResultMessageUtil;

@Service
public class ModifyUserInfoServiceImpl implements ModifyUserInfoService {

	@Autowired
	private ModifyUserInfoDao modifyUserInfoDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String modifyUserInfo(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> appEnterpriseUserByToken = modifyUserInfoDao.queryAppEnterpriseUserByToken(param);
		
		if (appEnterpriseUserByToken != null) {
			
			int superuserEnterpriseKey = (Integer) appEnterpriseUserByToken.get("enterprise_key");
			String auth = (String) appEnterpriseUserByToken.get("auth");
			
			int enterpriseUserKey = Integer.parseInt(param.get("enterpriseUserKey")); 
			
			if ("AU01".equals(auth)) {
				
				Map<String, Object> appEnterpriseUserByEnterpriseUserKey = modifyUserInfoDao.queryAppEnterpriseUserByEnterpriseUserKey(enterpriseUserKey);
				
				if (appEnterpriseUserByEnterpriseUserKey != null) {
					int enterpriseKey = (Integer) appEnterpriseUserByEnterpriseUserKey.get("enterprise_key");
					String userPassword = (String) appEnterpriseUserByEnterpriseUserKey.get("password");
					
					if (superuserEnterpriseKey == enterpriseKey) {
						
						String password = param.get("password");
						if (password == null) {
							password = userPassword;
							param.put("password", password);
						}
						
						try {
							modifyUserInfoDao.updateUserInfo(param);
							result.put("resultCode", 200);
						} catch(Exception e) {
							result.put("resultCode", 500);
						}
					} else {
						result.put("resultCode", 400);
					}
				} else {
					result.put("resultCode", 404);
				}
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
