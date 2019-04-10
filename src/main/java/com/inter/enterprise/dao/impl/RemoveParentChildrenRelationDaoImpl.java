package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.RemoveParentChildrenRelationDao;

@Repository
public class RemoveParentChildrenRelationDaoImpl implements RemoveParentChildrenRelationDao {

	private static final String NAMESPACE = "com.inter.enterprise.";

	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryAppEnterpriseUserByToken", param);
	}

	public String getParentSequence(String sequence) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getParentSequence", sequence);
	}
	
	public void deleteParentChildrenRelation(String sequence) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteParentChildrenRelation", sequence);
	}

}
