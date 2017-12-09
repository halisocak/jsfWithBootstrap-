package com.hp.ws.logging;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.hp.ws.constant.Beans;
import com.hp.ws.datasource.dao.UserDao;
import com.hp.ws.datasource.entity.RequestLog;
import com.hp.ws.proxy.ServiceProxy;
import com.hp.ws.service.BaseService;
import com.hp.ws.utility.RestClientUtils;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:02:03
 */
public class WsLogAppenderSkeleton extends AppenderSkeleton {

	@Override
	protected void append(LoggingEvent arg0) {
		WsLogMessagge wsLogMessagge = RestClientUtils.fromJson(arg0.getMessage().toString(), WsLogMessagge.class);

		RequestLog requestLog = new RequestLog();
		requestLog.setRequestTime(wsLogMessagge.getRequestTime());
		requestLog.setResponseTime(wsLogMessagge.getResponseTime());

		requestLog.setRequestHeader(wsLogMessagge.getRequestHeader());
		requestLog.setRequestMessage(wsLogMessagge.getRequestMessage());
		requestLog.setResponseMessage(wsLogMessagge.getResponseMessage());
		requestLog.setResponseHeader(wsLogMessagge.getResponseHeader());
		long duration = wsLogMessagge.getResponseTime().getTime() - wsLogMessagge.getResponseTime().getTime();
		requestLog.setDuration(duration);

		requestLog.setClientIp(wsLogMessagge.getClientIp());

		requestLog.setServiceName(wsLogMessagge.getMethod());
		requestLog.setAppUser(wsLogMessagge.getAppUser());
		ServiceProxy.getInstance().getBean(UserDao.class).saveEntity(requestLog);

	}

	private BaseService getBaseService() {
		return (BaseService) ServiceProxy.getInstance().getBean(Beans.BASE_SERVICE);
	}

	@Override
	public synchronized void doAppend(LoggingEvent event) {
		super.doAppend(event);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean requiresLayout() {
		// TODO Auto-generated method stub
		return false;
	}
}