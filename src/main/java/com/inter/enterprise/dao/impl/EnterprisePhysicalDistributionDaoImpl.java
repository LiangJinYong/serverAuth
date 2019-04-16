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
	public Map<String, String> queryAppPhysicalDistributionType(String sequence) {
		return orderSqlSession.selectOne(NAMESPACE + "queryAppPhysicalDistributionType", sequence);
	}
	
	@Override
	public Map<String, Integer> queryOrderBySequence(String sequence) {
		return orderSqlSession.selectOne(NAMESPACE + "queryOrderBySequence", sequence);
	}
	
	@Override
	public Map<String, String> queryBizServiceInfo(int orderNumber) {
		return orderSqlSession.selectOne(NAMESPACE + "queryBizServiceInfo", orderNumber);
	}
	
	@Override
	public List<String> selectDescendentSeqsInclusive(String sequence) {
		return orderSqlSession.selectList(NAMESPACE + "selectDescendentSeqsInclusive", sequence);
	}
	
	@Override
	public List<String> selectChildSeqsInclusive(String sequence) {
		return orderSqlSession.selectList(NAMESPACE + "selectChildSeqsInclusive", sequence);
	}
	
//	@Override
//	public void insertAppPhysicalDistribution(Map<String, Object> param) {
//		orderSqlSession.insert(NAMESPACE + "insertAppPhysicalDistribution", param);
//	}
	
	@Override
	public void updateSequenceStatus(Map<String, Object> param) {
		orderSqlSession.update(NAMESPACE + "updateSequenceStatus", param);
	}
	@Override
	public void updateSequenceDates(Map<String, Object> paramMap) {
		orderSqlSession.update(NAMESPACE + "updateSequenceDates", paramMap);
	}
	
	@Override
	public String queryManufactureType(Map<String, Object> paramMap) {
		return orderSqlSession.selectOne(NAMESPACE + "queryManufactureType", paramMap);
	}

	@Override
	public void updateChildrenSequenceDate(Map<String, Object> paramMap) {
		orderSqlSession.update(NAMESPACE + "updateChildrenSequenceDate", paramMap);
	}

	@Override
	public Map<String, Object> selectRelationBySequence(String childSequence) {
		return orderSqlSession.selectOne(NAMESPACE + "selectRelationBySequence", childSequence);
	}

	@Override
	public void insertParentChildrenRelation(Map<String, Object> paramMap) {
		orderSqlSession.insert(NAMESPACE + "insertParentChildrenRelation", paramMap);
	}

	@Override
	public void updateSequenceRelationByChildren(Map<String, Object> paramMap) {
		orderSqlSession.update(NAMESPACE + "updateSequenceRelationByChildren", paramMap);
	}

	@Override
	public void updateSequenceRelationByChildList(Map<String, Object> paramMap) {
		orderSqlSession.update(NAMESPACE + "updateSequenceRelationByChildList", paramMap);
	}

//	@Override
//	public void insertSingleAppPhysicalDistribution(Map<String, Object> paramMap) {
//		orderSqlSession.insert(NAMESPACE + "insertSingleAppPhysicalDistribution", paramMap);
//	}

	@Override
	public void updateSequenceRelationByParent(Map<String, Object> paramMap) {
		orderSqlSession.update(NAMESPACE + "updateSequenceRelationByParent", paramMap);
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

}
