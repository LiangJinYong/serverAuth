package com.inter.consumer.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inter.consumer.dao.DetailInfoDao;
import com.inter.consumer.service.DetailInfoCNService;
import com.inter.util.ResultMessageUtil;

@Service
public class DetailInfoCNServiceImpl implements DetailInfoCNService {

	@Autowired
	private DetailInfoDao detailInfoDao;
	
	@Autowired
	private ResultMessageUtil messageUtil;

	@Override
	public String detailInfoCN(Map<String, String> param) {

		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = new Gson();

		param.put("countryCode", "CN");
		
		String authCd = param.get("authCd");
		
		String sequence = param.get("sequence");

		Map<String, Object> detailInfo = detailInfoDao.getDetailInfoCN(param);

		List<Map<String, Object>> detailInfoTitleList = detailInfoDao.getDetailInfoTitleList(param);

		List<Map<String, Object>> detailInfoList = new ArrayList<Map<String, Object>>();

		if (detailInfo != null) {

			for (Map<String, Object> detailInfoTitle : detailInfoTitleList) {

				Map<String, Object> tempMap = new HashMap<String, Object>();

				if (detailInfoTitle.get("col_cl").equals("date")) {

					String productExpireDateStr = (String) detailInfo.get("PROD_EXP_DT");
					if (productExpireDateStr != null && !"".equals(productExpireDateStr)
							&& !"9999/12/31".equals(productExpireDateStr)) {

						String expireMsg;
						try {
							DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
							df.setTimeZone(TimeZone.getTimeZone((String) detailInfoTitle.get("timezone")));

							Date codeExpireDate = df.parse(productExpireDateStr);

							Date today = new Date();

							if (codeExpireDate.after(today)) {
								int diff = (int) ((codeExpireDate.getTime() - today.getTime()) / (24 * 60 * 60 * 1000));
								expireMsg = String.valueOf(diff);
								result.put("expireFlag", true);
							} else {
								expireMsg = (String) detailInfoTitle.get("invalid");
								result.put("expireFlag", false);
							}

							tempMap.put("title", detailInfoTitle.get("order_col_nm"));
							tempMap.put("content", expireMsg);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				} else if (detailInfoTitle.get("col_cl").equals("code")) {
					String productVal = (String) detailInfo.get((String) detailInfoTitle.get("order_col"));
					if (productVal != null && !"".equals(productVal.trim())) {
						String[] productValArr = productVal.split("\\^");

						if (productValArr.length == 2) {

							tempMap = new HashMap<String, Object>();
							tempMap.put("title", productValArr[0]);
							tempMap.put("content", productValArr[1]);
						}
					}
				} else if (detailInfoTitle.get("col_cl").equals("sys")) {
					if ("AU00".equals(authCd)) {
						tempMap.put("title", detailInfoTitle.get("order_col_nm"));
						tempMap.put("content", detailInfo.get((String) detailInfoTitle.get("order_col")));
						tempMap.put("titleCode", detailInfoTitle.get("order_col"));
					}
				} else if (detailInfoTitle.get("col_cl").equals("text")
						&& ("LAST_LOCATION".equals(detailInfoTitle.get("order_col"))
								|| "DETECT_COUNT".equals(detailInfoTitle.get("order_col"))
								|| "LAST_DETECT_TIME".equals(detailInfoTitle.get("order_col")))) {
					continue;
				} else if (detailInfoTitle.get("col_cl").equals("sale")) {
					String hasExpired = detailInfoDao.getHasExpired(sequence);
					
					if ("Y".equals(hasExpired)) {
						tempMap.put("title", detailInfoTitle.get("order_col_nm"));
						tempMap.put("content", detailInfoTitle.get("col_dtl_nm"));
					}
				} else {
					tempMap.put("title", detailInfoTitle.get("order_col_nm"));
					tempMap.put("content", detailInfo.get((String) detailInfoTitle.get("order_col")));
				}

				detailInfoList.add(tempMap);
			}

			Map<String, Object> extendedDetailInfo = detailInfoDao.getExtendedDetailInfoBySequence(param);
			if (extendedDetailInfo != null) {
				result.put("detectCount", extendedDetailInfo.get("detectCount"));
				result.put("lastAddress", extendedDetailInfo.get("lastAddress"));
				result.put("lastDetectTime", extendedDetailInfo.get("lastDetectTime"));
			}

			result.put("paramColor", detailInfo.get("PARAM_COLOR"));

			String textColor = (String) detailInfo.get("TEXT_COLOR");

			if ("".equals(textColor)) {
				textColor = "0";
			}
			result.put("textColor", textColor);

			String foodNutritionPath = (String) detailInfo.get("FNI_FILE_UUID_URL");
			String rawMaterialPath = (String) detailInfo.get("RMI_FILE_UUID_URL");
			String bizLogoPath = (String) detailInfo.get("BIZ_LOGO_UUID_URL");
			if (foodNutritionPath != null) {
				result.put("foodNutritionPath", param.get("urlHeader") + foodNutritionPath);
			} else {
				result.put("foodNutritionPath", "");
			}

			if (rawMaterialPath != null) {
				result.put("rawMaterialPath", param.get("urlHeader") + rawMaterialPath);
			} else {
				result.put("rawMaterialPath", "");
			}

			if (bizLogoPath != null) {
				result.put("bizLogoPath", param.get("urlHeader") + bizLogoPath);
			} else {
				result.put("bizLogoPath", "");
			}

			int logisticsCount = detailInfoDao.getLogisticsCount(param.get("sequence"));
			result.put("logisticsCount", logisticsCount);

			result.put("detail", detailInfoList);

			result.put("resultCode", 200);
		} else {
			result.put("resultCode", 415);
		}

		messageUtil.addResultMsg(param, result);
		
		return gson.toJson(result);
	}

}
