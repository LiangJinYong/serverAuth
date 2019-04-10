package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.TokenCheckDao;

@Repository
public class TokenCheckDaoImpl implements TokenCheckDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSessionTemplate;
	
	public int getUserCountByIdToken(Map<String, String> param) {
		return orderSqlSessionTemplate.selectOne(NAMESPACE + "getUserCountByIdToken", param);
	}

}
