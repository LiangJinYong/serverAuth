package com.inter.enterprise.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.DemoDataResetService;

@Controller
@RequestMapping("/enterprise")
public class DemoDataResetController {

	@Autowired
	private DemoDataResetService demoDataResetService;
	
	@RequestMapping("/demoDataReset")
	@ResponseBody
	public String demoDataReset(HttpServletRequest request) {
		String result = demoDataResetService.demoDataReset();
		return result;
	}
}
