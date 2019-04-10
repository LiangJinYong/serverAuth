package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.SequenceInfoDao;

@Repository
public class SequenceInfoDaoImpl implements SequenceInfoDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<Map<String, Object>> getSequenceInfoList() {
		
		return sqlSessionTemplate.selectList(NAMESPACE + "getSequenceInfoList");
	}

}
