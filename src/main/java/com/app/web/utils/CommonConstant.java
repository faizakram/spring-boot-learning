package com.app.web.utils;

public class CommonConstant {
	private CommonConstant() {
	}

	public static final String SUCCESS_PROPERTIES = "classpath:success.properties";
	public static final String ERROR_PROPERTIES = "classpath:error.properties";
	public static final String COMMON_MAP_OBJECT = "COMMON_MAP_OBJECT";
	public static final String ID = "id";
	public static final String RESULTS = "results";
	public static final String UNCHECKED = "unchecked";

	// Query Parameter Constant

	public static final String PERCENT_SYMBOLE = "%";
	public static final String PARAM1 = "param1";
	public static final String PARAM2 = "param2";
	public static final String PARAM3 = "param3";
	public static final String PARAM4 = "param4";
	public static final String PARAM5 = "param5";
	public static final String PARAM6 = "param6";
	public static final String PARAM7 = "param7";

	public static final String HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String USER_ID = "userId";

	public static final String DAYS = "DAYS";
	public static final String MONTHS = "MONTHS";
	public static final String YEARS = "YEARS";
	public static final String SIMPLE_DATE_FORMAT_VALUE = "yyyy-MM-dd";
	public static final String SIMPLE_DATE_FORMAT_MMMM_YYYY = "MMMM yyyy";
	public static final String DATE_TIME_FORMAT = "MM-dd-yyyy HH:mm:ss";
	public static final String UTC_TIME_ZONE = "UTC";
	public static final String SIMPLE_DATE_TIME_FORMAT_VALUE = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_MM_DD_YYYY = "MM/dd/yyyy";
	public static final String DATE_FORMAT_MM_DD_YYYY_HH_MM_SS = "MM/dd/yyyy HH:mm:ss";
	public static final String USER = "User";
	public static final String ENUM_ITEM = "Enum Item";
	public static final String POST = "Post";
	public static final String LIKE = "Like";
	public static final String LIKE_ENUM_CODE = "LIKE";
	public static final String FAVOURITE = "Favourite";
	public static final String COMMENT = "Comment";
	
	public static final String POST_ENUM_CODE = "POST";
	public static final String POST_ENUM_ITEM_CODE = "POST";
	public static final String COMMENT_ENUM_ITEM_CODE = "COMMENT";
	public static final String FOLLOWING_ENUM_ITEM_CODE = "FOLLOWING";
	public static final String BLOCKED_ENUM_ITEM_CODE = "BLOCKED";
	public static final String FAVOURITE_ENUM_ITEM_CODE = "FAVOURITE";
	public static final String SHARE_ENUM_ITEM_CODE = "FAVOURITE";
	public static final String HEADER_TOKEN = "X-Auth-Token";

	// Swagger Constant
	public static final String X_FRAME_OPTIONS = "x-frame-options";
	public static final String X_CONTENT_TYPE_OPTIONS = "x-content-type-options";
	public static final String STRICT_TRANSPORT_SECURITY = "strict-transport-security";
	public static final String CONTENT_SECURITY_POLICY = "content-security-policy";
	public static final String X_PERMITTED_CROSS_DOMAIN_POLICIES = "x-permitted-cross-domain-policies";

	/**
	 * Intercepter Constant
	 */
	public static final String SLASH_STAR_STAR = "/**";
	
	public static final String BLANK = "";
	public static final String EMAIL_VERIFICATION_SUBJECT = "Email Verification";
	public static final String VERIFIED = "Verified";
	public static final String TAG = "Tag";
	public static final String LIKE_QUERY = "%%%s%%";
	
	
	public static void main(String args[]) {
		System.out.println(String.format(LIKE_QUERY, "test"));
	}
}
