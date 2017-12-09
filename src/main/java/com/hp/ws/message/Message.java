package com.hp.ws.message;

import java.io.Serializable;

import com.hp.ws.enums.ErrorType;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:02:41
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;

	private ErrorType errorType;

	private boolean success = true;

	public Message(String message, ErrorType errorType) {
		this.message = message;
		this.setErrorType(errorType);
	}

	public Message() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
