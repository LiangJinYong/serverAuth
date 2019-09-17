package com.inter.serverAuth.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inter.serverAuth.service.ServerAuthService;

@Controller
public class ServerAuthController {

	@Autowired
	private ServerAuthService serverAuthService;
	
	@RequestMapping("/registerAuthKey")
	@ResponseBody
	public String registerAuthKey(HttpServletRequest request) {
		
		String param = request.getParameter("data");
		String result = serverAuthService.registerAuthKey(param);
		return result;
	}
	
	@RequestMapping("/verifyAuthKey")
	@ResponseBody
	public String verifyAuthKey(HttpServletRequest request) {
		
		String param = request.getParameter("data");
		String result = serverAuthService.verifyAuthKey(param);
		return result;
	}
	
	@RequestMapping("/getServerDate")
	@ResponseBody
	public String getServerDate(HttpServletRequest request) {
		
		String param = request.getParameter("data");
		String result = serverAuthService.getServerDate(param);
		return result;
	}
	
	@RequestMapping("/appCheck")
	@ResponseBody
	public String appCheck(HttpServletRequest request) throws IOException {
		
		String param = getBody(request);
		
		String result = serverAuthService.appCheck(param);
		return result;
	}
	
	public static String getBody(HttpServletRequest request) throws IOException {
		 
        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
 
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
 
        body = stringBuilder.toString();
        return body;
    }

}
