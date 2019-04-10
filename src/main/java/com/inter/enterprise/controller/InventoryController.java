package com.inter.enterprise.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.InventoryService;
import com.inter.util.RequestParamUtil;

@RequestMapping("/enterprise")
@Controller
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
	@RequestMapping("/inventory")
	@ResponseBody
	public String inventory(HttpServletRequest request) {
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(parameterMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = inventoryService.inventory(param);
		return result;
	}
}
