package com.hp.ws;

import com.hp.ws.datasource.entity.AppUser;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:42:58
 */
public interface SessionManager {

	Object getAttributeValue(String key);

	void addAttribute(String key, Object value);

	void removeAttribute(String key);

	AppUser getAppUser();

}
