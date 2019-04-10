package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.RegisterMemberDao;

@Repository
public class RegisterMemberDaoImpl implements RegisterMemberDao {
	
private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public void insertAgreementInfo(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertAgreementInfo", param);
	}

	public void insertAppUserInfo(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertAppUserInfo", param);
	}

	public void insertTokenInfo(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertTokenInfo", param);
	}

	@Override
	public void insertImg(Map<String, Object> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertImg", param);
	}

	@Override
	public void updateUserImgInfo(Map<String, Object> param) {
		sqlSessionTemplate.update(NAMESPACE + "updateUserImgInfo", param);
	}

}
