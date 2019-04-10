package com.inter.consumer.dao;

import java.util.Map;

public interface LoginIdRepetitionDao {

	int selectLoginIdCount(Map<String, String> param);

}
