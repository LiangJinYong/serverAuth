package com.inter.enterprise.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.UserListDao;

@Repository
public class UserListDaoImpl implements UserListDao {
	
	private static final String NAMESPACE = "com.inter.enterprise.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param) {
		
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryAppEnterpriseUserByToken", param);
	}

	public List<Map<String, Object>> queryUserList(int enterpriseKey) {
		
		return sqlSessionTemplate.selectList(NAMESPACE + "queryUserList", enterpriseKey);
	}

	
}
