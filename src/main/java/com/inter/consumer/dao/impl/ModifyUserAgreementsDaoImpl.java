package com.inter.consumer.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.ModifyUserAgreementsDao;

@Repository
public class ModifyUserAgreementsDaoImpl implements ModifyUserAgreementsDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public void insertAgreementInfo(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertAgreementInfo", param);
	}

	public void updateUserAgreementRel(Map<String, String> param) {
		sqlSessionTemplate.update(NAMESPACE + "updateUserAgreementRel", param);
	}

}
