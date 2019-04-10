package com.inter.consumer.dao;

import java.util.Map;

public interface TokenCheckDao {

	int getUserCountByIdToken(Map<String, String> param);

}
