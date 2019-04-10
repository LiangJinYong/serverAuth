package com.inter.enterprise.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.DemoDataQueryService;

@Controller
@RequestMapping("/enterprise")
public class DemoDataQueryController {

	@Autowired
	private DemoDataQueryService demoDataQueryService;
	
	@RequestMapping("/demoDataQuery")
	@ResponseBody
	public String demoDataQuery(HttpServletRequest request) {
		String result = demoDataQueryService.demoDataQuery();
		return result;
	}
}
