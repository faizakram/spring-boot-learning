package com.app.web.utils;

public class MappingConstant {
	private MappingConstant() {
	}

	public static final String POST = "/api/v1/post";
	public static final String HASHTAG = "/hashtag";
	public static final String COMMON = "/api/v1/open";
	public static final String USER = "/api/v1/user";
	public static final String TOKEN = "/token";
	public static final String VERIFICATION = "/email-verification";
	public static final String FOLLOW = "/follow";
	public static final String FOLLOWER = "/follower";
	public static final String FOLLOWING = "/following";
	public static final String BLOCKED = "/blocked";
	public static final String ADD = "/add";
	public static final String UPDATE = "/update";
	public static final String UPDATE_TRENDING = "/update-trending/{id}";
	public static final String COMMENT = "/comments";
	public static final String LIKE = "/like";
	public static final String FAVOURITE = "/favourite";
	public static final String FAVOURITES = "/favourites/{pageNumber}/{pageSize}";
	public static final String MASTER_KEY = "/masterKey";
	public static final String COMMON_ID = "/{id}";
	public static final String SLASH = "/";
	public static final String SLASH_WITH_PAGE = "/{pageNumber}/{pageSize}";
	public static final String LIST = "/list/{pageNumber}/{pageSize}";
	public static final String POSTS = "/posts/{pageNumber}/{pageSize}";
	public static final String TRENDING_POSTS = "/trending/posts/{pageNumber}/{pageSize}";
	public static final String POSTS_USER = "/posts/user/{pageNumber}/{pageSize}";
	public static final String TRENDING_POSTS_USER = "/trending/posts/user/{pageNumber}/{pageSize}";
	public static final String BY_TAG = "/bytag/{pageNumber}/{pageSize}";
	public static final String BY_USER = "/byuser/{pageNumber}/{pageSize}";
	public static final String SEARCH = "/search";
	public static final String SWAGGER_V2_API_DOCS = "/v2/api-docs";
	public static final String SWAGGER_CONGIGURATION_UI = "/configuration/ui";
	public static final String SWAGGER_RESOUCES = "/swagger-resources/**";

	// Excluded URL
	private static final String[] EXCLUDEURL = { COMMON + "/**" };

	public static String[] getExcludedURL() {
		return EXCLUDEURL.clone();
	}

}
