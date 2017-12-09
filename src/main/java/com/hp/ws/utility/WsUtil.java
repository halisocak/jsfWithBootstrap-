package com.hp.ws.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.hp.ws.cache.AppParameterCache;
import com.hp.ws.datasource.entity.AppParameter;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:06:33
 */
public class WsUtil {

	public static List<AppParameter> getDefaultAppParameterList() {
		List<AppParameter> appParameters = new ArrayList<AppParameter>();

		Field[] fields = AppParameterCache.class.getDeclaredFields();

		for (Field field : fields) {
			if (field.getModifiers() > 24) {
				Object value;
				try {
					value = field.get(field);
					if (field.getType().isAssignableFrom(String.class)) {
						AppParameter appParameter = new AppParameter();
						appParameter.setProperty(value.toString());
						String name = field.getName() + "Default";
						Field fieldValue = AppParameterCache.class.getDeclaredField(name);
						appParameter.setValue(fieldValue.get(fieldValue).toString());
						appParameter.setModifiedDate(DateTimeUtils.getNowAsDate());
						appParameter.setDescription(field.getName());
						appParameters.add(appParameter);
					}
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException
						| NullPointerException e) {}
			}
		}
		return appParameters;
	}

	public static boolean isValidActivationCode(String activationCode) {

		int sumCode = 0;

		String lastValue = activationCode.substring(10, 11);

		// System.out.println(lastValue);

		for (int i = 0; i < 10; i++) {
			sumCode += Integer.parseInt(String.valueOf(activationCode.charAt(i)));
		}

		String code = String.valueOf(sumCode);

		int sumOfCode = sumCode % 10;

		if (lastValue.equals(String.valueOf(sumOfCode))) {
			return true;
		}
		return false;
	}

	/**
	 * Seri numarasý doðrulama
	 *
	 * @param serialNumber
	 * @return
	 */
	public static boolean isValidSerialNumber(String serialNumber) {

		int evenCount = 0;
		boolean firstRule = false;
		boolean secondRule = false;

		for (int i = 0; i < serialNumber.length(); i++) {
			if (i % 2 == 0) {

				if (Integer.parseInt(String.valueOf(serialNumber.charAt(i))) <= (10 - i / 2)) {
					firstRule = true;
					if (Integer.parseInt(String.valueOf(serialNumber.charAt(i))) % 2 == 0) {
						evenCount++;
					}
				}
			} else {
				if (Integer.parseInt(String.valueOf(serialNumber.charAt(i))) <= 10 - evenCount) {
					secondRule = true;
				}
			}

		}

		if (firstRule && secondRule) {
			return true;
		}

		return false;

	}
}
