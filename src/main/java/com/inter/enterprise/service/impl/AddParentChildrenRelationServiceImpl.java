package com.inter.enterprise.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.enterprise.dao.AddParentChildrenRelationDao;
import com.inter.enterprise.service.AddParentChildrenRelationService;
import com.inter.util.ResultMessageUtil;

@Service
public class AddParentChildrenRelationServiceImpl implements AddParentChildrenRelationService {

	@Autowired
	private AddParentChildrenRelationDao addParentChildrenRelationDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Transactional
	public String addParentChildrenRelation(Map<String, String> param) {

		Map<String, Object> result = new HashMap<String, Object>();

		Gson gson = new Gson();

		Map<String, Object> appEnterpriseUserByToken = addParentChildrenRelationDao.queryAppEnterpriseUserByToken(param);

		if (appEnterpriseUserByToken != null) {
			
			// check each sequence to see if it already exists
			String sequenceStr = param.get("children");
			String[] sequenceArr = sequenceStr.split("-");

			for (int i = 0; i < sequenceArr.length; i++) {
				Map<String, Object> childSequence = addParentChildrenRelationDao.selectRelationBySequence(sequenceArr[i]);
				
				if (childSequence != null) {
					result.put("resultCode", 401);
					
					messageUtil.addResultMsg(param, result);
					
					return gson.toJson(result);
				}
			}
			
			Map<String, Object> relationMap = new HashMap<String, Object>();
			String seqChildren = param.get("children");
			String[] childSequenceArr = seqChildren.split("-");
			relationMap.put("childSequenceList", Arrays.asList(childSequenceArr));
			relationMap.putAll(param);
			relationMap.put("enterpriseUserKey", appEnterpriseUserByToken.get("enterprise_user_key"));
			
			try {
				addParentChildrenRelationDao.insertParentChildrenRelation(relationMap);
				result.put("resultCode", 200);
			} catch (Exception e) {
				result.put("resultCode", 402);
			}
		} else {
			result.put("resultCode", 403);
		}
		
		messageUtil.addResultMsg(param, result);

		return gson.toJson(result);
	}

}
