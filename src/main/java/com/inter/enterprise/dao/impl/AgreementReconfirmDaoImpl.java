package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.AgreementReconfirmDao;

@Repository
public class AgreementReconfirmDaoImpl implements AgreementReconfirmDao {
private static final String NAMESPACE = "com.inter.enterprise.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public void insertAgreementInfo(Map<String, String> param) {
		sqlSessionTemplate.insert(NAMESPACE + "insertAgreementInfo", param);
	}

	public void updateUserAgreementInfo(Map<String, String> param) {
		sqlSessionTemplate.update(NAMESPACE + "updateUserAgreementInfo", param);
	}

	public Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryAppEnterpriseUserByToken", param);
	}

}
