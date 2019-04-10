package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.ModifyPwdDao;

@Repository
public class ModifyPwdDaoImpl implements ModifyPwdDao {
	
	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public int queryPwdCount(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryPwdCount", param);
	}

	public void update2NewPwd(Map<String, String> param) {
		sqlSessionTemplate.update(NAMESPACE + "update2NewPwd", param);
	}
}
