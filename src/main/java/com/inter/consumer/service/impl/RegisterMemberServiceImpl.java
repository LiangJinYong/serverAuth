package com.inter.consumer.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.GetSpecifiedSequenceDao;
import com.inter.consumer.dao.LoginIdRepetitionDao;
import com.inter.consumer.dao.MemberLoginDao;
import com.inter.consumer.dao.RegisterMemberDao;
import com.inter.consumer.service.RegisterMemberService;
import com.inter.util.ResultMessageUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class RegisterMemberServiceImpl implements RegisterMemberService {

	@Autowired
	private RegisterMemberDao registerMemberDao;
	
	@Autowired
	private LoginIdRepetitionDao loginIdRepetitionDao;
	
	@Autowired 
	private GetSpecifiedSequenceDao getSpecifiedSequenceDao;
	
	@Autowired
	private MemberLoginDao memberLoginDao;
	

	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Transactional
	public String registerMember(Map<String, String> param) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String loginTypeCd = param.get("loginTypeCd");
		
		if (loginTypeCd == null) {
			loginTypeCd = "E";
			param.put("loginTypeCd", loginTypeCd);
		}
		
		int loginIdCount = loginIdRepetitionDao.selectLoginIdCount(param);
		
		if (loginIdCount != 0) {
			result.put("resultCode", 409);
			
			messageUtil.addResultMsg(param, result);
			
			return gson.toJson(result);
		}
		
		int agreeId =  getSpecifiedSequenceDao.getSpecifiedSequence("agree_id");
		int appUserId =  getSpecifiedSequenceDao.getSpecifiedSequence("app_user_id");
		String token = createToken(param.get("loginId"));
		
		param.put("agreeId", String.valueOf(agreeId));
		param.put("appUserId", String.valueOf(appUserId));
		param.put("token", token);
		
		try {
			registerMemberDao.insertAgreementInfo(param);

			registerMemberDao.insertTokenInfo(param);
			
			registerMemberDao.insertAppUserInfo(param);
			
			String imgStr = param.get("img");
			
			if (!"E".equals(loginTypeCd) && imgStr != null && imgStr.length() > 0) {
				Map<String, Object> paramObj = new HashMap<String, Object>();
				paramObj.putAll(param);
				
				byte[] imgBytes = Base64.getDecoder().decode(imgStr);
				int imgId = getSpecifiedSequenceDao.getSpecifiedSequence("img_id");
				paramObj.put("img", imgBytes);
				paramObj.put("imgId", imgId);
	
				registerMemberDao.insertImg(paramObj);
				registerMemberDao.updateUserImgInfo(paramObj);
			}
			
			Map<String, Object> userInfo = memberLoginDao.getUserInfoById(param);
			
			
			byte[] img = (byte[]) userInfo.get("img");
			userInfo.put("img", Base64.getEncoder().encodeToString(img));
			
			result.putAll(userInfo);
			result.put("token", token);
			result.put("resultCode", 200);
		} catch (Exception e) {
			result.put("resultCode", 501);
		}
		
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
