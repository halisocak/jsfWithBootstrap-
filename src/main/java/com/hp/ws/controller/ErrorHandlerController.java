package com.hp.ws.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.ws.NotificationManager;
import com.hp.ws.constant.RequestConstants;
import com.hp.ws.constant.RequestMappingConstants;
import com.hp.ws.enums.ErrorType;
import com.hp.ws.enums.NotificationType;
import com.hp.ws.exception.WsException;
import com.hp.ws.message.Notification;
import com.hp.ws.message.Response;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:44:40
 */
@Controller
@RequestMapping(RequestMappingConstants.ERROR_PREFIX)
public class ErrorHandlerController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ErrorHandlerController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private NotificationManager notificationManager;

	@RequestMapping(value = RequestMappingConstants.HANDLER_EXCEPTION)
	public @ResponseBody Response handlerException(HttpServletRequest request) {
		String message;
		ErrorType errorType = ErrorType.ServerError;
		Object exp = request.getAttribute(RequestConstants.APP_EXCEPTION);
		if (exp instanceof WsException) {
			WsException safekeyException = (WsException) exp;
			message = messageSource.getMessage("application.error." + safekeyException.getErrorType().name(), null,
					request.getLocale());
			errorType = safekeyException.getErrorType();
		} else {
			message = messageSource.getMessage("application.error.General", null, request.getLocale());
		}

		Notification notification = new Notification();
		notification.setMessage(message);
		notification.setErrorType(errorType);
		notification.setType(NotificationType.ERROR);
		notificationManager.setNotification(notification);
		return getResponse();
	}

	@RequestMapping(value = RequestMappingConstants.SERVER_ERROR)
	public @ResponseBody Response serverError(HttpServletRequest request) {

		logger.info("SERVER_ERROR HANDLER CONTROLLER");
		String message = messageSource.getMessage("application.error.General", null, request.getLocale());

		Notification notification = new Notification();

		notification.setMessage(message);
		notification.setErrorType(ErrorType.ServerError);
		notification.setType(NotificationType.FATAL);
		notificationManager.setNotification(notification);

		return getResponse();
	}

	@RequestMapping(value = RequestMappingConstants.PAGE_NOT_FOUND)
	public @ResponseBody Response pageNotFound(HttpServletRequest request) {
		logger.info("PAGE_NOT_FOUND HANDLER CONTROLLER");
		String message = messageSource.getMessage("application.error.RequestNotFound", null, request.getLocale());
		Notification notification = new Notification();

		notification.setMessage(message);
		notification.setErrorType(ErrorType.RequestNotFound);
		notification.setType(NotificationType.ERROR);
		notificationManager.setNotification(notification);

		return getResponse();
	}

	@RequestMapping(value = RequestMappingConstants.EXCEPTION_OCCURRED)
	public @ResponseBody Response exceptionOccurred(HttpServletRequest request) {
		logger.info("EXCEPTION_OCCURRED HANDLER CONTROLLER");

		String message = messageSource.getMessage("application.error.General", null, request.getLocale());

		Notification notification = new Notification();

		notification.setMessage(message);
		notification.setErrorType(ErrorType.ServerError);
		notification.setType(NotificationType.FATAL);
		notificationManager.setNotification(notification);

		return getResponse();
	}

	@RequestMapping(value = RequestMappingConstants.UNAUTHORIZED)
	public @ResponseBody Response unauthorized(HttpServletRequest request) {
		logger.info("UNAUTHORIZED ERROR HANDLER CONTROLLER");

		String message = messageSource.getMessage("application.error.Unauthorized", null, request.getLocale());

		Notification notification = new Notification();

		notification.setMessage(message);
		notification.setErrorType(ErrorType.Unauthorized);
		notification.setType(NotificationType.ERROR);
		notificationManager.setNotification(notification);

		return getResponse();
	}

	@RequestMapping(value = RequestMappingConstants.FORBIDDEN)
	public @ResponseBody Response forbidden(HttpServletRequest request) {
		logger.info("UNAUTHORIZED ERROR HANDLER CONTROLLER");

		String message = messageSource.getMessage("application.error.Forbidden", null, request.getLocale());

		Notification notification = new Notification();

		notification.setMessage(message);
		notification.setErrorType(ErrorType.Forbidden);
		notification.setType(NotificationType.ERROR);
		notificationManager.setNotification(notification);

		return getResponse();
	}

	@RequestMapping(value = RequestMappingConstants.UNREGISTERED)
	public @ResponseBody Response unregistered(HttpServletRequest request) {
		logger.info("UNREGISTERED ERROR HANDLER CONTROLLER");

		String message = messageSource.getMessage("application.error.Unregistered", null, request.getLocale());

		Notification notification = new Notification();
		notification.setMessage(message);
		notification.setErrorType(ErrorType.Unauthorized);
		notification.setType(NotificationType.ERROR);
		notificationManager.setNotification(notification);

		return getResponse();
	}

}