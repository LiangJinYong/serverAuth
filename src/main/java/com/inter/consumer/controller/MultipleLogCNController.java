package com.inter.consumer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.MultipleLogCNService;

@Controller
@RequestMapping("/consumer")
public class MultipleLogCNController {

	@Autowired
	private MultipleLogCNService multipleLogCNService;
	
	@RequestMapping("/multipleLogCN")
	@ResponseBody
	public String multipleLogCN(HttpServletRequest request) {
		
		String jsonLog = request.getParameter("log");
		String countryCode = request.getParameter("countryCode");
		
		String result = multipleLogCNService.multipleLogCN(jsonLog, countryCode);
		
		return result;
	}
}
