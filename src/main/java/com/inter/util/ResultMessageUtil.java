package com.inter.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inter.serverAuth.dao.MessageDao;


@Component
public class ResultMessageUtil {

	@Autowired
	private MessageDao messageDao;
	
	public void addResultMsg(Map<String, Object> param, Map<String, Object> result) {
		param.put("resultCode", String.valueOf(result.get("resultCode")));
		String resultMsg = messageDao.getResultMessage(param);
		result.put("resultMsg", resultMsg);
	}
	
}
