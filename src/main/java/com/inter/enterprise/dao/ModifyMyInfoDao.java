package com.inter.enterprise.dao;

import java.util.Map;

public interface ModifyMyInfoDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	void updateAppEnterpriseUser(Map<String, String> param);

}
