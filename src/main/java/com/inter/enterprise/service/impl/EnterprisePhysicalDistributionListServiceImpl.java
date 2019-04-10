package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.enterprise.dao.EnterprisePhysicalDistributionListDao;
import com.inter.enterprise.service.EnterprisePhysicalDistributionListService;
import com.inter.util.ResultMessageUtil;

@Service
public class EnterprisePhysicalDistributionListServiceImpl implements EnterprisePhysicalDistributionListService {

	@Autowired
	private EnterprisePhysicalDistributionListDao enterprisePhysicalDistributionListDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Transactional
	public String enterprisePhysicalDistributionList(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> appEnterpriseUser = enterprisePhysicalDistributionListDao.queryAppEnterpriseUserByToken(param);
		
		if (appEnterpriseUser != null) {
			String auth = (String) appEnterpriseUser.get("auth");
			
			if ("AU01".equals(auth) || "AU02".equals(auth)) {
				List<Map<String, Object>> physicalDistributionList = enterprisePhysicalDistributionListDao.queryPhysicalDistributionList(param);
				
				result.put("data", physicalDistributionList);
				result.put("resultCode", 200);
			} else {
				result.put("resultCode", 401);
			}
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}

}

