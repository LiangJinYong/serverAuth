package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.MessageDao;

@Repository
public class MessageDaoImpl implements MessageDao {
	private static final String NAMESPACE = "com.inter.enterprise.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public String getResultMessage(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getResultMessage", param);
	}

	@Override
	public String getCommonCodeValueName(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getCommonCodeValueName", param);
	}
}
