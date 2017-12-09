package com.hp.ws.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.hp.ws.SessionManager;
import com.hp.ws.constant.Beans;
import com.hp.ws.datasource.entity.AppUser;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:28
 */
@Component(value = Beans.SESSION_MANAGER)
@Scope(value = org.springframework.web.context.WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
public class SessionManagerImpl implements SessionManager, Serializable {
	private static final long serialVersionUID = 1L;

	private final Map<String, Object> attribute = new HashMap<String, Object>();
	private AppUser appUser;

	public Map<String, Object> getAttributeMap() {
		return attribute;
	}

	@Override
	public Object getAttributeValue(String key) {
		return attribute.get(key);
	}

	@Override
	public void addAttribute(String key, Object value) {
		this.attribute.put(key, value);
	}

	@Override
	public void removeAttribute(String key) {
		this.attribute.remove(key);
	}

	@Override
	public AppUser getAppUser() {
		return appUser;
	}

}