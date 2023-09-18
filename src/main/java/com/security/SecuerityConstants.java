package com.security;



import org.springframework.core.env.Environment;

import com.SpringApplicationCustomContext;

public class SecuerityConstants {
	public static final long EXPIRATION_TIME=7200000; // 2 hours in milliseconds
	public static final String TOKEN_PREFIX="Bearer ";
	public static final String HEADER_STRING="Authorization";
	//public static final String TOKEN_SECRET="jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0";
	public static final String H2_CONSOLE = "/h2-console/**";

	public static  String getToken() {
	
		Environment environment=(Environment) SpringApplicationCustomContext.getBean("environment");
		return environment.getProperty("TOKEN_SECRET");
	}
}
