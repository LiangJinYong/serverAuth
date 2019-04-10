package com.inter.enterprise.dao;

import java.util.List;
import java.util.Map;

public interface DemoDataResetDao {

	List<Map<String, Long>> getRelationSequences();

	void deletePhisicalDistributionData(Map<String, Object> param);

	void resetSequenceStatus(Map<String, Object> param);

	void deleteSequenceRelations();

}
