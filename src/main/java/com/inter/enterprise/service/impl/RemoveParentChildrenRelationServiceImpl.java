package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.enterprise.dao.RemoveParentChildrenRelationDao;
import com.inter.enterprise.service.RemoveParentChildrenRelationService;
import com.inter.util.ResultMessageUtil;

@Service
@Transactional
public class RemoveParentChildrenRelationServiceImpl implements RemoveParentChildrenRelationService {

	@Autowired
	private RemoveParentChildrenRelationDao removeParentChildrenRelationDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String removeParentChildrenRelation(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Gson gson = new Gson();
		
		Map<String, Object> appEnterpriseUser = removeParentChildrenRelationDao.queryAppEnterpriseUserByToken(param);
		
		if (appEnterpriseUser != null) {
			String sequence = param.get("sequence");
			
			try {
				String parentSequence = removeParentChildrenRelationDao.getParentSequence(sequence);
				
				if (parentSequence != null) {
					removeParentChildrenRelationDao.deleteParentChildrenRelation(parentSequence);
				}
				
				removeParentChildrenRelationDao.deleteParentChildrenRelation(sequence);
				
				result.put("resultCode", 200);
			} catch (Exception e) {
				result.put("resultCode", 401);
			}
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
