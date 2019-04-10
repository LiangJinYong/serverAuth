package com.inter.consumer.dao;

import java.util.Map;

public interface RegisterMemberDao {

	void insertAgreementInfo(Map<String, String> param);

	void insertAppUserInfo(Map<String, String> param);

	void insertTokenInfo(Map<String, String> param);

	void insertImg(Map<String, Object> param);

	void updateUserImgInfo(Map<String, Object> param);

}
