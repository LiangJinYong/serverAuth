package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.enterprise.dao.DemoDataResetDao;
import com.inter.enterprise.service.DemoDataResetService;

@Service
public class DemoDataResetServiceImpl implements DemoDataResetService {

	@Autowired
	private DemoDataResetDao demoDataResetDao;

	@Transactional
	public String demoDataReset() {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		List<Map<String, Long>> relationSequenceList = demoDataResetDao.getRelationSequences();
		
		Set<Long> allSequences = new HashSet<Long>();
		
		for(Map<String, Long> seqMap: relationSequenceList) {
			Set<String> keySet = seqMap.keySet();
			for(String key: keySet) {
				allSequences.add(seqMap.get(key));
				
			}
		}
		
		if (allSequences .size() > 0) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("allSequences", allSequences);
			
			try {
				demoDataResetDao.deletePhisicalDistributionData(param);
				demoDataResetDao.resetSequenceStatus(param);
				
				demoDataResetDao.deleteSequenceRelations();
			} catch (Exception e) {
				result.put("resultCode", 500);
			}
		}
		
		result.put("resultCode", 200);
		return gson.toJson(result);
	}

}
