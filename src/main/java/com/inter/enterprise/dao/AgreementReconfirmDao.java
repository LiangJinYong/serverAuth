package com.inter.enterprise.dao;

import java.util.Map;

public interface AgreementReconfirmDao {

	void insertAgreementInfo(Map<String, String> param);

	void updateUserAgreementInfo(Map<String, String> param);

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

}
