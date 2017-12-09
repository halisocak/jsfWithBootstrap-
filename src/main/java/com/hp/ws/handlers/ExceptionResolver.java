package com.hp.ws.handlers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.hp.ws.constant.RequestConstants;
import com.hp.ws.constant.RequestMappingConstants;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:01:14
 */
public class ExceptionResolver implements HandlerExceptionResolver {
	private transient Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object object, Exception exception) {
		logger.error("ExceptionResolver", exception);
		httpServletRequest.setAttribute(RequestConstants.APP_EXCEPTION, exception);
		RequestDispatcher rd = httpServletRequest.getRequestDispatcher(
				RequestMappingConstants.getUrl(RequestMappingConstants.HANDLER_EXCEPTION));
		try {
			rd.forward(httpServletRequest, httpServletResponse);
		} catch (ServletException | IOException e) {
			logger.error("", e);
		}

		return new ModelAndView();
	}

}
