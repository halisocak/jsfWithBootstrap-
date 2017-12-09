package com.hp.ws.exception;

import com.hp.ws.enums.ErrorType;

/**
 * @author 5019hoca
 *
 *         16 May 2017 14:53:39
 */
public class WsException extends Exception {
	private static final long serialVersionUID = 6622998351307923920L;
	private ErrorType errorType;

	public WsException(ErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;

	}

	public WsException(ErrorType errorType, String message, Throwable cause) {
		super(message, cause);
	}

	public WsException(ErrorType errorType, Throwable cause) {
		super(null, cause);
	}

	public WsException(ErrorType errorType) {
		this.errorType = errorType;

	}

	public WsException() {
		// TODO Auto-generated constructor stub
	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public void setErrorType(ErrorType errorType) {
		this.errorType = errorType;
	}

}
