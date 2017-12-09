package com.hp.ws.impl;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.hp.ws.RequestManager;
import com.hp.ws.message.RequestInfo;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:25
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class RequestManagerImpl implements RequestManager, Serializable {
	private static final long serialVersionUID = -1071519791944033348L;

	private final RequestInfo requestInfo = new RequestInfo();

	@Override
	public RequestInfo getRequestInfo() {
		return requestInfo;
	}
}