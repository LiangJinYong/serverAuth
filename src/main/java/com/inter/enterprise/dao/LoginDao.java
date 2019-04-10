package com.inter.enterprise.dao;

import java.util.Map;

public interface LoginDao {

	Map<String, Object> queryAppEnterpriseUserById(Map<String, String> param);

	void updateToken(Map<String, String> param);

	Map<String, Object> getDeliveryInfo(Integer enterpriseUserKey);

}
