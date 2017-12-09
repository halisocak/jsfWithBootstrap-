package com.hp.ws.message;

import com.hp.ws.datasource.entity.AppUser;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:02:50
 */
public class RequestInfo {

	private AppUser authenticatedUser;
	private String clientIp;

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public AppUser getAuthenticatedUser() {
		return authenticatedUser;
	}

	public void setAuthenticatedUser(AppUser authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
	}

}
