package com.inter.consumer.dao;

import java.util.Map;

public interface RegistrationQuestionDao {

	void insertAppQuestion(Map<String, String> param);

	Integer getUserCountByIdToken(Map<String, String> param);

}
