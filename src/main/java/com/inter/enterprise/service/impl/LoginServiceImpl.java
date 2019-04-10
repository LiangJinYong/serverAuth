package com.inter.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.LoginDao;
import com.inter.enterprise.service.LoginService;
import com.inter.util.ResultMessageUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String login(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> appEnterpriseUser = loginDao.queryAppEnterpriseUserById(param);
		
		if (appEnterpriseUser != null) {
			String passwordParam = param.get("password");
			String passwordDb = (String) appEnterpriseUser.get("password");
			
			if (passwordParam.equals(passwordDb)) {
				String id = param.get("id");
				String token = createToken(id);
				
				param.put("token", token);
				
				try {
					loginDao.updateToken(param);
					
					result.put("auth", appEnterpriseUser.get("auth"));
					result.put("type", appEnterpriseUser.get("type"));
					result.put("id", appEnterpriseUser.get("id"));
					result.put("name", appEnterpriseUser.get("name"));
					result.put("enterpriseUserKey", appEnterpriseUser.get("enterpriseUserKey"));
					result.put("manageChgYn", appEnterpriseUser.get("manageChgYn"));
					result.put("personalInfoChgYn", appEnterpriseUser.get("personalInfoChgYn"));
					result.put("manageYn", appEnterpriseUser.get("manageYn"));
					result.put("perosnalInfoYn", appEnterpriseUser.get("perosnalInfoYn"));
					result.put("token", token);
					
					Integer enterpriseUserKey = (Integer) appEnterpriseUser.get("enterpriseUserKey");
					
					Map<String, Object> deliveryInfo = loginDao.getDeliveryInfo(enterpriseUserKey);
					if (deliveryInfo != null) {
						result.putAll(deliveryInfo);
					}
					result.put("resultCode", 200);
				} catch (Exception e) {
					result.put("resultCode", 500);
				}
			} else {
				result.put("resultCode", 412);
			}
		} else {
			result.put("resultCode", 412);
		}
		
		messageUtil.addResultMsg(param, result);

		Gson gson = new Gson();
		return gson.toJson(result);
	}
	
	private String createToken(String id) {
		long time = new Date().getTime();
		String subject = id + time;

		String key = "convertedInterface";

		String token = Jwts.builder().setSubject(subject).signWith(SignatureAlgorithm.HS512, key).compact();

		return token;
	}

}
