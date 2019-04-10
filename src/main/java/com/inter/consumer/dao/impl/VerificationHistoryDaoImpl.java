package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.VerificationHistoryDao;

@Repository
public class VerificationHistoryDaoImpl implements VerificationHistoryDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSessionTemplate;
	
	public Map<String, Object> getOrderInfo(String sequence) {
		return orderSqlSessionTemplate.selectOne(NAMESPACE + "getOrderInfo", sequence);
	}
	
	public Integer getUserNoByPhoneNumber(String mobilePhoneNumber) {
		return orderSqlSessionTemplate.selectOne(NAMESPACE + "getUserNoByPhoneNumber", mobilePhoneNumber);
	}

	public void insertFailLog(Map<String, String> param) {
		orderSqlSessionTemplate.insert(NAMESPACE + "insertFailLog", param);
	}

	public void insertSuccessLog(Map<String, String> param) {
		orderSqlSessionTemplate.insert(NAMESPACE + "insertSuccessLog", param);
	}
	
	public void insertExtendedDetailInfo(Map<String, String> param) {
		orderSqlSessionTemplate.insert(NAMESPACE + "insertExtendedDetailInfo", param);
	}

	public String getTokenByUserId(String appUserId) {
		return orderSqlSessionTemplate.selectOne(NAMESPACE + "getTokenByUserId", appUserId);
	}

	@Override
	public Map<String, String> getGenderBirthById(String appUserId) {
		return orderSqlSessionTemplate.selectOne(NAMESPACE + "getGenderBirthById", appUserId);
	}

	@Override
	public Map<String, String> getSeqRuleCheckInfo(Map<String, String> param) {
		return orderSqlSessionTemplate.selectOne(NAMESPACE + "getSeqRuleCheckInfo", param);
	}

}
