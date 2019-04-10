package com.inter.consumer.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.consumer.service.SendMailFMSService;

@Controller
@RequestMapping("/consumer")
public class SendMailFMSController {

	@Autowired
	private SendMailFMSService sendMailFMSService;
	
	@RequestMapping("/sendMailFms")
	@ResponseBody
	public String sendMailFms(HttpServletRequest request) throws IOException {
		String mailJson = getMailJson(request);
		String result = sendMailFMSService.sendMailFms(mailJson);
		return result;
	}
	
	private String getMailJson(HttpServletRequest request) throws IOException
	{
	    BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
	    StringBuilder builder = new StringBuilder();
	    String line;    

	    // For every line in the file, append it to the string builder
	    while((line = reader.readLine()) != null)
	    {
	        builder.append(line);
	    }
	    reader.close();
	    
	    return builder.toString();
	}
}
