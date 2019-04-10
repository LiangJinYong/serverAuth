package com.inter.consumer.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.service.SendMailFMSService;

@Service
public class SendMailFMSServiceImpl implements SendMailFMSService {

	@Autowired
	@Qualifier("mailSenderFMS")
	private MailSender mailSender;

	public String sendMailFms(String mailJson) {
		Gson gson = new Gson();
		
		Map<String, Object> mailObj = gson.fromJson(mailJson, Map.class);
		String subject = (String) mailObj.get("subject");
		String mail = (String) mailObj.get("mail");
		String message = (String) mailObj.get("message");
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom("lianshukj.fms@lianshukj.com");
		simpleMailMessage.setTo(mail);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		
		mailSender.send(simpleMailMessage);
		return "success";
	}
	
}
