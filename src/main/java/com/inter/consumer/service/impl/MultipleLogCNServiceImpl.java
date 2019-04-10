package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.service.MultipleLogCNService;
import com.inter.consumer.service.VerificationHistoryCNService;

@Service
public class MultipleLogCNServiceImpl implements MultipleLogCNService {

	@Autowired
	private VerificationHistoryCNService verificationHistoryCNService;

	public String multipleLogCN(String jsonLog, String countryCode) {

		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();

		List<Map<String, String>> logList = gson.fromJson(jsonLog, List.class);

		for (Map<String, String> log : logList) {
			try {
				log.put("countryCode", countryCode);
				verificationHistoryCNService.verificationHistoryCN(log);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		result.put("resultCode", 200);
		return gson.toJson(result);
	}

}
