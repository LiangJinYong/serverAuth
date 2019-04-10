package com.inter.consumer.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inter.consumer.dao.ReWriteDao;
import com.inter.consumer.service.ReWriteService;
import com.inter.util.AesModule;

@Service
public class ReWriteServiceImpl implements ReWriteService {

	@Autowired
	private ReWriteDao reWriteDao;
	
	public String reWrite(Map<String, String> param) {
		String watermarkKey = param.get("watermarkKey");
		watermarkKey = watermarkKey.replace(" ", "+");
		String sequence = AesModule.aesDecrypt(watermarkKey);
		
		long seq = 0;
		if (sequence.contains("&")) {
			String[] seqSplit = sequence.split("&");
			if (seqSplit != null && seqSplit.length == 2) {
				seq = Long.parseLong(seqSplit[1]);
			}
		} else {
			seq = Long.parseLong(sequence);
		}

		Integer orderNumber = reWriteDao.getOrderNumberBySequence(seq);

		String result = "";
		if (orderNumber != null) {
			String homepageAddr = reWriteDao.queryHomepageAddr(orderNumber);

			if (homepageAddr != null && homepageAddr.length() >=3 && homepageAddr.startsWith("Y|")) {
				homepageAddr = homepageAddr.substring(2);
				result = "<meta http-equiv='refresh' content='0; url=" + homepageAddr + "'>";
			}
		}

		return result;
	}

}
