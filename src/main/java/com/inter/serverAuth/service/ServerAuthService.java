package com.inter.serverAuth.service;

public interface ServerAuthService {

	String registerAuthKey(String param);

	String verifyAuthKey(String param);

	String getServerDate(String param);

	String appCheck(String param);

}
