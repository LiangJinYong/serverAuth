package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.VerificationHistoryService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class VerificationHistoryController {

	@Autowired
	private VerificationHistoryService verificationHistoryService;

	@RequestMapping("/verificationHistory")
	@ResponseBody
	public String verificationHistory(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String result = verificationHistoryService.verificationHistory(param);
		return result;
	}
}
