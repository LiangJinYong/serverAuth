package com.inter.consumer.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.FindPwdDao;
import com.inter.consumer.dao.LoginIdRepetitionDao;
import com.inter.consumer.service.FindPwdService;
import com.inter.util.AesModule;
import com.inter.util.MakeCertificationCodeUtil;
import com.inter.util.ResultMessageUtil;

@Service
public class FindPwdServiceImpl implements FindPwdService {

	@Autowired
	private FindPwdDao findPwdDao;

	@Autowired
	private ResultMessageUtil messageUtil;

	@Autowired
	private LoginIdRepetitionDao loginIdRepetitionDao;

	@Autowired
	@Qualifier("mailSender")
	private MailSender mailSender;

	@Transactional
	public String findPwd(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		String loginTypeCd = param.get("loginTypeCd");
		
		if (loginTypeCd == null) {
			loginTypeCd = "E";
			param.put("loginTypeCd", loginTypeCd);
		}

		int loginIdCount = loginIdRepetitionDao.selectLoginIdCount(param);

		if (loginIdCount == 0) {
			result.put("resultCode", 412);

			messageUtil.addResultMsg(param, result);

			return gson.toJson(result);
		}

		String tempPwd = MakeCertificationCodeUtil.makeCertificationCode();

		String aesKey = findPwdDao.getEncrptedKey();
		aesKey = AesModule.DecryptKey(aesKey);
		String encryptedTempPwd = AesModule.aesEncrypt(tempPwd, aesKey);
		param.put("encryptedTempPwd", encryptedTempPwd);

		int appUserId = findPwdDao.getUserIdByLoginId(param);
		param.put("appUserId", String.valueOf(appUserId));

		try {
			findPwdDao.updatePwd(param);

			SimpleMailMessage message = new SimpleMailMessage();

			// TODO subject & message get from DB
			message.setFrom("lianshu@lianshukj.com");
			message.setTo(param.get("loginId"));
			message.setSubject("Find Password [ICS]");
			message.setText("Your temporary password is " + tempPwd);
			mailSender.send(message);
			
			result.put("resultCode", 200);
		} catch (Exception e) {
			result.put("resultCode", 501);
		}
		
		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
