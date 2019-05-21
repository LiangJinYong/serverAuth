package com.inter.enterprise.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.EnterprisePhysicalDistributionService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/enterprise")
public class EnterprisePhysicalDistributionController {

	@Autowired
	private EnterprisePhysicalDistributionService enterprisePhysicalDistributionService;

	@RequestMapping("/getReleaseListInfo")
	@ResponseBody
	public String getReleaseListInfo(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = enterprisePhysicalDistributionService.getReleaseListInfo(param);
		return result;
	}
	
	@RequestMapping("/physicalDistribution")
	@ResponseBody
	public String physicalDistribution(HttpServletRequest request) {
		
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String sender = request.getHeader("sender");
		
		if ("301".equals(sender)) {
			param.put("FMS", "true");
		}
		
		String result = enterprisePhysicalDistributionService.physicalDistribution(param);
		return result;
	}
}
