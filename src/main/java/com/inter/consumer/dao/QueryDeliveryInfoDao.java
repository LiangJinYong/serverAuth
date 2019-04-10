package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface QueryDeliveryInfoDao {

	int getUserCountByIdToken(Map<String, String> param);

	List<Map<String, Object>> selectDeliveryInfo(Map<String, String> param);

}
