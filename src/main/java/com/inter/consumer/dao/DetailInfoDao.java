package com.inter.consumer.dao;

import java.util.List;
import java.util.Map;

public interface DetailInfoDao {

	Map<String, Object> getDetailInfo(Map<String, String> param);

	Map<String, Object> getExtendedDetailInfoBySequence(Map<String, String> param);

	Map<String, Object> getOrderInfoBySequence(String sequence);

	List<Map<String, Object>> getDetailInfoTitleList(Map<String, String> param);

	int getUserCountByIdToken(Map<String, String> param);

	int getLogisticsCount(String sequence);

	Map<String, Object> getDetailInfoCN(Map<String, String> param);

	String getHasExpired(String sequence);

}
