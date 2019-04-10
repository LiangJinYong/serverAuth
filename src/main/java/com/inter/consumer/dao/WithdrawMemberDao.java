package com.inter.consumer.dao;

import java.util.Map;

public interface WithdrawMemberDao {

	void deleteUserToken(Map<String, String> param);

	void delteUserInfo(Map<String, String> param);

	Integer getUserIdByTokenAndPwd(Map<String, String> param);

}
