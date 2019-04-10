package com.inter.consumer.dao;

import java.util.Map;

public interface MemberLogoutDao {

	void deleteUserToken(Map<String, String> param);

}
