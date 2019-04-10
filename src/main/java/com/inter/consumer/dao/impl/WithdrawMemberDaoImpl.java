package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.WithdrawMemberDao;

@Repository
public class WithdrawMemberDaoImpl implements WithdrawMemberDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void deleteUserToken(Map<String, String> param) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteUserToken", param);
	}

	public void delteUserInfo(Map<String, String> param) {
		sqlSessionTemplate.delete(NAMESPACE + "delteUserInfo", param);
	}

	public Integer getUserIdByTokenAndPwd(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserIdByTokenAndPwd", param);
	}

}
