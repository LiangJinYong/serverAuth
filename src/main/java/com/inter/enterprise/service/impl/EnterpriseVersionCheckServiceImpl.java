package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.EnterpriseVersionCheckDao;
import com.inter.enterprise.service.EnterpriseVersionCheckService;
import com.inter.util.ResultMessageUtil;

@Service
public class EnterpriseVersionCheckServiceImpl implements EnterpriseVersionCheckService {

	@Autowired
	private EnterpriseVersionCheckDao enterpriseVersionCheckDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String versionCheck(Map<String, String> param) {

		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> versionCheckInfo = enterpriseVersionCheckDao.getVersionCheckInfo(param);

		if (versionCheckInfo != null) {
			result.putAll(versionCheckInfo);
			result.put("resultCode", 200);
			String encryptedKey = enterpriseVersionCheckDao.getEncrptedKey();
			result.put("encryptedKey", encryptedKey);
		} else {
			result.put("resultCode", 500);
		}

		messageUtil.addResultMsg(param, result);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}

}
