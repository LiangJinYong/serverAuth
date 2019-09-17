package com.inter.serverAuth.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inter.serverAuth.dao.ServerAuthDao;

@Repository
public class ServerAuthDaoImpl implements ServerAuthDao {
	
	private static final String NAMESPACE = "com.inter.serverAuth.";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public String getDecryptedParam(String param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getDecryptedParam", param);
	}
	
	@Override
	public void insertAuthKey(Map<String, Object> paramMap) {
		sqlSessionTemplate.insert(NAMESPACE + "insertAuthKey", paramMap);
	}

	@Override
	public void insertAuthLog(Map<String, Object> paramMap) {
		sqlSessionTemplate.insert(NAMESPACE + "insertAuthLog", paramMap);
	}

	@Override
	public int getSecurityKeyCount(String securityKey) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getSecurityKeyCount", securityKey);
	}

	@Override
	public String isValidSecurityKey(String securityKey) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "isValidSecurityKey", securityKey);
	}

	@Override
	public String getStatusCode(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getStatusCode", paramMap);
	}

	@Override
	public void insertEasySamplerLog(Map<String, Object> paramMap) {
		sqlSessionTemplate.insert(NAMESPACE + "insertEasySamplerLog", paramMap);
	}

	@Override
	public void insertAppCheckLog(Map<String, Object> paramMap) {
		sqlSessionTemplate.insert(NAMESPACE + "insertAppCheckLog", paramMap);
	}
}
