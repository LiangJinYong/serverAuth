package com.inter.consumer.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.inter.consumer.dao.VerificationHistoryDao;
import com.inter.consumer.service.VerificationHistoryService;

@Service
public class VerificationHistoryServiceImpl implements VerificationHistoryService {

	@Autowired
	private VerificationHistoryDao verificationHistoryDao;
	
	@Transactional
	public String verificationHistory(Map<String, String> param) {
		
 		Map<String, Object> result = new HashMap<String, Object>();
		
		Gson gson = new Gson();
		
		String appUserId = param.get("appUserId");
		
		if (appUserId == null || "".equals(appUserId) || "0".equals(appUserId)) {
			param.put("appUserId", "0");
		} else {
			String token = verificationHistoryDao.getTokenByUserId(appUserId);
			
			if (token == null) {
				result.put("logType", "token expired");
				return gson.toJson(result);
			}
			
			Map<String, String> userInfo = verificationHistoryDao.getGenderBirthById(appUserId);
			if (userInfo != null) {
				param.putAll(userInfo);
			}
		}
		
		String locationCd = param.get("locationCd");
		
		if (locationCd != null && locationCd.length() > 2) {
			locationCd = locationCd.substring(0, 2);
			param.put("locationCd", locationCd);
		}
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		try {
			date = df.parse(param.get("currentDatetime"));
			
			df = new SimpleDateFormat("yyyyMMdd");
			String ymd = df.format(date);
			df = new SimpleDateFormat("yyyy");
			String year = df.format(date);
			df = new SimpleDateFormat("MM");
			String month = df.format(date);
			df = new SimpleDateFormat("dd");
			String day = df.format(date);
			
			param.put("currentDatetime", param.get("currentDatetime"));
			param.put("ymd", ymd);
			param.put("year", year);
			param.put("month", month);
			param.put("day", day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String province = param.get("province");
		String city = param.get("city");
		String district = param.get("district");
		
		if (province != null) {
			province = province.replaceAll(",", "");
		}
		if (city != null) {
			city = city.replaceAll(",", "");
		}
		if (district != null) {
			district = district.replaceAll(",", "");
		}
		
		param.put("province", province);
		param.put("city", city);
		param.put("district", district);
		
		if (param.get("fullAddress") != null && param.get("fullAddress").trim().length() == 0) {
			param.put("fullAddress", "999");
			param.put("province", "999");
			param.put("city", "999");
			param.put("district", "999");
		}
		
		if (param.get("countryCode") == null || "".equals(param.get("countryCode"))) {
			param.put("countryCode", "ZZ");
		}
		
		String isSuccess = param.get("isSuccess");
		
		if ("1".equals(isSuccess)) {
			String sequence = param.get("sequence");
			
			Map<String, Object> orderInfo = verificationHistoryDao.getOrderInfo(sequence);
			
			if (orderInfo == null) {
				param.put("failReasonCode", "F02");
				verificationHistoryDao.insertFailLog(param);
				
				result.put("logType", "No such order");
			} else {
				param.put("orderNum", orderInfo.get("orderNum").toString());
				param.put("prodTypeCd", orderInfo.get("prodTypeCd").toString());
				verificationHistoryDao.insertSuccessLog(param);
				
				// when real success, insert or update detect count and last location
				verificationHistoryDao.insertExtendedDetailInfo(param);
				
				Map<String, String> seqRuleCheckInfo = verificationHistoryDao.getSeqRuleCheckInfo(param);
				
				if (seqRuleCheckInfo != null && seqRuleCheckInfo.get("ruleCheckCode").startsWith("A")) {
					result.putAll(seqRuleCheckInfo);
				}
				
				result.put("logType", "success");
			}
		} else {
			param.put("failReasonCode", "F01");
			verificationHistoryDao.insertFailLog(param);
			
			result.put("logType", "timeout");
		}
		
		return gson.toJson(result);
	}

}
