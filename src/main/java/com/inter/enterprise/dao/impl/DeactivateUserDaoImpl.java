package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.DeactivateUserDao;

@Repository
public class DeactivateUserDaoImpl implements DeactivateUserDao {

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

	public void updateUserAuthDeactivate(Map<String, String> param) {
		sqlSessionTemplate.update(NAMESPACE + "updateUserAuthDeactivate", param);
	}

}
