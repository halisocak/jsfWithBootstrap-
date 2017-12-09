package com.hp.ws;

import com.hp.ws.message.Notification;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:42:40
 */
public interface NotificationManager {

	Notification getNotification();

	void setNotification(Notification notification);

}