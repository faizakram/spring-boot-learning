package com.app.web.utils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.util.ObjectUtils;

/**
 * Date related util
 */
public class DateUtil {

	private DateUtil() {

	}

	/**
	 * generate date with time as null
	 * 
	 * @param inputDate
	 * @return Date
	 */
	public static Date getDateWithoutTime(Date inputDate) {
		Calendar calender = Calendar.getInstance();

		calender.setTime(inputDate);
		calender.set(Calendar.HOUR_OF_DAY, 0);
		calender.set(Calendar.MINUTE, 0);
		calender.set(Calendar.SECOND, 0);
		calender.set(Calendar.MILLISECOND, 0);

		return calender.getTime();
	}

	/**
	 * add days to input date
	 * 
	 * @param inputDate
	 * @param daysToAdd
	 * @return Date
	 */
	public static Date addDaysToDate(Date inputDate, int daysToAdd) {
		Calendar calender = Calendar.getInstance();

		calender.setTime(inputDate);
		calender.add(Calendar.DAY_OF_MONTH, daysToAdd);

		return calender.getTime();
	}

	/**
	 * convert date text format into date
	 * 
	 * @param desiredFormat
	 * @param inputDateString
	 * @return Date
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String inputDateString, String desiredFormat) {
		if(ObjectUtils.isEmpty(inputDateString)) 
			return null;
		try {
			DateFormat formatter = new SimpleDateFormat(desiredFormat);
			formatter.setLenient(false);
			return formatter.parse(inputDateString);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * convert string to desired date
	 * 
	 * @param desiredFormat
	 * @param inputDateString
	 * @return Date
	 * @throws ParseException
	 */
	public static String convertDateToString(String desiredFormat, Date inputDate) {
		if(ObjectUtils.isEmpty(inputDate)) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(desiredFormat);
		return formatter.format(inputDate);
	}

	/**
	 * convert given date according to timezone
	 * 
	 * @param inputDate
	 * @param inputTimezoneCode
	 * @param targetTimezoneCode
	 * @return LocalDateTime
	 */
	public static LocalDateTime convertDateByTimeZone(Date inputDate, String inputTimezoneCode,
			String targetTimezoneCode) {

		TimeZone inputTimezone = TimeZone.getTimeZone(inputTimezoneCode);
		ZoneId inputZoneId = inputTimezone.toZoneId();
		LocalDateTime inputLocalDateTime = LocalDateTime.ofInstant(inputDate.toInstant(), inputZoneId);
		ZonedDateTime inputZonedDateAndTime = ZonedDateTime.of(inputLocalDateTime, inputZoneId);

		TimeZone targerTimezone = TimeZone.getTimeZone(targetTimezoneCode);
		ZoneId targetZoneId = targerTimezone.toZoneId();
		ZonedDateTime targetZonedDateAndTime = inputZonedDateAndTime.withZoneSameInstant(targetZoneId);
		return targetZonedDateAndTime.toLocalDateTime();

	}

	/**
	 * generate new date with given input
	 * 
	 * @param yr
	 * @param month
	 * @param day
	 * @param hrs
	 * @param min
	 * @param sec
	 * @return Date
	 */
	public static Date generateDate(int yr, int month, int day, int hrs, int min, int sec) {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yr);
		cal.set(Calendar.MONTH, month - 1); // No of month start from 0
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hrs);
		cal.set(Calendar.MINUTE, min);
		cal.set(Calendar.SECOND, sec);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * convert given date according to timezone
	 * 
	 * @param inputDate
	 * @param inputTimezoneCode
	 * @param targetTimezoneCode
	 * @return Date
	 */
	public static Date convertDateToAnotherTimeZone(Date inputDate, String inputTimezoneCode,
			String targetTimezoneCode) {

		TimeZone fromTimezone = TimeZone.getTimeZone(inputTimezoneCode);// get Timezone object
		TimeZone toTimezone = TimeZone.getTimeZone(targetTimezoneCode);

		long fromOffset = fromTimezone.getOffset(inputDate.getTime());// get offset
		long toOffset = toTimezone.getOffset(inputDate.getTime());

		// calculate offset difference and calculate the actual time
		long convertedTime = inputDate.getTime() - (fromOffset - toOffset);
		Date newDate = new Date(convertedTime);
		DateFormat df = new SimpleDateFormat(CommonConstant.SIMPLE_DATE_TIME_FORMAT_VALUE);
		df.format(newDate);

		return newDate;
	}

	/**
	 * Get current time stamp in UTC
	 * 
	 * @return Current Date In UTC
	 */
	public static Timestamp getCurrentTimestampInUTC() {
		String defaultTimeZone = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
		Date currentUtcDate = convertDateToAnotherTimeZone(new Date(), defaultTimeZone, CommonConstant.UTC_TIME_ZONE);
		return new Timestamp(currentUtcDate.getTime());

	}

	public static Date getCurrentDateInUTC() {
		String defaultTimeZone = TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT);
		return convertDateToAnotherTimeZone(new Date(), defaultTimeZone, CommonConstant.UTC_TIME_ZONE);

	}

	/**
	 * get day from the passed date
	 * 
	 * @param date
	 * @return String
	 * @throws ParseException
	 */
	public static String getDayOfTheDate(String date) throws ParseException {
		Date formatedDate = new SimpleDateFormat(CommonConstant.SIMPLE_DATE_FORMAT_VALUE).parse(date);
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
		return simpleDateformat.format(formatedDate);
	}

	/**
	 * generate date with input time
	 * 
	 * @param date
	 * @param time
	 * @return Date
	 */
	public static Date generateDateTime(Date date, Time time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar calTime = Calendar.getInstance();
		calTime.setTime(time);
		cal.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
		cal.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
		return cal.getTime();
	}

	/**
	 * Get Date Difference form current date to give date
	 * 
	 * @param date
	 * @return Map<String,Integer> // days, months, year
	 */
	public static Map<String, Integer> getDateDiff(String date) {
		Map<String, Integer> dateMap = new HashMap<>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.SIMPLE_DATE_FORMAT_VALUE);
		LocalDate pdate = LocalDate.parse(date, formatter);
		LocalDate now = LocalDate.now(ZoneOffset.UTC);
		Period diff = Period.between(pdate, now);
		dateMap.put(CommonConstant.DAYS, diff.getDays());
		dateMap.put(CommonConstant.MONTHS, diff.getMonths());
		dateMap.put(CommonConstant.YEARS, diff.getYears());
		return dateMap;
	}

	/**
	 * Add Days, Months,Year at a time
	 * 
	 * @param dateMap
	 * @return
	 */
	public static LocalDate addDayMonthYear(Map<String, Integer> dateMap) {
		LocalDate date = LocalDate.now(ZoneOffset.UTC);
		date = date.plusDays(dateMap.get(CommonConstant.DAYS));
		date = date.plusMonths(dateMap.get(CommonConstant.MONTHS));
		date = date.plusMonths(dateMap.get(CommonConstant.YEARS));
		return date;
	}

	public static boolean currentDateTimeDiffer(Date lastDate, Long signUpExpireTime) {
		return lastDate.getTime() + (signUpExpireTime * 60) * (60 * 1000L) > DateUtil.getCurrentTimestampInUTC()
				.getTime();
	}

	/**
	 * Add Min
	 * 
	 * @param date
	 * @param mins
	 * @return
	 */
	public static Date addMinToCurrentUTCDate(int mins) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentTimestampInUTC());
		calendar.add(Calendar.MINUTE, mins);
		return calendar.getTime();
	}
	/**
	 * add Min To Current UTC Date
	 * @param currentTimeStamp
	 * @param mins
	 * @return
	 */
	public static Date addMinToCurrentUTCDate(Timestamp currentTimeStamp, int mins) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentTimeStamp);
		calendar.add(Calendar.MINUTE, mins);
		return calendar.getTime();
	}

}
