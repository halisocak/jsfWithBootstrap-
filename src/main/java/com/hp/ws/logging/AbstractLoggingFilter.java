package com.hp.ws.logging;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.ws.utility.RestClientUtils;
import com.hp.ws.utility.WebUtils;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:41
 */
public abstract class AbstractLoggingFilter implements Filter {
	Logger requestLogger = LoggerFactory.getLogger("RequestLogger");

	public abstract void chainDoFilterAfter(RequestLoggingWrapper requestCopier, ResponseLoggingWrapper responseCopier,
			WsLogMessagge wsLogMessagge) throws IOException;

	public abstract void chainDoFilterBefore(RequestLoggingWrapper requestCopier, ResponseLoggingWrapper responseCopier,
			WsLogMessagge wsLogMessagge) throws IOException;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (requestLogger.isTraceEnabled()) {
			reqResLogging(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	private void reqResLogging(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (response.getCharacterEncoding() == null) {
			response.setCharacterEncoding("UTF-8");
		}
		Timestamp startTime = new Timestamp(System.currentTimeMillis());
		WsLogMessagge wsLogMessagge = new WsLogMessagge();
		RequestLoggingWrapper requestWrapper = new RequestLoggingWrapper((HttpServletRequest) request);
		ResponseLoggingWrapper responseWrapper = new ResponseLoggingWrapper((HttpServletResponse) response);
		try {
			chainDoFilterBefore(requestWrapper, responseWrapper, wsLogMessagge);
		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			chain.doFilter(requestWrapper, responseWrapper);
		} finally {
			try {
				Timestamp endTime = new Timestamp(System.currentTimeMillis());
				wsLogMessagge.setRequestTime(startTime);
				wsLogMessagge.setResponseTime(endTime);
				wsLogMessagge.setRequestHeader(RestClientUtils.toJson(getHeadersInfo(requestWrapper), true));
				wsLogMessagge.setResponseHeader(RestClientUtils.toJson(getHeadersInfo(responseWrapper), true));
				wsLogMessagge.setRequestMessage(requestWrapper.getCopyAsString());
				wsLogMessagge.setResponseMessage(responseWrapper.getCopyAsString());
				wsLogMessagge.setClientIp(WebUtils.getClientIpAddress(requestWrapper));
				chainDoFilterAfter(requestWrapper, responseWrapper, wsLogMessagge);
				String wsLogMessagge1 = RestClientUtils.toJson(wsLogMessagge);
				requestLogger.trace(wsLogMessagge1);
				responseWrapper.flushBuffer();
			} catch (Exception e) {
				requestLogger.error("Error", e);
			}
		}
	}

	private Map<String, String> getHeadersInfo(ResponseLoggingWrapper responseCopier) {
		Map<String, String> map = new HashMap<String, String>();
		for (Iterator<String> iterator = responseCopier.getHeaderNames().iterator(); iterator.hasNext();) {
			String headerNames = iterator.next();
			String value = responseCopier.getHeader(headerNames);
			map.put(headerNames, value);

		}
		return map;
	}

	private Map<String, String> getHeadersInfo(RequestLoggingWrapper requestCopier) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> headerNames = requestCopier.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			String value = requestCopier.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	@Override
	public void destroy() {

	}
}
