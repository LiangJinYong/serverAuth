package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface GetQuestionListDao {

	List<Map<String, Object>> queryQuestionAnswerList(String token);

	int getUserCountByIdToken(Map<String, String> param);
}
