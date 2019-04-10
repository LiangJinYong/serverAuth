package com.inter.consumer.service.impl;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.GetSpecifiedSequenceDao;
import com.inter.consumer.dao.MemberLoginDao;
import com.inter.consumer.dao.ModifyUserInfoDao;
import com.inter.consumer.service.ModifyUserInfoService;
import com.inter.util.ResultMessageUtil;

@Service("modifyUserInfoServiceForConsumer")
public class ModifyUserInfoServiceImpl implements ModifyUserInfoService {

	@Autowired
	private ModifyUserInfoDao modifyUserInfoDao;

	@Autowired
	private ResultMessageUtil messageUtil;
	
	@Autowired
	private MemberLoginDao memberLoginDao;

	@Autowired
	private GetSpecifiedSequenceDao getSpecifiedSequenceDao;

	@Transactional
	public String modifyUserInfo(Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		Integer userId = memberLoginDao.getUserIdByToken(param.get("token"));

		if (userId == null || !String.valueOf(userId).equals(param.get("appUserId"))) {
			result.put("resultCode", 403);

			messageUtil.addResultMsg(param, result);
			
			return gson.toJson(result);
		}

		String img = param.get("img");
		Map<String, Object> paramObj = new HashMap<String, Object>();
		paramObj.putAll(param);

		try {
			if (img != null && !"".equals(img)) {

				byte[] imgBytes = Base64.getDecoder().decode(img);
				int imgId = getSpecifiedSequenceDao.getSpecifiedSequence("img_id");
				paramObj.put("img", imgBytes);
				paramObj.put("imgId", imgId);

				modifyUserInfoDao.insertImg(paramObj);
				modifyUserInfoDao.updateUserImgInfo(paramObj);
			} else {
				modifyUserInfoDao.updateUserDtlInfo(param);
			}

			result.put("resultCode", 200);
		} catch (Exception e) {
			result.put("resultCode", 501);
		}
		
		messageUtil.addResultMsg(param, result);

		return gson.toJson(result);
	}

}
