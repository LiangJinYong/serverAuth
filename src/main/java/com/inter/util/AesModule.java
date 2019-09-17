package com.inter.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AesModule {
	private static final String key = "BoychaERikAjh1JJ";
	private static final String ivKey = "Iris12aSUkAC8sae";

	// data encrypt
	public static String aesEncrypt(String text, String key) {

		byte[] sKey = null;
		byte[] bText = null;
		byte[] encrypted = null;
		String result = "";
		// key = getAesKey();
		
		try {
			sKey = key.getBytes("UTF-8");
			bText = text.getBytes("UTF-8");

			// AES/ECB/PKCS5Padding
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(sKey, "AES"));
			encrypted = cipher.doFinal(bText);
			result = toHexString(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static String Decrypt(String text, String key) {
	      
	      byte[] sKey = null;
	      byte[] bText = null;
	        byte[] decrypted = null;
	        String result = "";
	      //  key = getAesKey();
	        
	        try {
	           sKey = key.getBytes("UTF-8");
	           bText = hexToByte(text);
	            // AES/ECB/PKCS5Padding
	            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sKey, "AES"));
	            decrypted = cipher.doFinal(bText);
	            result = new String(decrypted, "UTF-8");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	   }

	public static String DecryptKey(String text) {

		byte[] sKey = null;
		byte[] bText = null;
		byte[] decrypted = null;
		String result = "";
		String dec_key = "ICS2018KORVERSEQ";

		try {
			sKey = dec_key.getBytes("UTF-8");
			bText = hexToByte(text);
			// AES/ECB/PKCS5Padding
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(sKey, "AES"));
			decrypted = cipher.doFinal(bText);
			result = new String(decrypted, "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static byte[] hexToByte(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;

		}

		byte[] byteArray = new byte[hex.length() / 2];

		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);

		}
		return byteArray;
	}

	public static String toHexString(byte[] encrypted) {
		if (encrypted == null || encrypted.length == 0) {
			return null;

		}

		StringBuffer sb = new StringBuffer(encrypted.length * 2);

		String hexNumber;

		for (int x = 0; x < encrypted.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & encrypted[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));

		}

		return sb.toString();
	}

	// QR decrypt
	public static String aesDecrypt(String text) {

		String result = "";

		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] keyBytes = new byte[16];
			byte[] b = key.getBytes("UTF-8");

			byte[] ivBytes = new byte[16];
			byte[] b_iv = ivKey.getBytes("UTF-8");

			int len = b.length;
			if (len > keyBytes.length)
				len = keyBytes.length;
			System.arraycopy(b, 0, keyBytes, 0, len);
			System.arraycopy(b_iv, 0, ivBytes, 0, len);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

			Base64 decoder = new Base64();
			byte[] results = cipher.doFinal(decoder.decode(text));
			result = new String(results, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();

		}

		return result;
	}
}
