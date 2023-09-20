package com.security;

import com.SpringApplicationCustomContext;

public class SecuerityConstants {
	public static final long EXPIRATION_TIME=7200000; // 2 hours in milliseconds
	public static final String TOKEN_PREFIX="Bearer ";
	public static final String HEADER_STRING="Authorization";
	//public static final String TOKEN_SECRET="jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0jf9i4jgu83nfl0";
	public static final String ORDERS_SAVE="orders/all";
	public static final String USERS_SAVE="users/save";

	public static String getTokenSecret()
    {
        AppProperties appProperties = (AppProperties) SpringApplicationCustomContext.getBean("AppProperties");
        return appProperties.getTokenSecret();
    }
}
