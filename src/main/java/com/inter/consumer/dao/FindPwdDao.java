package com.inter.consumer.dao;

import java.util.Map;

public interface FindPwdDao {

	void updatePwd(Map<String, String> param);

	int getUserIdByLoginId(Map<String, String> param);

	String getEncrptedKey();

}
