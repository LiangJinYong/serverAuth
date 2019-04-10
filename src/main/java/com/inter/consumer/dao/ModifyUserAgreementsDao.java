package com.inter.consumer.dao;

import java.util.Map;

public interface ModifyUserAgreementsDao {

	void insertAgreementInfo(Map<String, String> param);

	void updateUserAgreementRel(Map<String, String> param);

}
