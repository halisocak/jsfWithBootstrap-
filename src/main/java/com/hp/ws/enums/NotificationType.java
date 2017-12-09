package com.hp.ws.enums;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public enum NotificationType {

	/*
	 * INFO(1, "INFO"), WARNING(2, "WARNING"), ERROR(3, "ERROR"), FATAL(4,
	 * "FATAL");
	 */

	INFO("INFO", 1), WARNING("WARNING", 2), ERROR("ERROR", 3), FATAL("FATAL", 4);

	private Integer value;
	private String label;

	private NotificationType(final String label, final Integer value) {
		this.value = value;
		this.label = label;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static NotificationType getByValue(String value) {
		NotificationType notifcationType = notificationTypeMap.get(value);
		if (notifcationType == null) {
			return NotificationType.ERROR;
		}

		return notifcationType;

	}

	public static NotificationType getByLabel(String value) {

		Collection<NotificationType> values = notificationTypeMap.values();
		for (NotificationType portalNotificationType : values) {
			if (portalNotificationType.getLabel().equals(value)) {
				return portalNotificationType;
			}
		}
		return NotificationType.ERROR;

	}

	private static final Map<Integer, NotificationType> notificationTypeMap = new HashMap<Integer, NotificationType>();
	static {
		// populating the map
		for (NotificationType myEnum : values()) {
			notificationTypeMap.put(myEnum.getValue(), myEnum);
		}
	}

}
