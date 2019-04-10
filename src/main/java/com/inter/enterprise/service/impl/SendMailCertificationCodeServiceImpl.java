package com.inter.enterprise.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.enterprise.dao.SendMailCertificationCodeDao;
import com.inter.enterprise.service.SendMailCertificationCodeService;
import com.inter.util.GetTimeUtil;
import com.inter.util.MakeCertificationCodeUtil;
import com.inter.util.ResultMessageUtil;

@Service
public class SendMailCertificationCodeServiceImpl implements SendMailCertificationCodeService {

	@Autowired
	private SendMailCertificationCodeDao sendMailCertificationCodeDao;

	@Autowired
	@Qualifier("mailSender")
	private MailSender mailSender;
	
	@Autowired
	private ResultMessageUtil messageUtil;

	public String sendMailCertificationCode(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();

		int count = sendMailCertificationCodeDao.queryAppEnterpriseCount(param);

		if (count == 0) {
			String certificationCode = MakeCertificationCodeUtil.makeCertificationCode();
			String time = GetTimeUtil.getTime();
			param.put("certificationCode", certificationCode);
			param.put("time", time);
			try {
				sendMailCertificationCodeDao.insertMailCertificationCode(param);

				SimpleMailMessage message = new SimpleMailMessage();
				String mail = param.get("mail");
				message.setFrom("lianshu@lianshukj.com");
				message.setTo(mail);
				
				param.put("codeId", "ENTERPRISE_CERTI_MAIL");
				param.put("codeValue", "MAIL_SUBJECT");
				String subject = messageUtil.getCommonCodeValueName(param);
				
				param.put("codeValue", "MAIL_TEXT");
				String text = messageUtil.getCommonCodeValueName(param);
				
				message.setSubject(subject);
				message.setText(text + certificationCode);
				mailSender.send(message);
				result.put("resultCode", 200);
			} catch (Exception e) {
				result.put("resultCode", 500);
				e.printStackTrace();
			}
		} else {
			result.put("resultCode", 408);
		}
		
		messageUtil.addResultMsg(param, result);
		
		Gson gson = new Gson();
		return gson.toJson(result);
	}
}