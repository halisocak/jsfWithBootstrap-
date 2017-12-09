
package com.hp.ws.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

/**
 *
 * Http Headers ve Http Status degistirmeyi saglar. Body icin daha once
 * tanimlanmis BaseResponse obje json olarak doner controllerdan
 *
 * @author 5019hoca
 *
 *         16 May 2017 15:02:54
 */
public class Response extends ResponseEntity<ResponseBody> {

	public Response(ResponseBody body, HttpStatus statusCode) {
		super(body, statusCode);
	}

	public Response(ResponseBody body, MultiValueMap<String, String> headers, HttpStatus statusCode) {
		super(body, headers, statusCode);
	}

	public Response(HttpStatus statusCode) {
		super(statusCode);
	}

	public Response(MultiValueMap<String, String> headers, HttpStatus statusCode) {
		super(headers, statusCode);
	}

}
