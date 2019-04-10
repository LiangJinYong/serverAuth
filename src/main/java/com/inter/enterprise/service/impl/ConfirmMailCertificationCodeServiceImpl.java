package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.ConfirmMailCertificationCodeDao;
import com.inter.enterprise.service.ConfirmMailCertificationCodeService;
import com.inter.util.ResultMessageUtil;

@Service
public class ConfirmMailCertificationCodeServiceImpl implements ConfirmMailCertificationCodeService {

	@Autowired
	private ConfirmMailCertificationCodeDao confirmMailCertificationCodeDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String confirmMailCertificationCode(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, String> certificationCode = confirmMailCertificationCodeDao.queryCertificationCode(param);
		
		if (certificationCode != null) {
			
			String certificationCodeParam = param.get("certificationCode");
			String certificationCodeDB = certificationCode.get("certification_code");
			if (certificationCodeParam.equals(certificationCodeDB)) {
				result.put("resultCode", 200);
			} else {
				result.put("resultCode", 406);
			}
		} else {
			result.put("resultCode", 413);
		}
		
		messageUtil.addResultMsg(param, result);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}
