package com.hp.ws.constant;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:44:12
 */
public class RequestMappingConstants {
	public static final String ERROR_PREFIX = "/error";

	public static final String SERVER_ERROR = "/serverErrorHandler";
	public static final String HANDLER_EXCEPTION = "/handlerException";
	public static final String UNAUTHORIZED = "/401";
	public static final String FORBIDDEN = "403";
	public static final String UNREGISTERED = "/unregistered";
	public static final String ACCESS_DENIED = "503";

	public static final String PAGE_NOT_FOUND = "/404";
	public static final String EXCEPTION_OCCURRED = "/500";

	public static final String GET_SERVER_PARAMETERS = "/serverParameters";

	public static final String CREATE_USER = "/createUser";
	public static final String SECURE = "/secure";
	public static final String  HOME_PAGE="/home";



	public static String getUrl(String path) {
		return ERROR_PREFIX + path;
	}
}
