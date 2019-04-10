package com.inter.enterprise.dao;

import java.util.Map;

public interface DeactivateUserDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	Map<String, Object> queryAppEnterpriseUserByEnterpriseUserKey(int enterpriseUserKey);

	void updateUserAuthDeactivate(Map<String, String> param);

}
