package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.RegisterDeliveryInfoDao;

@Repository
public class RegisterDeliveryInfoDaoImpl implements RegisterDeliveryInfoDao {
	
	private static final String NAMESPACE = "com.inter.enterprise.";

	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryAppEnterpriseUserByToken", param);
	}
	
	@Override
	public void insertDeliveryInfo(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertDeliveryInfo", param);
	}

	@Override
	public Map<String, Object> getEnterpriseInfo(String enterpriseUserKey) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getEnterpriseInfo", enterpriseUserKey);
	}

	@Override
	public String isInvokingBlockChainForDelivery(String deliveryId) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "isInvokingBlockChainForDelivery", deliveryId);
	}
	
}
