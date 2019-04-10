package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.RegistrationQuestionDao;
import com.inter.consumer.service.RegistrationQuestionService;
import com.inter.util.ResultMessageUtil;

@Service
public class RegistrationQuestionServiceImpl implements RegistrationQuestionService {

	@Autowired
	private RegistrationQuestionDao registrationQuestionDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;

	public String registrationQuestion(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();

		Integer userCount = registrationQuestionDao.getUserCountByIdToken(param);
		
		if (userCount != 0) {
			try {
				registrationQuestionDao.insertAppQuestion(param);
				result.put("resultCode", 200);
			} catch (Exception e) {
				result.put("resultCode", 500);
			}
		} else {
			result.put("resultCode", 403);
		}
		
		Gson gson = new Gson();
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
