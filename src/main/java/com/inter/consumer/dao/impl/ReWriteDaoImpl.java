package com.inter.consumer.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.ReWriteDao;

@Repository
public class ReWriteDaoImpl implements ReWriteDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSession;

	public Integer getOrderNumberBySequence(Long sequence) {
		return orderSqlSession.selectOne(NAMESPACE + "getOrderNumberBySequence", sequence);
	}

	public String queryHomepageAddr(Integer orderNumber) {
		return orderSqlSession.selectOne(NAMESPACE + "queryHomepageAddr", orderNumber);
	}

}
