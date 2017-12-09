package com.hp.ws.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.ws.NotificationManager;
import com.hp.ws.constant.RequestMappingConstants;
import com.hp.ws.enums.NotificationType;
import com.hp.ws.message.Message;
import com.hp.ws.message.Notification;
import com.hp.ws.message.Response;
import com.hp.ws.message.ResponseBody;

/**
 * @author 5019hoca
 *
 * 16 May 2017 14:44:36
 */
@Controller
//@RequestMapping(value = "/mvc")
public class BaseController {
	
	@Autowired
	private NotificationManager notificationManager;

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private HttpServletRequest servletRequest;

	public Response getResponse() {
		return getResponse((Object) null);
	}

	// NotificationManager'daki notificationlari kullanarak olusturur
	public Response getResponse(Object baseResponse) {

		return getResponse(baseResponse, null);
	}

	public Response getResponse(String code) {
		return getResponse(null, code);
	}

	public Response getResponse(String code, Object[] params) {
		return getResponse(null, code, params);
	}

	public Response getResponse(Object baseResponse, String code) {
		return getResponse(baseResponse, code, null);
	}

	public Response getResponse(Object baseResponse, String code, Object[] params) {
		ResponseBody responseBody = new ResponseBody();
		HttpStatus httpStatus = HttpStatus.OK;
		Notification notification = notificationManager.getNotification();
		Message message = new Message();
		if (notification != null) {
			if (notification.getMessage().equals(notification.getErrorType().name())) {
				String messageStr = messageSource.getMessage("application.error." + notification.getErrorType().name(),
						null, servletRequest.getLocale());
				message.setMessage(messageStr);
			} else {
				message.setMessage(notification.getMessage());
			}
			message.setErrorType(notification.getErrorType());
			if (NotificationType.ERROR.equals(NotificationType.getByLabel(notification.getType()))) {
				message.setSuccess(false);
				// httpStatus = HttpStatus.SEE_OTHER;
			}
		} else {
			message.setErrorType(null);
			message.setSuccess(true);
		}
		responseBody.setMessage(message);

		responseBody.setResponseObject(baseResponse);

		Response OtpResponse = new Response(responseBody, getHeader(), httpStatus);
		return OtpResponse;
	}

	private MultiValueMap<String, String> getHeader() {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		return map;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

}
