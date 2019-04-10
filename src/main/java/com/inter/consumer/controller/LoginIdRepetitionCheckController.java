package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.LoginIdRepetitionService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class LoginIdRepetitionCheckController {

	@Autowired
	private LoginIdRepetitionService loginIdRepetitionService;
	
	@RequestMapping("/loginIdRepetitionCheck")
	@ResponseBody
	public String loginIdRepetitionCheck(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String result = loginIdRepetitionService.loginIdRepetitionCheck(param);
		return result;
	}
}
