/*
 * Created: 2016-09-13
 * ==================================================================================================
 *
 * Jimi Technology Corp. Ltd. License, Version 1.0 
 * Copyright (c) 2009-2016 Jimi Tech. Co.,Ltd.   
 * Published by R&D Department, All rights reserved.
 * For the convenience of communicating and reusing of codes, 
 * Any java names,variables as well as comments should be made according to the regulations strictly.
 *
 * ==================================================================================================
 * This software consists of contributions made by Jimi R&D.
 * @author: Li.Shangzhi
 */
package cn.concox.wechat.uti.pay;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.concox.comm.util.StringUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtil.
 */
public class DateUtil {
	
	/** The sdf cache. */
	private static Map<String, SimpleDateFormat> sdfCache = new Hashtable<String, SimpleDateFormat>();

	/** The Constant ONE_DAY. */
	public static final long ONE_DAY = 24 * 60 * 60 * 1000;

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(DateUtil.class);

	/**
	 * Convert String to date with a format.
	 * 
	 * @param date
	 *            Input String to convert
	 * @param pattern
	 *            pattern
	 * @return Date
	 */
	public static Date parse(final String date, final String pattern) {
		Date resultDate = null;
		if (null != date || null != pattern) {
			try {
				final SimpleDateFormat sdf = getSimpleDateFormat(pattern);
				synchronized (sdf) {
					resultDate = sdf.parse(date);
				}
			} catch (ParseException e) {
				LOG.error(e.toString());
			}
		}
		return resultDate;
	}

	/**
	 * Check the date is correct.
	 * 
	 * @param date
	 *            Input String to check
	 * @param pattern
	 *            pattern
	 * @return If check correct, return true; otherwise, return false flag: false-not right date format true-right date format
	 */
	public static boolean checkIsDate(final String date, final String pattern) {
		boolean flag = false;
		if (null == date || "".equals(date) || null == pattern || "".equals(pattern)) {
			flag = false;
		} else {
			final SimpleDateFormat sdf = getSimpleDateFormat(pattern);
			try {
				synchronized (sdf) {
					final Date tempDate = sdf.parse(date);
					final String tempStr = sdf.format(tempDate);
					if (tempStr.equalsIgnoreCase(date)) {
						flag = true;
					}
				}
			} catch (ParseException e) {
				LOG.error("Error parsing '" + date + "' using pattern '" + pattern + "'");
			}
		}
		return flag;
	}

	/**
	 * Convert date to String with a format.
	 * 
	 * @param date
	 *            Input date to convert
	 * @param pattern
	 *            pattern
	 * @return String
	 */
	public static String format(final Date date, final String pattern) {
		return null == date || null == pattern ? "" : getSimpleDateFormat(pattern).format(date);
	}

	/**
	 * Convert date to String.
	 * 
	 * @param date
	 *            Input date to convert
	 * @return String
	 */
	public static String formatDOB(final Date date) {
		return format(date, "dd/MM/yyyy");
	}

	/**
	 * get today.
	 * 
	 * @return Returns the today
	 */
	public static Date getToday() {
		final Date date = new Date();
		final String s = format(date, "dd/MM/yyyy");
		return parse(s, "dd/MM/yyyy");
	}

	/**
	 * Constructs a SimpleDateFormat using the given pattern and the default date format symbols for the default locale.
	 * 
	 * @param pattern
	 *            pattern
	 * @return SimpleDateFormat
	 */
	private static SimpleDateFormat getSimpleDateFormat(final String pattern) {
		SimpleDateFormat sdf = (SimpleDateFormat) sdfCache.get(pattern);
		if (null == sdf) {
			sdf = new SimpleDateFormat();
			sdf.applyPattern(pattern);
			sdf.setLenient(false);
			sdfCache.put(pattern, sdf);
		}
		return sdf;
	}

	/**
	 * Start Modification For BCP By Angelo on 21 Nov 2007 Date Comparasion Method.
	 * 
	 * @param aDate
	 *            the a date
	 * @param curDate
	 *            the cur date
	 * @param bDate
	 *            the b date
	 * @return If the method is correct, return true; otherwise, return false
	 */
	public static boolean compareDate(final Date aDate, final Date curDate, final Date bDate) {
		boolean result = false;
		if (null == aDate || null == curDate || null == bDate) {
			result = false;
		} else {
			result = (curDate.after(aDate) || curDate.equals(aDate)) && curDate.before(bDate);
		}
		return result;
	}

	/**
	 * Date Comparasion Method.
	 * 
	 * @param aDateStr
	 *            the a date str
	 * @param bDateStr
	 *            the b date str
	 * @param pattern
	 *            the pattern
	 * @return boolean If the method is correct, return true; otherwise, return false
	 */
	public static boolean compareDate(final String aDateStr, final String bDateStr,
	        final String pattern) {
		final Date aDate = parse(aDateStr, pattern);
		final Date bDate = parse(bDateStr, pattern);
		boolean result = false;
		if ((null != aDate && null != bDate) && (aDate.before(bDate) || aDate.equals(bDate))) {
			result = true;
		}
		return result;
	}

	/**
	 * date calculate.
	 * 
	 * @param inputDate
	 *            the input date
	 * @param field
	 *            the field
	 * @param amount
	 *            the amount
	 * @return Date
	 */
	public static Date add(final Date inputDate, final int field, final int amount) {
		Date resultDate = null;
		if (null != inputDate) {
			final Calendar cal = Calendar.getInstance();
			cal.setTime(inputDate);
			cal.add(field, amount);
			resultDate = cal.getTime();
		}
		return resultDate;
	}

	/**
	 * Adds the day of year.
	 * 
	 * @param inputDate
	 *            the input date
	 * @param amount
	 *            the amount
	 * @return the date
	 */
	public static Date addDayOfYear(final Date inputDate, final int amount) {
		return add(inputDate, Calendar.DAY_OF_YEAR, amount);
	}

	/**
	 * Adds the date.
	 * 
	 * @param inputDate
	 *            the input date
	 * @param amount
	 *            the amount
	 * @return the date
	 */
	public static Date addDate(final Date inputDate, final int amount) {
		return add(inputDate, Calendar.DATE, amount);
	}

	/**
	 * Adds the month.
	 * 
	 * @param inputDate
	 *            the input date
	 * @param amount
	 *            the amount
	 * @return the date
	 */
	public static Date addMonth(final Date inputDate, final int amount) {
		return add(inputDate, Calendar.MONTH, amount);
	}

	/**
	 * Adds the year.
	 * 
	 * @param inputDate
	 *            the input date
	 * @param amount
	 *            the amount
	 * @return the date
	 */
	public static Date addYear(final Date inputDate, final int amount) {
		return add(inputDate, Calendar.YEAR, amount);
	}

	/**
	 * Convert to sql date.
	 * 
	 * @param date
	 *            the date
	 * @return the java.sql. date
	 */
	public static java.sql.Date convertToSQLDate(final Date date) {
		return null == date ? null : new java.sql.Date(date.getTime());
	}

	/**
	 * Convert to sql timestamp.
	 * 
	 * @param date
	 *            the date
	 * @return the timestamp
	 */
	public static Timestamp convertToSQLTimestamp(final Date date) {
		return null == date ? null : new Timestamp(date.getTime());
	}

	/**
	 * Method to truncte the input date's time to 00:00:00.
	 * 
	 * @param inputDate
	 *            the input date
	 * @return the date
	 */
	public static Date truncateTime(final Date inputDate) {
		return truncateTime(inputDate, Calendar.getInstance());
	}

	/**
	 * Method to truncte the input date's time to 00:00:00.
	 * 
	 * @param inputDate
	 *            the input date
	 * @param calendar
	 *            the calendar
	 * @return the date
	 */
	public static Date truncateTime(final Date inputDate, final Calendar calendar) {
		Date date = null;

		if (null != inputDate && null != calendar) {
			calendar.setTime(inputDate);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			date = calendar.getTime();
		}
		return date;

	}

	/**
	 * get first day of year.
	 * 
	 * @param date
	 *            the date
	 * @return Date
	 */
	public static Date getFirstDayOfYear(final Date date) {
		Date firstDayOfYear = null;
		if (null != date) {
			final GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
			gc.setTime(date);
			gc.set(Calendar.DAY_OF_MONTH, 1);
			gc.set(Calendar.MONTH, 0);
			firstDayOfYear = truncateTime(gc.getTime());
		}
		return firstDayOfYear;
	}

	/**
	 * get first day of month.
	 * 
	 * @param date
	 *            the date
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(final Date date) {
		final GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc.getTime();
	}

	/**
	 * get last day of month.
	 * 
	 * @param date
	 *            the date
	 * @return Date
	 */
	public static Date getLastDayOfMonth(final Date date) {
		Date resultDate = null;
		if (null != date) {
			Date day = getFirstDayOfMonth(date);
			day = addMonth(day, 1);
			day = addDate(day, -1);
			resultDate = day;
		}
		return resultDate;
	}

	/**
	 * get now date.
	 * 
	 * @return the now date
	 * @returnDate
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * Compare date.
	 * 
	 * @param firstDate
	 *            the first date
	 * @param secondDate
	 *            the second date
	 * @return true, if successful
	 */
	public static boolean compareDate(final Date firstDate, final Date secondDate) {
		boolean result = false;
		if (null == firstDate || null == secondDate) {
			result = false;
		} else {
			final Calendar firstCalendar = Calendar.getInstance();
			firstCalendar.setTime(firstDate);
			firstCalendar.set(Calendar.DAY_OF_MONTH, firstCalendar.get(Calendar.DAY_OF_MONTH) - 1);
			final Calendar secondCalendar = Calendar.getInstance();
			secondCalendar.setTime(secondDate);
			result = firstCalendar.after(secondCalendar);
		}
		return result;
	}

	/**
	 * Compare sql date.
	 * 
	 * @param firstDate
	 *            the first date
	 * @param secondDate
	 *            the second date
	 * @return the int
	 */
	public static int compareSQLDate(final Date firstDate, final Date secondDate) {
		int result = 0;
		if (null == firstDate && null == secondDate) {
			result = 0;
		} else if (null == firstDate && null != secondDate) {
			result = 1;
		} else if (null != firstDate && null == secondDate) {
			result = -1;
		} else {
			result = new Date(firstDate.getTime()).compareTo(new Date(secondDate.getTime()));
		}
		return result;
	}

	

	public static String getCalendarYear(int yearFromNow) {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, yearFromNow);
		return String.valueOf(calendar.get(Calendar.YEAR));
	}
	/**
	 * Date diff.
	 * 
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return the int
	 */
	public static int dateDiff(final Date fromDate, final Date toDate) {
		int result = 0;
		if (null != fromDate && null != toDate) {
			result = (int) ((toDate.getTime() - fromDate.getTime()) / (24 * 60 * 60 * 1000));
		}
		return result;
	}

	/**
	 * 将当前时间转换成字符串，cenhuineng新增
	 * @param date
	 * @return
	 */
	public static String formatDate(final Date date) {
		return format(date, "yyyyMMddHHmmss");
	}
	
	/**
	 * 计算时间差，返回单位分
	 * 
	 * @author 黄韦谋
	 * @crate 2015年3月9日 上午11:16:33
	 * @param startTime
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static int countDifftime(Date startTime, Date endTime) {
		long num = endTime.getTime() - startTime.getTime();
		return (int) (num / 1000 / 60);
	}
	
	/**
	 * 计算时间差，返回单位秒
	 * 
	 * @author 黄韦谋
	 * @crate 2015年3月9日 上午11:16:33
	 * @param startTime
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static int countDiffSecond(Date startTime, Date endTime) {
		long num = endTime.getTime() - startTime.getTime();
		return (int) (num / 1000);
	}
	
	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateFormatter() {
		Date date = new Date();
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Timestamp getTimestampByStr(String time) throws ParseException{
		if(!StringUtil.isRealEmpty(time)){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			format.setLenient(false);
			Timestamp ts = new Timestamp(format.parse(time).getTime()); 
			return ts;
		}else{
			return null;
		}
	}
}