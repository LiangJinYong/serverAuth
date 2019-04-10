package com.inter.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author user
 * Get China's local time
 */
public class GetTimeUtil {

	private GetTimeUtil() {
	}

	public static String getTime() {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date();

		return df.format(curDate);
	}
	
	public static String getSpecifiedTimeUnit(String pattern) {

		DateFormat df = new SimpleDateFormat(pattern);
		Date curDate = new Date();

		return df.format(curDate);
	}
}
