package com.inter.consumer.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.inter.consumer.dao.GetQuestionListDao;

@Repository
public class GetQuestionListDaoImpl implements GetQuestionListDao {

	private static final String NAMESPACE = "com.inter.consumer.";
	@Autowired
	@Qualifier("orderSqlSession")
	private SqlSessionTemplate sqlSessionTemplate;

	public List<Map<String, Object>> queryQuestionAnswerList(String token) {

		return sqlSessionTemplate.selectList(NAMESPACE + "queryQuestionAnswerList", token);
	}

	public int getUserCountByIdToken(Map<String, String> param) {
		return sqlSessionTemplate.selectOne(NAMESPACE + "getUserCountByIdToken", param);
	}

}
