package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.WithdrawMemberDao;
import com.inter.consumer.service.WithdrawMemberService;
import com.inter.util.ResultMessageUtil;

@Service
public class WithdrawMemberServiceImpl implements WithdrawMemberService {

	@Autowired
	private WithdrawMemberDao withdrawMemberDao;

	@Autowired
	private ResultMessageUtil messageUtil;

	@Transactional
	public String withdrawMember(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String loginTypeCd = param.get("loginTypeCd");
		
		if (loginTypeCd == null) {
			loginTypeCd = "E";
			param.put("loginTypeCd", loginTypeCd);
		}

		try {
			if ("E".equals(loginTypeCd)) {
				Integer userId = withdrawMemberDao.getUserIdByTokenAndPwd(param);
				
				if (userId == null || !String.valueOf(userId).equals(param.get("appUserId"))) {
					result.put("resultCode", 412);
					
					messageUtil.addResultMsg(param, result);
					return gson.toJson(result);
				}
			}

			withdrawMemberDao.deleteUserToken(param);
			withdrawMemberDao.delteUserInfo(param);

			result.put("resultCode", 200);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("resultCode", 501);
		}

		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
