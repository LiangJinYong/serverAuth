package com.inter.enterprise.dao;

import java.util.Map;

public interface CreateUserDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	void insertNormalAppEnterpriseUser(Map<String, String> param);

	int getCurrentIdCount(String currentId);

	void insertAgreementInfo(Map<String, String> param);

}
