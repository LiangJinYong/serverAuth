package com.inter.consumer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.MultipleLogService;

@Controller
@RequestMapping("/consumer")
public class MultipleLogController {

	@Autowired
	private MultipleLogService multipleLogService;
	
	@RequestMapping("/multipleLog")
	@ResponseBody
	public String multipleLog(HttpServletRequest request) {
		
		String jsonLog = request.getParameter("log");
		String countryCode = request.getParameter("countryCode");
		
		String result = multipleLogService.multipleLog(jsonLog, countryCode);
		
		return result;
	}
}
