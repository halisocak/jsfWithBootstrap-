package com.hp.ws.utility;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:06:19
 */
public class RestClientUtils {

	/**
	 * Execute the HTTP method to the given URI template, writing the given
	 * request entity to the request, and returns the response as
	 * ResponseEntity."
	 * 
	 * @param httpUrl
	 *            the URL example: http://www....com/cards/get
	 * @param httpMethod
	 *            the HTTP method (GET, POST, etc)
	 * @param requestHeader
	 *            the request header
	 * @param requestBody
	 *            the request body
	 * @param returnType
	 *            the type of the return value
	 * @return the response as entity
	 */
	public static <T> ResponseEntity<T> getResponseEntity(String httpUrl, HttpMethod httpMethod,
			HttpHeaders requestHeader, String requestBody, Class<T> returnType) {
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> entity = new HttpEntity<String>(requestBody, requestHeader);
		ResponseEntity<T> responseEntity = restTemplate.exchange(httpUrl, httpMethod, entity, returnType);
		return responseEntity;
	}

	/**
	 * Returns basic authorization string according to input parameters
	 * 
	 * @param username
	 *            the basic authorization username
	 * @param password
	 *            the basic authorization password
	 * @return the basic authorization string
	 */
	public static String getBasicAuthorizationString(String username, String password) {
		String authCreds = username + ":" + password;
		byte[] plainCredsBytes = authCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);
		return "Basic " + base64Creds;
	}

	/**
	 * Returns json string of any input object(model).
	 * 
	 * @param object
	 *            the object(model)
	 * @return json the json string which mapped from object
	 */
	public static String toJson(Object object) {
		return toJson(object, false, false);
	}

	/**
	 * Returns json string of any input object(model).
	 * 
	 * @param object
	 *            the object(model)
	 * @param boolean
	 *            will json be displayed pretty
	 * @return json the json string which mapped from object
	 */
	public static String toJson(Object object, boolean indentOutput) {
		return toJson(object, false, indentOutput);
	}

	/**
	 * Returns json string of any input object(model)
	 * 
	 * @param object
	 *            the object(model)
	 * @param ignoreNullValues
	 *            null values will be ignored if true
	 * @param boolean
	 *            will json be displayed pretty
	 * @return json the json string which mapped from object
	 */
	public static String toJson(Object object, boolean ignoreNullValues, boolean indentOutput) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.INDENT_OUTPUT, indentOutput);
			if (ignoreNullValues) {
				mapper.setSerializationInclusion(Include.NON_NULL);
			}
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/***
	 * Returns object of any json string. Null values are being ignored
	 * 
	 * @param content
	 *            the json string
	 * @param clazz
	 *            type of object that json string will be mapped
	 * @return <T> T the object which mapped from json string according to class
	 */
	public static <T> T fromJson(String content, Class<T> clazz) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(content, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String findValueInJson(String json, String node) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
		String messageType = mapper.readTree(json).findValue(node).asText();
		return messageType;
	}
}
