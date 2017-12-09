package com.hp.ws.proxy;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ServletContextAware;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:04:07
 */
public class ServiceProxy implements ApplicationContextAware, ServletContextAware {
	private ApplicationContext applicationContext;
	private ServletContext servletContext;

	private static ServiceProxy instance;

	public static ServiceProxy getInstance() {
		if (instance == null) {
			instance = new ServiceProxy();
		}
		return instance;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		instance = this;
		this.applicationContext = context;
	}

	public <T> T getBean(Class<T> classes) {
		return applicationContext.getBean(classes);
	}

	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}

	public String getServletRealPath() {
		return servletContext.getRealPath("");
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
