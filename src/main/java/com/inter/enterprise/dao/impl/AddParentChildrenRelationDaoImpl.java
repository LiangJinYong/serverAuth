package com.inter.enterprise.dao.impl;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.AddParentChildrenRelationDao;

@Repository
public class AddParentChildrenRelationDaoImpl implements AddParentChildrenRelationDao {

	private static final String NAMESPACE = "com.inter.enterprise.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "queryAppEnterpriseUserByToken", param);
	}

	public Map<String, Object> selectRelationBySequence(String childSequence) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "selectRelationBySequence", childSequence);
	}

	public void insertParentChildrenRelation(Map<String, Object> relationMap) {
		sqlSessionTemplate.insert(NAMESPACE + "insertParentChildrenRelation", relationMap);
	}

}
