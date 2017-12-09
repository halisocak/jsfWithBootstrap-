package com.hp.ws.model;

import org.springframework.stereotype.Component;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:03:58
 */
@Component
public class CreateUserRequest extends BaseRequest {

	private String userName;

	private String emailAddress;

	private String description;

	private String roleList;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoleList() {
		return roleList;
	}

	public void setRoleList(String roleList) {
		this.roleList = roleList;
	}

}
