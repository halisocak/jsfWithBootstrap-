package com.hp.ws.utility;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:06:25
 */
public class WebUtils extends org.springframework.web.util.WebUtils {
	public static String getServerString(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	}

	public static String getApplicationServerString(HttpServletRequest request) {
		return getServerString(request) + request.getContextPath();
	}

	public static String getClientIpAddress(HttpServletRequest request) {
		String clientIpAdress = request.getHeader("HTTP_CLIENT_IP");
		if (clientIpAdress == null) {
			clientIpAdress = request.getRemoteAddr();
		}
		return clientIpAdress;
	}

}
