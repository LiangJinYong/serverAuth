package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.RecentDetectionListDao;

@Repository
public class RecentDetectionListDaoImpl implements RecentDetectionListDao {
	
	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int getUserCountByIdToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserCountByIdToken", param);
	}

	public List<Map<String, Object>> getSucDetectionList(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getSucDetectionList", param);
	}

	public List<Map<String, Object>> getFailDetectionList(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "getFailDetectionList", param);
	}

}
