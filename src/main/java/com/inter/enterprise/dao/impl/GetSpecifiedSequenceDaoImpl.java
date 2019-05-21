package com.inter.enterprise.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.GetSpecifiedSequenceDao;

@Repository
public class GetSpecifiedSequenceDaoImpl implements GetSpecifiedSequenceDao {

	private static final String NAMESPACE = "com.inter.enterprise.";
	
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int getSpecifiedSequence(String sequenceName) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getSpecifiedSequence", sequenceName);
	}

}
