package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.TokenCheckDao;

@Repository("enterpriseTokenCheckDao")
public class TokenCheckDaoImpl implements TokenCheckDao {

	private static final String NAMESPACE = "com.inter.enterprise.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSessionTemplate;
	
	public int getUserCountByIdToken(Map<String, String> param) {
		return orderSqlSessionTemplate.selectOne(NAMESPACE + "getUserCountByIdToken", param);
	}

}
