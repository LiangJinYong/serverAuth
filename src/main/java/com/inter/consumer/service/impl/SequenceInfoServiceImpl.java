package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.SequenceInfoDao;
import com.inter.consumer.service.SequenceInfoService;
import com.inter.util.ResultMessageUtil;

@Service
public class SequenceInfoServiceImpl implements SequenceInfoService {

	@Autowired
	private SequenceInfoDao sequenceInfoDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;
	
	public String sequenceInfo(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		List<Map<String, Object>> sequenceInfoList = sequenceInfoDao.getSequenceInfoList();
		
		result.put("sequenceInfoList", sequenceInfoList);
		result.put("resultCode", 200);
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}
}
