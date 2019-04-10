package com.inter.enterprise.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.SendMailCertificationCodeService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/enterprise")
public class SendMailCertificationCodeController {

	@Autowired
	private SendMailCertificationCodeService sendMailCertificationCodeService;
	
	@RequestMapping("/sendMailCertificationCode")
	@ResponseBody
	public String sendMailCertificationCode(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String result = sendMailCertificationCodeService.sendMailCertificationCode(param);
		return result;
	}
}
