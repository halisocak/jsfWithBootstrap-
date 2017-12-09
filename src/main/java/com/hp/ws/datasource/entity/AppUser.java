
package com.hp.ws.datasource.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * @author 5019hoca
 *
 *         16 May 2017 14:52:46
 */
@Entity
public class AppUser {

	/* tableNames dont remove begin */
	public final static String PROP_APP_USER = "appUser";
	public final static String PROP_CREATE_DATE = "createDate";
	public final static String PROP_DESCRIPTION = "description";
	public final static String PROP_EMAIL_ADDRESS = "emailAddress";
	public final static String PROP_ENABLED = "enabled";
	public final static String PROP_ID = "id";
	public final static String PROP_IV = "iv";
	public final static String PROP_MODIFIED_DATE = "modifiedDate";
	public final static String PROP_PASSWORD = "password";
	public final static String PROP_ROLE_LIST = "roleList";
	public final static String PROP_SALT = "salt";
	public final static String PROP_USER_NAME = "userName";
	/* tableNames end */

	@Id
	@SequenceGenerator(name = "APP_USER_ID_GENERATOR", sequenceName = "SEQ_APP_USER")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_USER_ID_GENERATOR")
	@Column(unique = true, precision = 18)
	private Long id;

	@Column(unique = true)
	private String userName;

	@Column
	private String emailAddress;

	@Column
	private String description;

	@Column
	private Date modifiedDate;

	@Column(nullable = false)
	private Date createDate;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private byte[] salt;

	@Column(nullable = false)
	private boolean enabled;

	@Column
	private String roleList;

	@Column(nullable = false)
	private byte[] iv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public String getRoleList() {
		return roleList;
	}

	public void setRoleList(String roleList) {
		this.roleList = roleList;
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

	public byte[] getIv() {
		return iv;
	}

	public void setIv(byte[] iv) {
		this.iv = iv;
	}

}