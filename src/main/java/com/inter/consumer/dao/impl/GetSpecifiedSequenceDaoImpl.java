package com.inter.consumer.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.GetSpecifiedSequenceDao;

@Repository
public class GetSpecifiedSequenceDaoImpl implements GetSpecifiedSequenceDao {
	private static final String NAMESPACE = "com.inter.consumer.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public int getSpecifiedSequence(String sequenceName) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getSpecifiedSequence", sequenceName);
	}

}
