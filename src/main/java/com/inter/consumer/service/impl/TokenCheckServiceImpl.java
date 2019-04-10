package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.TokenCheckDao;
import com.inter.consumer.service.TokenCheckService;
import com.inter.util.ResultMessageUtil;

@Service
public class TokenCheckServiceImpl implements TokenCheckService {

	@Autowired
	private TokenCheckDao tokenCheckDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String tokenCheck(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		int userCount = tokenCheckDao.getUserCountByIdToken(param);
		
		if (userCount > 0) {
			result.put("resultCode", 200);
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
