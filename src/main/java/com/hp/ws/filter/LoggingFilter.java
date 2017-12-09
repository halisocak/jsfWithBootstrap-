package com.hp.ws.filter;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.hp.ws.SessionManager;
import com.hp.ws.constant.Beans;
import com.hp.ws.logging.AbstractLoggingFilter;
import com.hp.ws.logging.RequestLoggingWrapper;
import com.hp.ws.logging.ResponseLoggingWrapper;
import com.hp.ws.logging.WsLogMessagge;
import com.hp.ws.utility.RestClientUtils;
import com.hp.ws.utility.WebUtils;

/**
 * @author 5019hoca
 *
 *         16 May 2017 14:57:25
 */
public class LoggingFilter extends AbstractLoggingFilter {

	@Override
	public void chainDoFilterAfter(RequestLoggingWrapper requestWrapper, ResponseLoggingWrapper responseWrapper,
			WsLogMessagge wsLogMessagge) throws IOException {

		wsLogMessagge.setClientIp(WebUtils.getClientIpAddress(requestWrapper));
		wsLogMessagge.setMethod(requestWrapper.getMethod() + ":" + requestWrapper.getRequestURI());

		if (requestWrapper.getQueryString() != null) {
			wsLogMessagge.setMethod(wsLogMessagge.getMethod() + "?" + requestWrapper.getQueryString());
		}

		try {
			String messageType = RestClientUtils.findValueInJson(wsLogMessagge.getResponseMessage(), "errorType");
			if (messageType != null) {
				wsLogMessagge.setFault(true);
			}
		} catch (Exception e) {
			if (StringUtils.isEmpty(wsLogMessagge.getResponseMessage())) {
				String message = responseWrapper.getHttpStatus() + ":" + responseWrapper.getHttpErrorMsg();
				wsLogMessagge.setResponseMessage(message);
				wsLogMessagge.setFault(true);
			}
		}

		if (requestWrapper.getRequestURI().equals("/reme/authenticate")) {
			String cardPassword = "\"cardpassword\":\"";
			String requestHeader = wsLogMessagge.getRequestHeader();
			if (requestHeader.contains(cardPassword)) {
				int startIndex = requestHeader.indexOf(cardPassword);
				int endIndex = requestHeader.indexOf(",", startIndex + cardPassword.length());
				String cardPasswordAttribute = requestHeader.substring(startIndex, endIndex);
				String replacedHeader = requestHeader.replace(cardPasswordAttribute, "\"cardpassword\":\"****\"");
				wsLogMessagge.setRequestHeader(replacedHeader);
			}
		}
	}

	@Override
	public void chainDoFilterBefore(RequestLoggingWrapper requestCopier, ResponseLoggingWrapper responseCopier,
			WsLogMessagge wsLogMessagge) throws IOException {
		HttpSession httpSession = null;
		try {
			httpSession = requestCopier.getSession();
		} catch (IllegalStateException e) {
		}

		if (httpSession != null && httpSession.getAttribute(Beans.SCOPED_SESSION_MANAGER) != null) {
			SessionManager sessionManagerImpl = (SessionManager) httpSession.getAttribute(Beans.SCOPED_SESSION_MANAGER);
			wsLogMessagge.setAppUser(sessionManagerImpl.getAppUser());

		}

	}
}
