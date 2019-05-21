package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.enterprise.dao.AgreementReconfirmDao;
import com.inter.enterprise.dao.GetSpecifiedSequenceDao;
import com.inter.enterprise.service.AgreementReconfirmService;
import com.inter.util.ResultMessageUtil;

@Service
public class AgreementReconfirmServiceImpl implements AgreementReconfirmService {

	@Autowired
	private AgreementReconfirmDao agreementReconfirmDao;
	
	@Autowired
	private GetSpecifiedSequenceDao getSpecifiedSequenceDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Transactional
	public String agreementReconfirm(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		Map<String, Object> appEnterpriseUserByToken = agreementReconfirmDao.queryAppEnterpriseUserByToken(param);
		
		try {
			if (appEnterpriseUserByToken != null) {
				
				int agreeId = getSpecifiedSequenceDao.getSpecifiedSequence("agree_id");
				param.put("agreeId", String.valueOf(agreeId));
				
				agreementReconfirmDao.insertAgreementInfo(param);
				agreementReconfirmDao.updateUserAgreementInfo(param);
				
				result.put("resultCode", 200);
			} else {
				result.put("resultCode", 403);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("resultCode", 500);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
