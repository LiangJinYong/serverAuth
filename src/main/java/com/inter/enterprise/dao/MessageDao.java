package com.inter.enterprise.dao;

import java.util.Map;

public interface MessageDao {
	
	String getResultMessage(Map<String, String> param);

	String getCommonCodeValueName(Map<String, String> param);
}
