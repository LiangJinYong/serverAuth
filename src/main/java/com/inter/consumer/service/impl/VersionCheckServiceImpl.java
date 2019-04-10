package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.VersionCheckDao;
import com.inter.consumer.service.VersionCheckService;
import com.inter.util.ResultMessageUtil;

@Service
public class VersionCheckServiceImpl implements VersionCheckService {

	@Autowired
	private VersionCheckDao versionCheckDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Transactional
	public String versionCheck(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
	
		Map<String, Object> versionCheckInfo = versionCheckDao.getVersionCheckInfo(param);

		if (versionCheckInfo != null) {
			result.putAll(versionCheckInfo);
			result.put("resultCode", 200);
			
			String encryptedKey = versionCheckDao.getEncrptedKey();
			result.put("encryptedKey", encryptedKey);
		} else {
			result.put("resultCode", 500);
		}
		
		Gson gson = new Gson();
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}
	
}
