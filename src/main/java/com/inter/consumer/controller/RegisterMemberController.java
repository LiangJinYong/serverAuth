package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.RegisterMemberService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class RegisterMemberController {

	@Autowired
	private RegisterMemberService registerMemberService;
	
	@RequestMapping("/registerMember")
	@ResponseBody
	public String registerMember(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String result = registerMemberService.registerMember(param);
		return result;
	}
}
