package com.hp.ws.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.hp.ws.NotificationManager;
import com.hp.ws.message.Notification;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:20
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
public class NotificationManagerImpl implements NotificationManager {

	private Notification notification;

	@Override
	public Notification getNotification() {
		return notification;
	}

	@Override
	public void setNotification(Notification notification) {
		this.notification = notification;
	}

}
