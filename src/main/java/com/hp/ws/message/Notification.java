package com.hp.ws.message;

import com.hp.ws.enums.ErrorType;
import com.hp.ws.enums.NotificationType;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:02:46
 */
public class Notification {

	private String message;

	private String type;

	private ErrorType errorType;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(NotificationType NotificationType) {
		this.type = NotificationType.getLabel();
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

}
