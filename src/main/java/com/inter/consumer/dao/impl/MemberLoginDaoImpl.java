package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.MemberLoginDao;

@Repository
public class MemberLoginDaoImpl implements MemberLoginDao {

	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public Integer getUserIdByToken(String token) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserIdByToken", token);
	}

	public Integer getUserIdByIdPw(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserIdByIdPw", param);
	}

	public Map<String, Object> getUserInfoById(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserInfoById", param);
	}

	public void deleteTokenByUserId(Integer userId) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteTokenByUserId", userId);
	}

	public void insertTokenInfo(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertTokenInfo", param);
	}

	@Override
	public Map<String, Object> checkUserExistence(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "checkUserExistence", param);
	}

}
