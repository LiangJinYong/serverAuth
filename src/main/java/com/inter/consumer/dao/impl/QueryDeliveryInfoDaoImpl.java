package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.QueryDeliveryInfoDao;

@Repository
public class QueryDeliveryInfoDaoImpl implements QueryDeliveryInfoDao {

	private static final String NAMESPACE = "com.inter.consumer.";

	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int getUserCountByIdToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserCountByIdToken", param);
	}

	@Override
	public List<Map<String, Object>> selectDeliveryInfo(Map<String, String> param) {
		return sqlSessionTemplate.selectList(NAMESPACE + "selectDeliveryInfo", param);
	}

}
