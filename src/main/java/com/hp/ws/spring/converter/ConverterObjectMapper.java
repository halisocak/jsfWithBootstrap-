package com.hp.ws.spring.converter;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:05:48
 */
public class ConverterObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -515754883383506150L;

	public ConverterObjectMapper() {
		super();
		// if the request body object includes date string or the response body
		// object includes date object
		// this configuration automatically does the type conversion.
		// configure(Feature.WRITE_DATES_WITH_ZONE_ID, true);
		// configure(Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		// setDateFormat(formatter);
		// getSerializationConfig().withDateFormat(formatter);
		getSerializationConfig().with(formatter);
	}
}
