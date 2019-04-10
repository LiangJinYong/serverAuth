package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.GetSpecifiedSequenceDao;
import com.inter.consumer.dao.MemberLoginDao;
import com.inter.consumer.dao.ModifyUserAgreementsDao;
import com.inter.consumer.service.ModifyUserAgreementsService;
import com.inter.util.ResultMessageUtil;

@Service
public class ModifyUserAgreementsServiceImpl implements ModifyUserAgreementsService {

	@Autowired
	private ModifyUserAgreementsDao modifyUserAgreementsDao;
	
	@Autowired
	private GetSpecifiedSequenceDao getSpecifiedSequenceDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;

	@Autowired
	private MemberLoginDao memberLoginDao;
	
	public String modifyUserAgreements(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Integer userId = memberLoginDao.getUserIdByToken(param.get("token"));

		if (userId == null || !String.valueOf(userId).equals(param.get("appUserId"))) {
			result.put("resultCode", 403);

			messageUtil.addResultMsg(param, result);
			
			return gson.toJson(result);
		}
		
		int agreeId = getSpecifiedSequenceDao.getSpecifiedSequence("agree_id");
		param.put("agreeId", String.valueOf(agreeId));
		
		try {
			modifyUserAgreementsDao.insertAgreementInfo(param);
			
			modifyUserAgreementsDao.updateUserAgreementRel(param);
			
			result.put("resultCode", 200);
		} catch (Exception e) {
			result.put("resultCode", 501);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
