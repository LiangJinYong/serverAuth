package com.inter.enterprise.dao;

import java.util.List;
import java.util.Map;

public interface EnterprisePhysicalDistributionDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	Map<String, String> queryAppPhysicalDistributionType(String sequence);

	void insertAppPhysicalDistribution(Map<String, Object> param);

	Map<String, Integer> queryOrderBySequence(String sequence);

	Map<String, String> queryBizServiceInfo(int orderNumber);

	List<String> selectChildrenSequence(String sequence);
	
	void updateSequenceStatus(Map<String, Object> paramMap);

	void updateSequenceDates(Map<String, Object> paramMap);

	String queryManufactureType(Map<String, Object> paramMap);

	void updateChildrenSequenceDate(Map<String, Object> paramMap);

	Map<String, Object> selectRelationBySequence(String childSequence);

	void insertParentChildrenRelation(Map<String, Object> paramMap);

	void updateSequenceRelationByChildren(Map<String, Object> paramMap);

	void updateSequenceRelationByChildList(Map<String, Object> paramMap);

	void insertSingleAppPhysicalDistribution(Map<String, Object> paramMap);

	void updateSequenceRelationByParent(Map<String, Object> paramMap);

	Map<String, String> getDistInfo(Map<String, Object> paramMap);

}
