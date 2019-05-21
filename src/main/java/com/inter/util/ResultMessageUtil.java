package com.inter.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inter.enterprise.dao.MessageDao;


@Component
public class ResultMessageUtil {

	@Autowired
	private MessageDao messageDao;
	
	public void addResultMsg(Map<String, String> param, Map<String, Object> result) {
		param.put("resultCode", String.valueOf(result.get("resultCode")));
		String resultMsg = messageDao.getResultMessage(param);
		result.put("resultMsg", resultMsg);
	}
	
	public String getCommonCodeValueName(Map<String, String> param) {
	
		return messageDao.getCommonCodeValueName(param);
	}
}
