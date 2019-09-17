package com.inter.serverAuth.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.serverAuth.dao.MessageDao;

@Repository
public class MessageDaoImpl implements MessageDao {
	private static final String NAMESPACE = "com.inter.serverAuth.";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public String getResultMessage(Map<String, Object> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getResultMessage", param);
	}
}
