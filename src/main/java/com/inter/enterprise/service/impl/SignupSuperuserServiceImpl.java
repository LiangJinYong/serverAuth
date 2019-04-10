package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.GetSpecifiedSequenceDao;
import com.inter.enterprise.dao.SignupSuperuserDao;
import com.inter.enterprise.service.SignupSuperuserService;
import com.inter.util.GetTimeUtil;
import com.inter.util.ResultMessageUtil;

@Service
public class SignupSuperuserServiceImpl implements SignupSuperuserService {

	@Autowired
	private SignupSuperuserDao signupSuperuserDao;

	@Autowired
	private GetSpecifiedSequenceDao getSpecifiedSequenceDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Transactional
	public String signupSuperuser(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String currentMail = param.get("mail");
		String currentId = param.get("id");
		
		int currentMailCount = signupSuperuserDao.getCurrentMailCount(currentMail); 
		
		if (currentMailCount > 0) {
			result.put("resultCode", 408);
			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		
		int currentIdCount = signupSuperuserDao.getCurrentIdCount(currentId);
		
		if (currentIdCount > 0) {
			result.put("resultCode", 409);
			messageUtil.addResultMsg(param, result);
			return gson.toJson(result);
		}
		
		String time = GetTimeUtil.getTime();
		param.put("time", time);
		
		try {
			
			int enterpriseUserKey = getSpecifiedSequenceDao.getSpecifiedSequence("enterprise_user_key");
			param.put("enterpriseUserKey", String.valueOf(enterpriseUserKey));
			
			int agreeId = getSpecifiedSequenceDao.getSpecifiedSequence("agree_id");
			param.put("agreeId", String.valueOf(agreeId));
			
			signupSuperuserDao.insertAgreementInfo(param);
			signupSuperuserDao.insertAppEnterprise(param);
			signupSuperuserDao.insertAppEnterpriseUser(param);
			result.put("resultCode", 200);
		}catch (Exception e) {
			result.put("resultCode", 500);
		}
		
		messageUtil.addResultMsg(param, result);
		return gson.toJson(result);
	}

}
