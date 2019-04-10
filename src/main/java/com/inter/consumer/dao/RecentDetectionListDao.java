package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface RecentDetectionListDao {

	int getUserCountByIdToken(Map<String, String> param);

	List<Map<String, Object>> getSucDetectionList(Map<String, String> param);

	List<Map<String, Object>> getFailDetectionList(Map<String, String> param);

}
