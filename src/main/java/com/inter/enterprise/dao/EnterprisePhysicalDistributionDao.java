package com.inter.enterprise.dao;

import java.util.List;
import java.util.Map;

public interface EnterprisePhysicalDistributionDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);
	
	List<Map<String, Object>> getReleaseList();

	void updateSequenceStatus(Map<String, Object> param);

	String queryManufactureType(Map<String, Object> paramMap);

	void insertParentChildrenRelation(Map<String, Object> paramMap);

	Map<String, String> getDistInfo(Map<String, Object> paramMap);

	List<String> selectDescendentSeqsInclusive(String sequence);

	List<String> selectChildSeqsInclusive(String sequence);

	void insertLogisticsData(Map<String, Object> paramObj);

	String isInvokingBlockChain(String firstSeq);

	void removeParentRelation(Map<String, Object> paramObj);

	void removeParentAndDescendentRelation(Map<String, Object> paramObj);

	List<String> getAllDescendentSeqs(String sequence);

	Map<String, Object> selectBizServiceInfo(String sequence);

	String hasParentSeq(String childSeq);

	String queryCurrentLogisticsType(String sequence);
	
	void updateSequenceDates(Map<String, String> paramMap);

	Map<String, Object> getReleaseAddressInfoByReleaseId(String releaseId);

	Map<String, Object> getEnterpriseAddressInfoByUserKey(Map<String, String> param);

	Map<String, Object> getEnterpriseAddressInfoByToken(Map<String, String> param);

}
