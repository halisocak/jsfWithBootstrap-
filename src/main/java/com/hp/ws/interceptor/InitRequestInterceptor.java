package com.hp.ws.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hp.ws.RequestManager;
import com.hp.ws.constant.RequestConstants;
import com.hp.ws.datasource.entity.AppUser;
import com.hp.ws.service.UserService;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:34
 */
public class InitRequestInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = Logger.getLogger(InitRequestInterceptor.class);
 
	@Autowired
	private RequestManager requestManager;
	@Autowired
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
		if (request.getUserPrincipal() != null) {
			String username = request.getUserPrincipal().getName();
			AppUser appUser = userService.getAppUser(username);
			requestManager.getRequestInfo().setAuthenticatedUser(appUser);;
		}
		requestManager.getRequestInfo().setClientIp(request.getHeader(RequestConstants.CLIENT_IP));

		return true;
	}

	// after the handler is executed
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		long startTime = (Long) request.getAttribute("startTime");

		long endTime = System.currentTimeMillis();

		long executeTime = endTime - startTime;
		logger.info(request.getPathInfo() + " executeTime:" + executeTime);

	}
}