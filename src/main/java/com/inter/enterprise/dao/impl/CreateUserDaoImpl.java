package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.CreateUserDao;

@Repository
public class CreateUserDaoImpl implements CreateUserDao {
	
	private static final String NAMESPACE = "com.inter.enterprise.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryAppEnterpriseUserByToken", param);
	}

	public void insertNormalAppEnterpriseUser(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertNormalAppEnterpriseUser", param);
	}

	public int getCurrentIdCount(String currentId) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getCurrentIdCount", currentId);
	}

	public void insertAgreementInfo(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertAgreementInfo", param);
	}
}
