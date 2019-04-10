package com.inter.enterprise.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.EnterprisePhysicalDistributionHistoryListService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/enterprise")
public class EnterprisePhysicalDistributionHistoryListController {

	@Autowired
	private EnterprisePhysicalDistributionHistoryListService enterprisePhysicalDistributionHistoryListService;

	@RequestMapping("/physicalDistributionHistoryList")
	@ResponseBody
	public String physicalDistributionHistoryList(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = enterprisePhysicalDistributionHistoryListService.physicalDistributionHistoryList(param);
		return result;
	}
}
