package com.inter.enterprise.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.SignupSuperuserService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/enterprise")
public class SignupSuperuserController {

	@Autowired
	private SignupSuperuserService signupSuperuserService;

	@RequestMapping("/signupSuperuser")
	@ResponseBody
	public String signupSuperuser(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();

		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String result = signupSuperuserService.signupSuperuser(param);
		return result;
	}
}
