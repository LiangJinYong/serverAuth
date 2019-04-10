package com.inter.enterprise.dao;

import java.util.Map;

public interface RegisterDeliveryInfoDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);
	
	void insertDeliveryInfo(Map<String, String> param);

	Map<String, Object> getEnterpriseInfo(String enterpriseUserKey);

}
