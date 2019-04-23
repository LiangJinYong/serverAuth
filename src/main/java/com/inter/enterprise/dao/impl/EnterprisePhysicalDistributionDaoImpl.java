package com.inter.enterprise.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.EnterprisePhysicalDistributionDao;

@Repository
public class EnterprisePhysicalDistributionDaoImpl implements EnterprisePhysicalDistributionDao {
	
	private static final String NAMESPACE = "com.inter.enterprise.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSession;
	
	@Override
	public Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param) {
		
		return orderSqlSession.selectOne(NAMESPACE + "queryAppEnterpriseUserByToken", param);
	}
	
	@Override
	public List<String> selectDescendentSeqsInclusive(String sequence) {
		return orderSqlSession.selectList(NAMESPACE + "selectDescendentSeqsInclusive", sequence);
	}
	
	@Override
	public List<String> selectChildSeqsInclusive(String sequence) {
		return orderSqlSession.selectList(NAMESPACE + "selectChildSeqsInclusive", sequence);
	}
	
	@Override
	public void updateSequenceStatus(Map<String, Object> param) {
		orderSqlSession.update(NAMESPACE + "updateSequenceStatus", param);
	}
	@Override
	public void updateSequenceDates(Map<String, String> paramMap) {
		orderSqlSession.update(NAMESPACE + "updateSequenceDates", paramMap);
	}
	
	@Override
	public String queryManufactureType(Map<String, Object> paramMap) {
		return orderSqlSession.selectOne(NAMESPACE + "queryManufactureType", paramMap);
	}

	@Override
	public void insertParentChildrenRelation(Map<String, Object> paramMap) {
		orderSqlSession.insert(NAMESPACE + "insertParentChildrenRelation", paramMap);
	}

	@Override
	public Map<String, String> getDistInfo(Map<String, Object> paramMap) {
		return orderSqlSession.selectOne(NAMESPACE + "getDistInfo", paramMap);
	}

	@Override
	public void insertLogisticsData(Map<String, Object> paramObj) {
		orderSqlSession.insert(NAMESPACE + "insertLogisticsData", paramObj);
	}

	@Override
	public String isInvokingBlockChain(String firstSeq) {
		return orderSqlSession.selectOne(NAMESPACE + "isInvokingBlockChain", firstSeq);
	}

	@Override
	public void removeParentRelation(Map<String, Object> paramObj) {
		orderSqlSession.update(NAMESPACE + "removeParentRelation", paramObj);
	}

	@Override
	public void removeParentAndDescendentRelation(Map<String, Object> paramObj) {
		orderSqlSession.update(NAMESPACE + "removeParentAndDescendentRelation", paramObj);
	}

	@Override
	public List<String> getAllDescendentSeqs(String sequence) {
		return orderSqlSession.selectList(NAMESPACE + "getAllDescendentSeqs", sequence);
	}

	@Override
	public Map<String, Object> selectBizServiceInfo(String sequence) {
		return orderSqlSession.selectOne(NAMESPACE + "selectBizServiceInfo", sequence);
	}

	@Override
	public String hasParentSeq(String childSeq) {
		return orderSqlSession.selectOne(NAMESPACE + "hasParentSeq", childSeq);
	}

	@Override
	public String queryCurrentLogisticsType(String sequence) {
		return orderSqlSession.selectOne(NAMESPACE + "queryCurrentLogisticsType", sequence);
	}

}
