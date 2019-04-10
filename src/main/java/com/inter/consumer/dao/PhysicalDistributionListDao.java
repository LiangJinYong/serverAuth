package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface PhysicalDistributionListDao {

	List<Map<String, Object>> queryPhysicalDistributionInfo(String sequence);

	int getUserCountByIdToken(Map<String, String> param);

}
