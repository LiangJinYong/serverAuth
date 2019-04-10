package com.inter.enterprise.dao;

import java.util.Map;

public interface RemoveParentChildrenRelationDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);

	String getParentSequence(String sequence);

	void deleteParentChildrenRelation(String sequence);

}
