package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.DetailInfoService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class DetailInfoController {

	@Autowired
	private DetailInfoService detailInfoService;

	@RequestMapping("/detailInfo")
	@ResponseBody
	public String detailInfo(HttpServletRequest request) {
		
		String requestURL = request.getRequestURL().toString();
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);

		String token = request.getHeader("token");
		param.put("token", token);
		
		int serverPort = request.getServerPort();
		
		param.put("port", String.valueOf(serverPort));
		String[] split = requestURL.split(String.valueOf(serverPort));
		
		param.put("urlHeader", split[0] + serverPort);
		
		String result = detailInfoService.detailInfo(param);
		
		return result;
	}
}
