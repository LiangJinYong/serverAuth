package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.CreateUserDao;
import com.inter.enterprise.dao.GetSpecifiedSequenceDao;
import com.inter.enterprise.service.CreateUserService;
import com.inter.util.GetTimeUtil;
import com.inter.util.ResultMessageUtil;

@Service
public class CreateUserServiceImpl implements CreateUserService {

	@Autowired
	private CreateUserDao createUserDao;
	
	@Autowired
	private GetSpecifiedSequenceDao getSpecifiedSequenceDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String createUser(Map<String, String> param) {

		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String currentId = param.get("id");
		int currentIdCount = createUserDao.getCurrentIdCount(currentId);
		
		if (currentIdCount > 0) {
			result.put("resultCode", 409);
			
			messageUtil.addResultMsg(param, result);
			
			return gson.toJson(result);
		}
		
		Map<String, Object> appEnterpriseUser = createUserDao.queryAppEnterpriseUserByToken(param);

		if (appEnterpriseUser != null) {

			String auth = (String) appEnterpriseUser.get("auth");

			if ("AU01".equals(auth)) {
				int enterpriseKey = (Integer) appEnterpriseUser.get("enterprise_key");

				String time = GetTimeUtil.getTime();
				param.put("time", time);
				param.put("enterpriseKey", String.valueOf(enterpriseKey));

				try {
					int enterpriseUserKey = getSpecifiedSequenceDao.getSpecifiedSequence("enterprise_user_key");
					param.put("enterpriseUserKey", String.valueOf(enterpriseUserKey));
					createUserDao.insertNormalAppEnterpriseUser(param);
					
					result.put("resultCode", 200);
				} catch (Exception e) {
					result.put("resultCode", 500);
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

}
