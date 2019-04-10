package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.RegistrationQuestionService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class RegistrationQuestionController {

	@Autowired
	private RegistrationQuestionService registrationQuestionService;

	@RequestMapping(value = "/registrationQuestion")
	@ResponseBody
	public String registrationQuestion(HttpServletRequest request) {

		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);

		String token = request.getHeader("token");
		param.put("token", token);

		String result = registrationQuestionService.registrationQuestion(param);

		return result;
	}
}
