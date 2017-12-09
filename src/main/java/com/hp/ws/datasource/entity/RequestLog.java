/**
 *  @author Halis Ocak Haz 27
 */
package com.hp.ws.datasource.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity

public class RequestLog {
	/* tableNames dont remove begin */
	public final static String PROP_APP_USER = "appUser";
	public final static String PROP_CLIENT_IP = "clientIp";
	public final static String PROP_DURATION = "duration";
	public final static String PROP_ID = "id";
	public final static String PROP_REQUEST_HEADER = "requestHeader";
	public final static String PROP_REQUEST_LOG = "requestLog";
	public final static String PROP_REQUEST_MESSAGE = "requestMessage";
	public final static String PROP_REQUEST_TIME = "requestTime";
	public final static String PROP_RESPONSE_HEADER = "responseHeader";
	public final static String PROP_RESPONSE_MESSAGE = "responseMessage";
	public final static String PROP_RESPONSE_TIME = "responseTime";
	public final static String PROP_SERIAL_NUMBER = "serialNumber";
	public final static String PROP_SERVICE_NAME = "serviceName";
	public final static String PROP_SOURCE_IP = "sourceIp";
	public final static String PROP_SUCCESS = "success";
	/* tableNames end */

	@Id
	@SequenceGenerator(name = "REQUEST_LOG_ID_GENERATOR", sequenceName = "SEQ_REQUEST_LOG")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REQUEST_LOG_ID_GENERATOR")
	@Column
	private Long id;

	@Column(nullable = false)
	private String serviceName;

	@ManyToOne
	private AppUser appUser;

	@Column
	private String sourceIp;

	@Column
	private String clientIp;

	@Column
	private Timestamp requestTime;

	@Lob
	@Column
	private String requestMessage;

	@Lob
	@Column
	private String requestHeader;

	@Column
	private Timestamp responseTime;

	@Lob
	@Column
	private String responseMessage;

	@Lob
	@Column
	private String responseHeader;

	@Column
	private long duration;

	@Column
	private boolean success;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public Timestamp getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getRequestHeader() {
		return requestHeader;
	}

	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}

	public String getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(String responseHeader) {
		this.responseHeader = responseHeader;
	}

}