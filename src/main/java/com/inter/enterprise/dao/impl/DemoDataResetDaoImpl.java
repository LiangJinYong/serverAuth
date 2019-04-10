package com.inter.enterprise.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.DemoDataResetDao;

@Repository
public class DemoDataResetDaoImpl implements DemoDataResetDao {
	
	private static final String NAMESPACE = "com.inter.enterprise.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSession;
	
	public List<Map<String, Long>> getRelationSequences() {
		return orderSqlSession.selectList(NAMESPACE + "getRelationSequences");
	}

	public void deletePhisicalDistributionData(Map<String, Object> param) {
		orderSqlSession.delete(NAMESPACE + "deletePhisicalDistributionData", param);
		
	}
	
	public void resetSequenceStatus(Map<String, Object> param) {
		orderSqlSession.update(NAMESPACE + "resetSequenceStatus", param);
		
	}

	public void deleteSequenceRelations() {
		orderSqlSession.delete(NAMESPACE + "deleteSequenceRelations");
	}
}
