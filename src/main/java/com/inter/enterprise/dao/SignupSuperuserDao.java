package com.inter.enterprise.dao;

import java.util.Map;

public interface SignupSuperuserDao {

	void insertAppEnterprise(Map<String, String> param);

	void insertAppEnterpriseUser(Map<String, String> param);

	int getCurrentMailCount(String currentMail);

	int getCurrentIdCount(String currentId);

	void insertAgreementInfo(Map<String, String> param);

}
