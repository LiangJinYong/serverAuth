package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.DetailInfoDao;

@Repository
public class DetailInfoDaoImpl implements DetailInfoDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSession;
	
	public Map<String, Object> getDetailInfo(Map<String, String> param) {
		return orderSqlSession.selectOne(NAMESPACE + "getDetailInfo", param);
	}

	public Map<String, Object> getExtendedDetailInfoBySequence(Map<String, String> param) {
		return orderSqlSession.selectOne(NAMESPACE + "getExtendedDetailInfoBySequence", param);
	}

	public Map<String, Object> getOrderInfoBySequence(String sequence) {
		return orderSqlSession.selectOne(NAMESPACE + "getOrderInfoBySequence", sequence);
	}

	public List<Map<String, Object>> getDetailInfoTitleList(Map<String, String> param) {
		return orderSqlSession.selectList(NAMESPACE + "getDetailInfoTitleList", param);
	}

	public int getUserCountByIdToken(Map<String, String> param) {
		return orderSqlSession.selectOne(NAMESPACE + "getUserCountByIdToken", param);
	}

	public int getLogisticsCount(String sequence) {
		return orderSqlSession.selectOne(NAMESPACE + "getLogisticsCount", sequence);
	}

	@Override
	public Map<String, Object> getDetailInfoCN(Map<String, String> param) {
		return orderSqlSession.selectOne(NAMESPACE + "getDetailInfoCN", param);
	}

	@Override
	public String getHasExpired(String sequence) {
		return orderSqlSession.selectOne(NAMESPACE + "getHasExpired", sequence);
	}

}
