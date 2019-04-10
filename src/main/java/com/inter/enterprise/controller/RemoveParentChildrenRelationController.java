package com.inter.enterprise.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.RemoveParentChildrenRelationService;
import com.inter.util.RequestParamUtil;

@Controller
@RequestMapping("/enterprise")
public class RemoveParentChildrenRelationController {

	@Autowired
	private RemoveParentChildrenRelationService removeParentChildrenRelationService;

	@RequestMapping("/removeParentChildrenRelation")
	@ResponseBody
	public String removeParentChildrenRelation(HttpServletRequest request) {
		Map<String, String[]> paramMap = request.getParameterMap();
		Map<String, String> param = RequestParamUtil.getParamMap(paramMap);
		
		String token = request.getHeader("token");
		param.put("token", token);
		
		String result = removeParentChildrenRelationService.removeParentChildrenRelation(param);
		return result;
	}
}
