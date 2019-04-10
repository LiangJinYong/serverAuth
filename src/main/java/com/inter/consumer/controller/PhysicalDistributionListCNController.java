package com.inter.consumer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.PhysicalDistributionListCNService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/consumer")
public class PhysicalDistributionListCNController {
	
	@Autowired
	private PhysicalDistributionListCNService physicalDistributionListCNService;
	
	@RequestMapping("/physicalDistributionListCN")
	@ResponseBody
	public String physicalDistributionListCN(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);

		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = physicalDistributionListCNService.physicalDistributionList(param);
		
		return result;
	}
}
