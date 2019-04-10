package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.LoginIdRepetitionDao;
import com.inter.consumer.service.LoginIdRepetitionService;
import com.inter.util.ResultMessageUtil;

@Service
public class LoginIdRepetitionServiceImpl implements LoginIdRepetitionService {

	@Autowired
	private LoginIdRepetitionDao userIdRepetitionDao;

	@Autowired
	private ResultMessageUtil messageUtil;

	public String loginIdRepetitionCheck(Map<String, String> param) {

		Map<String, Object> result = new HashMap<String, Object>();

		Gson gson = new Gson();
		
		String loginTypeCd = param.get("loginTypeCd");
		
		if (loginTypeCd == null) {
			loginTypeCd = "E";
			param.put("loginTypeCd", loginTypeCd);
		}

		int userIdCount = userIdRepetitionDao.selectLoginIdCount(param);

		if (userIdCount == 0) {
			result.put("resultCode", 200);
		} else {
			result.put("resultCode", 409);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
