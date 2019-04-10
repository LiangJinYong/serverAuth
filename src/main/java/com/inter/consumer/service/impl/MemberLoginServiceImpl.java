package com.inter.consumer.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.MemberLoginDao;
import com.inter.consumer.service.MemberLoginService;
import com.inter.util.ResultMessageUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class MemberLoginServiceImpl implements MemberLoginService {

	@Autowired
	private MemberLoginDao memberLoginDao;

	@Autowired
	private ResultMessageUtil messageUtil;

	public String memberLogin(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String loginTypeCd = param.get("loginTypeCd");
		
		if (loginTypeCd == null) {
			loginTypeCd = "E";
			param.put("loginTypeCd", loginTypeCd);
		}
		
		if (!"E".equals(loginTypeCd)) {
			Map<String, Object> user = memberLoginDao.checkUserExistence(param);
			
			if (user == null) {
				result.put("resultCode", 420);
				
				messageUtil.addResultMsg(param, result);
				
				return gson.toJson(result);
			}
		}

		Integer userId = memberLoginDao.getUserIdByIdPw(param);

		if (userId == null) {
			result.put("resultCode", 412);

			messageUtil.addResultMsg(param, result);
			
			return gson.toJson(result);
		}

		String token = createToken(param.get("loginId"));
		memberLoginDao.deleteTokenByUserId(userId);

		param.put("token", token);
		param.put("appUserId", String.valueOf(userId));

		memberLoginDao.insertTokenInfo(param);

		Map<String, Object> userInfo = memberLoginDao.getUserInfoById(param);

		byte[] img = (byte[]) userInfo.get("img");
		userInfo.put("img", Base64.getEncoder().encodeToString(img));

		result.putAll(userInfo);

		result.put("resultCode", 200);
		result.put("token", token);

		messageUtil.addResultMsg(param, result);

		return gson.toJson(result);
	}

	private String createToken(String loginId) {
		long time = new Date().getTime();
		String subject = loginId + time;

		String key = "icsInterface";

		String token = Jwts.builder().setSubject(subject).signWith(SignatureAlgorithm.HS512, key).compact();

		return token;
	}

}
