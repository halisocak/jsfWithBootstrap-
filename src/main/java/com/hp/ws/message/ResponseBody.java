package com.hp.ws.message;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:03:20
 */
public class ResponseBody {
	private Message message;
	private Object responseObject;

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}
}