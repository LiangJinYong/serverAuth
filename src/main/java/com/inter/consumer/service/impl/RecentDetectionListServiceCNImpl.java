package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.RecentDetectionListDao;
import com.inter.consumer.service.RecentDetectionListCNService;

@Service
public class RecentDetectionListServiceCNImpl implements RecentDetectionListCNService {

	@Autowired
	private RecentDetectionListDao recentDetectionListDao;

	public String recentDetectionListCN(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		param.put("countryCode", "CN");

		param.put("orderFlag", "CN");
		// get succss list
		List<Map<String, Object>> sucDetectionList = recentDetectionListDao.getSucDetectionList(param);

		for (Map<String, Object> sucMap : sucDetectionList) {
			String bizLogoPath = (String) sucMap.get("bizLogoPath");
			if (bizLogoPath != null) {
				sucMap.put("bizLogoPath", param.get("urlHeader") + bizLogoPath);
			} else {
				sucMap.put("bizLogoPath", "");
			}
		}

		// get fail list
		List<Map<String, Object>> failDetectionList = recentDetectionListDao.getFailDetectionList(param);

		// put two lists into result
		result.put("sucDetectionList", sucDetectionList);
		result.put("failDetectionList", failDetectionList);

		result.put("resultCode", 200);

		return gson.toJson(result);
	}

}
