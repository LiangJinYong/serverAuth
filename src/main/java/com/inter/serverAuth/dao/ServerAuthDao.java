package com.inter.serverAuth.dao;

import java.util.Map;

public interface ServerAuthDao {

	String getDecryptedParam(String param);
	
	void insertAuthKey(Map<String, Object> paramMap);

	void insertAuthLog(Map<String, Object> paramMap);

	int getSecurityKeyCount(String securityKey);

	String isValidSecurityKey(String securityKey);

	String getStatusCode(Map<String, Object> paramMap);

	void insertEasySamplerLog(Map<String, Object> paramMap);

	void insertAppCheckLog(Map<String, Object> paramMap);

}
