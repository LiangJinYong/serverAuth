package com.inter.serverAuth.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.serverAuth.dao.ServerAuthDao;
import com.inter.serverAuth.service.ServerAuthService;
import com.inter.util.ResultMessageUtil;

@Service
public class ServerAuthServiceImpl implements ServerAuthService {

	@Autowired
	private ServerAuthDao serverAuthDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Override
	@Transactional
	public String registerAuthKey(String param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String decryptedParam = serverAuthDao.getDecryptedParam(param);
		
		if (decryptedParam == null) {
			result.put("resultCode", 442);
			result.put("resultMsg", "Input value invalid");
		} else {
			
			@SuppressWarnings("unchecked")
			Map<String, Object> paramMap = gson.fromJson(decryptedParam, Map.class);
			
			serverAuthDao.insertAuthKey(paramMap);
			
			result.put("resultCode", 200);
			messageUtil.addResultMsg(paramMap, result);
		}
		
		
		return gson.toJson(result);
	}

	@Override
	public String verifyAuthKey(String param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String decryptedParam = serverAuthDao.getDecryptedParam(param);
		
		if (decryptedParam == null) {
			result.put("resultCode", 442);
			result.put("resultMsg", "Input value invalid");
		} else {
			 
			@SuppressWarnings("unchecked")
			Map<String, Object> paramMap = gson.fromJson(decryptedParam, Map.class);
			
			String securityKey = (String) paramMap.get("SecurityKey");
			
			int securityKeyCount = serverAuthDao.getSecurityKeyCount(securityKey);
			
			if (securityKeyCount > 0) {
				String isValid = serverAuthDao.isValidSecurityKey(securityKey);
				
				if ("Y".equals(isValid)) {
					result.put("resultCode", 200);
				} else {
					result.put("resultCode", 440);
				}
			} else {
				result.put("resultCode", 441);
			}
			
			result.put("SessionId", paramMap.get("SessionId"));
			paramMap.put("resultCode", result.get("resultCode"));
			
			@SuppressWarnings("unchecked")
			Map<String, Object> runParam = (Map<String, Object>) paramMap.get("RunParam");
			
			paramMap.remove("RunParam");
			
			paramMap.putAll(runParam);
			
			serverAuthDao.insertAuthLog(paramMap);
			
			messageUtil.addResultMsg(paramMap, result);
		}
		
		return gson.toJson(result);
	}

	@Override
	public String getServerDate(String param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String decryptedParam = serverAuthDao.getDecryptedParam(param);
		
		if (decryptedParam == null) {
			result.put("resultCode", 442);
			result.put("resultMsg", "Input value invalid");
		} else {
			@SuppressWarnings("unchecked")
			Map<String, Object> paramMap = gson.fromJson(decryptedParam, Map.class);
			
			Date now = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String serverDate = format.format(now);
			
			result.put("serverDate", serverDate);
			result.put("SessionId", paramMap.get("SessionId"));
			result.put("resultCode", 200);
			
			serverAuthDao.insertEasySamplerLog(paramMap);
		}
		
		
		return gson.toJson(result);
	}

	@Override
	public String appCheck(String param) {
		
		Gson gson = new Gson();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> paramMap = gson.fromJson(param, Map.class);
		String statusCode = serverAuthDao.getStatusCode(paramMap);
		
		paramMap.put("statusCode", statusCode);
		serverAuthDao.insertAppCheckLog(paramMap);
		
		return statusCode;
	}

}
