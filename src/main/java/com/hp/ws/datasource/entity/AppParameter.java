/**
 *  @author Halis Ocak Haz 27
 */
package com.hp.ws.datasource.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { AppParameter.PROP_MODIFIED_DATE })
@Entity
public class AppParameter {
	/* tableNames dont remove begin */
	public final static String PROP_APP_PARAMETER = "appParameter";
	public final static String PROP_DESCRIPTION = "description";
	public final static String PROP_KEY = "property";
	public final static String PROP_MODIFIED_DATE = "modifiedDate";
	public final static String PROP_VALUE = "value";
	/* tableNames end */

	@Id
	@Column(nullable = false)
	private String property;

	@Column
	private String value;

	@Column
	private String description;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	@Column
	private Date modifiedDate;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}