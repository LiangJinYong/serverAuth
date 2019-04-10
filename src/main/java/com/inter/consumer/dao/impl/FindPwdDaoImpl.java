package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.FindPwdDao;

@Repository
public class FindPwdDaoImpl implements FindPwdDao {
	
	private static final String NAMESPACE = "com.inter.consumer.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int getUserIdByLoginId(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserIdByLoginId", param);
	}
	
	public void updatePwd(Map<String, String> param) {
		sqlSessionTemplate.update(NAMESPACE + "updatePwd", param);
	}

	public String getEncrptedKey() {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getEncrptedKey");
	}

}
