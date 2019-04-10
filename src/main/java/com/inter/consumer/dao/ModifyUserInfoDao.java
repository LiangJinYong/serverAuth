package com.inter.consumer.dao;

import java.util.Map;

public interface ModifyUserInfoDao {

	void insertImg(Map<String, Object> paramObj);

	void updateUserImgInfo(Map<String, Object> param);

	void updateUserDtlInfo(Map<String, String> param);

}
