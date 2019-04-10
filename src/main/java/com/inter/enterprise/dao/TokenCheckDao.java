package com.inter.enterprise.dao;

import java.util.Map;

public interface TokenCheckDao {

	int getUserCountByIdToken(Map<String, String> param);

}
