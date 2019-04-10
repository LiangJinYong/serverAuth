package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.ActivateUserDao;

@Repository
public class ActivateUserDaoImpl implements ActivateUserDao {
	
	private static final String NAMESPACE = "com.inter.enterprise.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryAppEnterpriseUserByToken", param);
	}

	public Map<String, Object> queryAppEnterpriseUserByEnterpriseUserKey(int enterpriseUserKey) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryAppEnterpriseUserByEnterpriseUserKey", enterpriseUserKey);
	}

	public void updateUserAuth(Map<String, String> param) {
		sqlSessionTemplate.update(NAMESPACE + "updateUserAuth", param);
	}

}
