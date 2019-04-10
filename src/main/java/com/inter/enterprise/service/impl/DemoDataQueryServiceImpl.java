package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.DemoDataQueryDao;
import com.inter.enterprise.service.DemoDataQueryService;

@Service
public class DemoDataQueryServiceImpl implements DemoDataQueryService {

	@Autowired
	private DemoDataQueryDao demoDataQueryDao;
	
	public String demoDataQuery() {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		List<Map<String, Object>> data = demoDataQueryDao.selectDemoData();
		
		result.put("data", data);
		result.put("resultCode", 200);
		return gson.toJson(result);
	}
	
	
}
