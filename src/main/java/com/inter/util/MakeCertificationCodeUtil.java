package com.inter.util;

import java.util.Random;

public class MakeCertificationCodeUtil {

	private MakeCertificationCodeUtil() {
	}

	public static String makeCertificationCode() {
		StringBuilder sb = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 6; i++) {
			int randomInt = random.nextInt(10);
			sb.append(randomInt);
		}

		return sb.toString();
	}
}
