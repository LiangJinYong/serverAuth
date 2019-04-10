package com.inter.enterprise.dao;

import java.util.Map;

public interface EnterpriseVersionCheckDao {

	Map<String, Object> getVersionCheckInfo(Map<String, String> param);

	String getEncrptedKey();

}
