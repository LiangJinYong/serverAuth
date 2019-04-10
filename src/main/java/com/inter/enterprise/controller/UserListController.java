package com.inter.enterprise.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.enterprise.service.UserListService;

@Controller
@RequestMapping("/enterprise")
class UserListController {

	@Autowired
	private UserListService userListService;

	@RequestMapping("/userList")
	@ResponseBody
	public String userList(HttpServletRequest request) {
		String token = request.getHeader("token");

		Map<String, String> param = new HashMap<String, String>();
		param.put("token", token);

		String result = userListService.userList(param);
		return result;
	}
}
