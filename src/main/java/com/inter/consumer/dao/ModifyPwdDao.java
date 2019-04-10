package com.inter.consumer.dao;

import java.util.Map;

public interface ModifyPwdDao {

	int queryPwdCount(Map<String, String> param);

	void update2NewPwd(Map<String, String> param);

}
