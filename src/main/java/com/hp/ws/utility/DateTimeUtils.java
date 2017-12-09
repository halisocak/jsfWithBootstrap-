package com.hp.ws.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 5019hoca
 *
 *         16 May 2017 15:06:12
 */
public class DateTimeUtils {

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static String getNowAsStr() {

		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		String str = sdf.format(Calendar.getInstance().getTime());
		return str;
	}

	public static Date getNowAsDate() {
		return Calendar.getInstance().getTime();
	}

	public static int compareNowDate(long millis, int addHour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return compareNowDate(calendar.getTime(), addHour);
	}

	public static int compareNowDate(Date date, int addHour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, addHour);
		return getNowAsDate().compareTo(calendar.getTime());
	}

	public static Timestamp getNowAsTimestamp() {
		return new Timestamp(DateTimeUtils.getNowAsDate().getTime());
	}

	public static void main(String[] args) {
		int i = DateTimeUtils.compareNowDate(DateTimeUtils.getNowAsDate(), -1);
		System.err.println(i);

	}
}
