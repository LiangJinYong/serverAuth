package com.inter.enterprise.dao;

import java.util.Map;

public interface AddParentChildrenRelationDao {

	Map<String, Object> queryAppEnterpriseUserByToken(Map<String, String> param);
	Map<String, Object> selectRelationBySequence(String childSequence);
	void insertParentChildrenRelation(Map<String, Object> relationMap);


}
