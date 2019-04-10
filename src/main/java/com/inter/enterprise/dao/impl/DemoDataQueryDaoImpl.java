package com.inter.enterprise.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.enterprise.dao.DemoDataQueryDao;

@Repository
public class DemoDataQueryDaoImpl implements DemoDataQueryDao {
	
	private static final String NAMESPACE = "com.inter.enterprise.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate orderSqlSession;
	
	public List<Map<String, Object>> selectDemoData() {
		return orderSqlSession.selectList(NAMESPACE + "selectDemoData");
	}

}
