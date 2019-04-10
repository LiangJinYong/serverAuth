package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.MemberLogoutDao;
import com.inter.consumer.service.MemberLogoutService;
import com.inter.util.ResultMessageUtil;

@Service
public class MemberLogoutServiceImpl implements MemberLogoutService {

	@Autowired
	private MemberLogoutDao memberLogoutDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String memberLogout(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		try {
			memberLogoutDao.deleteUserToken(param);
			
			result.put("resultCode", 200);
		} catch (Exception e) {
			result.put("resultCode", 501);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
