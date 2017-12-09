package com.hp.ws.logging;

import org.apache.log4j.AsyncAppender;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:45
 */
public class AsyncWsLogAppender extends AsyncAppender {

	@Override
	public void activateOptions() {
		super.activateOptions();
		synchronized (this) {
			WsLogAppenderSkeleton wsLogAppender = new WsLogAppenderSkeleton();
			wsLogAppender.activateOptions();
			addAppender(wsLogAppender);
		}
	}

	@Override
	public boolean requiresLayout() {
		return false;
	}

}
