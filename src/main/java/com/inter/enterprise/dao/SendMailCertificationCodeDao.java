package com.inter.enterprise.dao;

import java.util.Map;

public interface SendMailCertificationCodeDao {

	int queryAppEnterpriseCount(Map<String, String> param);

	void insertMailCertificationCode(Map<String, String> param);

}
