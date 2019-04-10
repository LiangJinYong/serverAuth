package com.inter.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class RequestParamUtil {

	private RequestParamUtil() {}
	// convert request parameter type from Map<String, String[]> to Map<String, Stirng>
	
	public static Map<String, String> getParamMap(Map<String, String[]> param) {
		Map<String, String> paramMap = new HashMap<String, String>();
		Set<Entry<String,String[]>> entrySet = param.entrySet();
		for(Entry<String, String[]> entry : entrySet) {
			paramMap.put(entry.getKey(), entry.getValue()[0]);
		}
		
		return paramMap;
	}
}
