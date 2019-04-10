package com.inter.consumer.service.impl;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.GetSpecifiedSequenceDao;
import com.inter.consumer.dao.ReportFailureDao;
import com.inter.consumer.service.ReportFailureService;
import com.inter.util.ResultMessageUtil;

@Service
public class ReportFailureServiceImpl implements ReportFailureService {

	@Autowired
	private ReportFailureDao reportFailureDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Autowired 
	private GetSpecifiedSequenceDao getSpecifiedSequenceDao;

	@Transactional
	public String reportFailure(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		int imgId = getSpecifiedSequenceDao.getSpecifiedSequence("img_id");
		param.put("imgId", String.valueOf(imgId));
		
		String img = param.get("img");
		byte[] imgBytes = Base64.getDecoder().decode(img);
		
		Map<String, Object> paramObj = new HashMap<String, Object>();
		paramObj.putAll(param);
		paramObj.put("imgId", imgId);
		paramObj.put("img", imgBytes);
		
		try {
			reportFailureDao.insertImg(paramObj);
			
			reportFailureDao.insertFailureReportInfo(paramObj);
			
			result.put("resultCode", 200);
		} catch (Exception e) {
			result.put("resultCode", 501);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}
	
}
