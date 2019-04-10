package com.inter.enterprise.dao;

import java.util.List;
import java.util.Map;

public interface EnterprisePhysicalDistributionListDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	List<Map<String, Object>> queryPhysicalDistributionList(Map<String, String> param);

}
