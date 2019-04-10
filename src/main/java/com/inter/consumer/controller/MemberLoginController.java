package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.MemberLoginService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class MemberLoginController {

	@Autowired
	private MemberLoginService memberLoginService;
	
	@RequestMapping("/memberLogin")
	@ResponseBody
	public String memberLogin(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String result = memberLoginService.memberLogin(param);
		return result;
	}
}
