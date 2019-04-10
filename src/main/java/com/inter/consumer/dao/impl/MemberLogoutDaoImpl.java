package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.MemberLogoutDao;

@Repository
public class MemberLogoutDaoImpl implements MemberLogoutDao {

	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void deleteUserToken(Map<String, String> param) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteUserToken", param);
	}

}
