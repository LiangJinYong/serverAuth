package com.inter.enterprise.dao;

import java.util.Map;

public interface ModifyUserInfoDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	Map<String, Object> queryAppEnterpriseUserByEnterpriseUserKey(int enterpriseUserKey);

	void updateUserInfo(Map<String, String> param);

}
