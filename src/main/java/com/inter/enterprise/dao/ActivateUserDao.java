package com.inter.enterprise.dao;

import java.util.Map;

public interface ActivateUserDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	Map<String, Object> queryAppEnterpriseUserByEnterpriseUserKey(int enterpriseUserKey);

	void updateUserAuth(Map<String, String> param);

}
