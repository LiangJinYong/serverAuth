package com.inter.enterprise.dao;

import java.util.List;
import java.util.Map;

public interface EnterprisePhysicalDistributionDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	Map<String, String> queryAppPhysicalDistributionType(String sequence);

	Map<String, Integer> queryOrderBySequence(String sequence);

	Map<String, String> queryBizServiceInfo(int orderNumber);

	void updateSequenceStatus(Map<String, Object> param);

	void updateSequenceDates(Map<String, Object> paramMap);

	String queryManufactureType(Map<String, Object> paramMap);

	void updateChildrenSequenceDate(Map<String, Object> paramMap);

	Map<String, Object> selectRelationBySequence(String childSequence);

	void insertParentChildrenRelation(Map<String, Object> paramMap);

	void updateSequenceRelationByChildren(Map<String, Object> paramMap);

	void updateSequenceRelationByChildList(Map<String, Object> paramMap);

	void updateSequenceRelationByParent(Map<String, Object> paramMap);

	Map<String, String> getDistInfo(Map<String, Object> paramMap);

	List<String> selectDescendentSeqsInclusive(String sequence);

	List<String> selectChildSeqsInclusive(String sequence);

	void insertLogisticsData(Map<String, Object> paramObj);

	String isInvokingBlockChain(String firstSeq);

	void removeParentRelation(Map<String, Object> paramObj);

	void removeParentAndDescendentRelation(Map<String, Object> paramObj);

	List<String> getAllDescendentSeqs(String sequence);

	// void insertAppPhysicalDistribution(Map<String, Object> param);
	// void insertSingleAppPhysicalDistribution(Map<String, Object> paramMap);
}
