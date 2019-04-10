package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.MemberLoginDao;
import com.inter.consumer.dao.ModifyPwdDao;
import com.inter.consumer.service.ModifyPwdService;
import com.inter.util.ResultMessageUtil;

@Service
public class ModifyPwdServiceImpl implements ModifyPwdService {

	@Autowired
	private ModifyPwdDao modifyPwdDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Autowired
	private MemberLoginDao memberLoginDao;

	public String modifyPwd(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();

		Gson gson = new Gson();
		
		Integer userId = memberLoginDao.getUserIdByToken(param.get("token"));

		if (userId == null || !String.valueOf(userId).equals(param.get("appUserId"))) {
			result.put("resultCode", 403);

			messageUtil.addResultMsg(param, result);
			
			return gson.toJson(result);
		}
		
		int pwdCount = modifyPwdDao.queryPwdCount(param);
		
		if (pwdCount == 0) {
			result.put("resultCode", 417);

			messageUtil.addResultMsg(param, result);
			
			return gson.toJson(result);
		}

		try {
			modifyPwdDao.update2NewPwd(param);
			
			result.put("resultCode", 200);
		} catch (Exception e) {
			result.put("resultCode", 501);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
