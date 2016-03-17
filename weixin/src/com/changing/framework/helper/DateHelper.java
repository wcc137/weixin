/**
 *@公司：          前景科技
 *@系统名称：changing_framework
 *@文件名称：DateHelper.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2010-10-27 上午10:19:40
 *@完成时间：2010-10-27 上午10:19:40
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;




/**
 * @author watermelon
 *         <p>
 *         功能描述:
 *         <p>
 *         使用示例：
 *         <p>
 */
public class DateHelper {
	/**
	 * 将从【2004-05-06 12:12:12.0】格式转换成【2004-05-06 12:12:12】,空返回“”
	 * 
	 * @param strString
	 * @return
	 */
	public static String dateTimeSubString(String strString) {
		if (strString == null || strString.equals("")
				|| strString.length() <= 0) {
			return "";
		} else if (strString.length() >= 19) {
			strString = strString.substring(0, 19);
			return strString;
		} else {
			return strString.substring(0);
		}
	}

	/**
	 * 字符串转换为日期类型
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date stringToDate(String dateString) {
		Date date;
		SimpleDateFormat format = new SimpleDateFormat(
				DateHelperConstant.DATETIME_FORMAT);
		format.setLenient(false);// 这个的功能是不把1996-13-3 转换为1997-1-3
		try {
			date = format.parse(dateString);
		} catch (Exception e) {
			date = new Date();
		}
		return date;
	}

	public static Date stringToDate(String strDate, String nFmtDate)
			throws Exception {
		SimpleDateFormat fmtDate = new SimpleDateFormat(nFmtDate);
		return fmtDate.parse(strDate);
	}

	/**
	 * Date类型转换为Calendar
	 * 
	 * @param date
	 * @return
	 */
	public static Calendar toCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * 返回当前时间，当前时间为new Date()的返回值
	 * 
	 * @return
	 */
	public static Date getNow() {
		return new Date();
	}

	/**
	 * 设置时间的年度
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date setYear(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.setTime(date);
		c.set(Calendar.YEAR, amount);
		return c.getTime();
	}

	/**
	 * 设置时间的月
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date setMonth(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.setTime(date);
		c.set(Calendar.MONDAY, amount);
		return c.getTime();
	}

	/**
	 * 设置时间的日期
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	public static Date setDay(Date date, int amount) {
		Calendar c = Calendar.getInstance();
		c.setLenient(false);
		c.setTime(date);
		c.set(Calendar.DATE, amount);
		return c.getTime();
	}

	/**
	 * 字符串转化为Calendar类型
	 * 
	 * @param dateString
	 *            需要转换的字符串
	 * @return
	 */
	public static Calendar stringToCalendar(String dateString) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(stringToDate(dateString));
		return calendar;
	}

	/**
	 * 按照固定格式对日历类型进行转换
	 * 
	 * @param calendar
	 * @param pattern
	 *            格式类型从DateHelperConstant
	 *            中获取，如DateHelperConstant.DATETIME_FORMAT为 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String format(Calendar calendar, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(calendar.getTime()).toString();
	}

	/**
	 * 按照固定格式对日期类型进行转换
	 * 
	 * @param date
	 * @param pattern
	 *            格式类型从DateHelperConstant
	 *            中获取，如DateHelperConstant.DATETIME_FORMAT为 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * <p>
	 * 将特定格式的String型对象转换成java.util.Date对象
	 * </p>
	 * 
	 * @param strDate
	 *            a date string
	 * @param nFmtDate
	 *            specific date string format defined in this class.
	 * @exception raise
	 *                ParseException, if string format dismathed.
	 * @return Date
	 * @throws Exception
	 */
	public static Date parseDate(String strDate, int nFmtDate) throws Exception {
		SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
		switch (nFmtDate) {
		default:
		case DateTools.FMT_DATE_YYYYMMDD:
			fmtDate = new SimpleDateFormat("yyyy-MM-dd");
			// fmtDate.applyLocalizedPattern("yyyy-MM-dd");
			break;
		case DateTools.FMT_DATE_YYYYMMDD_HHMMSS:
			fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// fmtDate.applyLocalizedPattern("yyyy-MM-dd HH:mm:ss");
			break;
		case DateTools.FMT_DATE_HHMM:
			fmtDate = new SimpleDateFormat("HH:mm");
			// fmtDate.applyLocalizedPattern("HH:mm");
			break;
		case DateTools.FMT_DATE_HHMMSS:
			fmtDate = new SimpleDateFormat("HH:mm:ss");
			// fmtDate.applyLocalizedPattern("HH:mm:ss");
			break;
		}
		return fmtDate.parse(strDate);
	}

	/**
	 * the number of milliseconds since January 1, 1970, 00:00:00 GMT 把秒数转为日期及时间
	 * 
	 * @param lDateInt
	 *            秒数－毫秒
	 * @param nFmtDate
	 *            格式
	 * @return string型日期及时间
	 * @throws Exception
	 */
	public static String formatDate(long lDateInt, int nFmtDate)
			throws Exception {
		SimpleDateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");
		switch (nFmtDate) {
		default:
		case DateTools.FMT_DATE_YYYYMMDD:
			fmtDate = new SimpleDateFormat("yyyy-MM-dd");
			// fmtDate.applyLocalizedPattern("yyyy-MM-dd");
			break;
		case DateTools.FMT_DATE_YYYYMMDD_HHMMSS:
			fmtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// fmtDate.applyLocalizedPattern("yyyy-MM-dd HH:mm:ss");
			break;
		case DateTools.FMT_DATE_HHMM:
			fmtDate = new SimpleDateFormat("HH:mm");
			// fmtDate.applyLocalizedPattern("HH:mm");
			break;
		case DateTools.FMT_DATE_HHMMSS:
			fmtDate = new SimpleDateFormat("HH:mm:ss");
			// fmtDate.applyLocalizedPattern("HH:mm:ss");
			break;
		}
		return fmtDate.format(new Date(lDateInt));

	}

	/**
	 * 返回此时间值，以毫秒为单位。当前时间，以从历元至现在所经过的 UTC 毫秒数形式。
	 * 
	 * @param date
	 * @return
	 */
	public static long getLong(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获得日期所在周的第几天,可设置星期的第一天
	 * 
	 * @param date
	 * @param firstWeekDay
	 *            设置星期几为第一天，一般设置星期天或星期一 获取值从
	 *            DateHelperConstant中获得,如DateHelperConstant.WEEKFIRSTDAY_MONDAY
	 * @return
	 */
	public static int getDayOfWeek(Date date, int weekFirstDay) {
		int returnInt = 10;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		returnInt = calendar.get(Calendar.DAY_OF_WEEK) + weekFirstDay;
		if (returnInt <= 0) {
			returnInt = returnInt + 7;
		}
		return returnInt;
	}

	/**
	 * 获得当前日期所在年的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYear(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("日期不能为空……");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * 获得当前日期所在月的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date) {
		if (date == null) {
			throw new IllegalArgumentException("日期不能为空……");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	// --------------------------------有待考虑---------------------------------------------------------------------------------------
	/**
	 * 指定当前月的第几个星期
	 * 
	 * @param calendar
	 * @return
	 */
	public static int getDayOfWeekOfMonth(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}

	// --------------------------------有待考虑---------------------------------------------------------------------------------------

	/**
	 * 获得一个星期的第一天，可设置星期的第一天
	 * 
	 * @param date
	 * @param weekFirstDay
	 *            设置星期几为第一天，一般设置星期天或星期一 获取值从
	 *            DateHelperConstant中获得,如DateHelperConstant.WEEKFIRSTDAY_MONDAY
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date, int weekFirstDay) {
		int weekDayInt = getDayOfWeek(date, weekFirstDay);
		return addDays(date, -1 * weekDayInt + 1);
	}

	/**
	 * 获得一个星期的最后一天，可设置星期的第一天
	 * 
	 * @param date
	 * @param weekFirstDay
	 *            设置星期几为第一天，一般设置星期天或星期一 获取值从
	 *            DateHelperConstant中获得,如DateHelperConstant.WEEKFIRSTDAY_MONDAY
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date, int weekFirstDay) {
		int weekDayInt = getDayOfWeek(date, weekFirstDay);
		return addDays(date, -1 * weekDayInt + 7);
	}

	/**
	 * 获得一周的日期，可设置星期的第一天
	 * 
	 * @param date
	 * @param weekFirstDay
	 *            设置星期几为第一天，一般设置星期天或星期一 获取值从
	 *            DateHelperConstant中获得,如DateHelperConstant.WEEKFIRSTDAY_MONDAY
	 * @return
	 */
	public static ArrayList getDaysOfWeek(Date date, int weekFirstDay) {
		ArrayList list = new ArrayList();
		Date dateFirst = getFirstDayOfWeek(date, weekFirstDay);
		for (int i = 0; i < 7; i++) {
			list.add(addDays(dateFirst, i));
		}
		return list;
	}

	/**
	 * 获得月的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		int monthDayInt = getDayOfMonth(date);
		return addDays(date, -1 * monthDayInt + 1);
	}

	/**
	 * 获得月的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		int monthDayInt = getDayOfMonth(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return addDays(date, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
				- monthDayInt);
	}

	/**
	 * 获得当前月的每一天
	 * 
	 * @param date
	 * @return
	 */
	public static ArrayList getDaysOfMonths(Date date) {
		ArrayList list = new ArrayList();
		Date firstDate = getFirstDayOfMonth(date);
		Date lastDate = getLastDayOfMonth(date);
		while (lastDate.after(firstDate)) {
			list.add(firstDate);
			firstDate = addDays(firstDate, 1);
		}
		list.add(lastDate);
		return list;
	}

	/**
	 * 获得年的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfYear(Date date) {
		int yearDayInt = getDayOfYear(date);
		return addDays(date, -1 * yearDayInt + 1);
	}

	/**
	 * 获得年的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfYear(Date date) {
		int yearDayInt = getDayOfYear(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return addDays(date, calendar.getActualMaximum(Calendar.DAY_OF_YEAR)
				- yearDayInt);
	}

	/**
	 * 获得当前时间所在的周，跨年的情况，如2009-12-27为星期天 2009年最后一周就为2009-12-27至2010-01-02
	 * 2010年的第一周为2010-01-03至2010-01-09 新的一年开始几天归并为上一年的最后一周
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 对日进行操作
	 * 
	 * @param date
	 * @param calendarField
	 * @param amount
	 * @return
	 */
	private static Date add(Date date, int calendarField, int amount) {
		if (date == null) {
			throw new IllegalArgumentException("日期不能为空……");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendarField, amount);
		return calendar.getTime();
	}

	/**
	 * 在给定的日期上进行年的操作，date不能为空，否则抛出异常
	 * 
	 * @param date
	 *            给定日期
	 * @param amount
	 *            增减量
	 * @return
	 */
	public static Date addYears(Date date, int amount) {
		return add(date, Calendar.YEAR, amount);
	}

	/**
	 * 在给定的日期上进行月的操作，date不能为空，否则抛出异常
	 * 
	 * @param date
	 *            给定日期
	 * @param amount
	 *            增减量
	 * 
	 * @return
	 */
	public static Date addMonths(Date date, int amount) {
		return add(date, Calendar.MONTH, amount);
	}

	/**
	 * 在给定的日期上进行周的操作，相当于在给定日期上加上7天，date不能为空，否则抛出异常
	 * 
	 * @param date
	 *            给定日期
	 * @param amount
	 *            增减量
	 * 
	 * @return
	 */
	public static Date addWeeks(Date date, int amount) {
		return add(date, Calendar.WEEK_OF_YEAR, amount);
	}

	/**
	 * 在给定的日期上进行日的操作，date不能为空，否则抛出异常
	 * 
	 * @param date
	 *            给定日期
	 * @param amount
	 *            增减量
	 * 
	 * @return
	 */
	public static Date addDays(Date date, int amount) {
		return add(date, Calendar.DAY_OF_MONTH, amount);
	}

	/**
	 * 在给定的日期上进行小时的操作，date不能为空，否则抛出异常
	 * 
	 * @param date
	 *            给定日期
	 * @param amount
	 *            增减量
	 * 
	 * @return
	 */
	public static Date addHours(Date date, int amount) {
		return add(date, Calendar.HOUR_OF_DAY, amount);
	}

	/**
	 * 在给定的日期上进行分钟的操作，date不能为空，否则抛出异常
	 * 
	 * @param date
	 *            给定日期
	 * @param amount
	 *            增减量
	 * 
	 * @return
	 */
	public static Date addMinutes(Date date, int amount) {
		return add(date, Calendar.MINUTE, amount);
	}

	/**
	 * 在给定的日期上进行秒的操作，date不能为空，否则抛出异常
	 * 
	 * @param date
	 *            给定日期
	 * @param amount
	 *            增减量
	 * 
	 * @return
	 */
	public static Date addSeconds(Date date, int amount) {
		return add(date, Calendar.SECOND, amount);
	}

	/**
	 * 在给定的日期上进行毫秒的操作，date不能为空，否则抛出异常
	 * 
	 * @param date
	 *            给定日期
	 * @param amount
	 *            增减量
	 * 
	 * @return
	 */
	public static Date addMilliseconds(Date date, int amount) {
		return add(date, Calendar.MILLISECOND, amount);
	}

	/**
	 * 判断是否为同一天
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1
				.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * 判断是否为同一天
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * 判断date1是否大于date2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isAfter(Date date1, Date date2) {
		return date1.after(date2);
	}

	/**
	 * 返回时间差，返回的单位为
	 * <P>
	 * DateHelper.compareDate(date, DateHelper.addHours(date, -24),
	 * DateHelperConstant.RETURNTYPE_DAY, 8, -1) =1
	 * <P>
	 * DateHelper.compareDate(date, DateHelper.addHours(date, -4),
	 * DateHelperConstant.RETURNTYPE_HOURS ,8, -1) =4
	 * <P>
	 * DateHelper.compareDate(date, DateHelper.addHours(date, -4),
	 * DateHelperConstant.RETURNTYPE_DAY ,8, -1)=0.16666667
	 * 
	 * @param date1
	 * @param date2
	 * @param returnType
	 *            返回的单位 ，从常量类中获得，如DateHelperConstant.RETURNTYPE_HOURS
	 * @param fraction
	 *            小数位
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static double compareDate(Date date1, Date date2, int returnType,
			int fraction, float defaultValue) {
		if (date1 != null & date2 != null) {
			Calendar calendar = Calendar.getInstance();
			Calendar calendar2 = Calendar.getInstance();
			calendar.setTime(date1);
			calendar2.setTime(date2);

			double returnInt = NumberHelper.div(
					(calendar.getTimeInMillis() - calendar2.getTimeInMillis()),
					returnType, fraction, defaultValue);
			return returnInt;
		} else {
			return defaultValue;
		}

	}

	/**
	 * 日期转换给定格式转化为字符串
	 * 
	 * @param date
	 * @param pattern
	 *            需要的日期格式，日期格式从常量类中获得 DateHelperConstant.DATE_FORMAT
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date).toString();
	}

	// -----------------------------------------------------------------------------------------------------------------------
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar calendar = DateHelper.stringToCalendar("2010-01-02 00:00:01");
		System.out.println("当前日期为："
				+ DateHelper.dateToString(calendar.getTime(),
						DateHelperConstant.DATETIME_FORMAT));
		Date date = calendar.getTime();
		// Date date = dateHelper.addWeeks(calendar.getTime(), 1);
		// System.out.println(dateHelper.dateToString(date,
		// DateHelperConstant.DATETIME_FORMAT));
		// 设置当前每周第一天
		/*
		 * int firstDay = DateHelperConstant.WEEKFIRSTDAY_TUESDAY;
		 * System.out.println("当前时间所在周的位置：" +
		 * dateHelper.getDayOfWeek(calendar.getTime(), firstDay));
		 * System.out.println("当前时间所在周第一天为" + dateHelper.dateToString(dateHelper
		 * .getFirstDayOfWeek(calendar.getTime(), firstDay),
		 * DateHelperConstant.DATETIME_FORMAT));
		 * System.out.println("当前时间所在周最后一天为" + dateHelper.dateToString(
		 * dateHelper.getLastDayOfWeek(calendar.getTime(), firstDay),
		 * DateHelperConstant.DATETIME_FORMAT)); System.out.println("获得月的第一天：" +
		 * dateHelper.format(dateHelper.getFirstDayOfMonth(calendar.getTime()),
		 * DateHelperConstant.DATETIME_FORMAT)); System.out.println("获得月的最后一天：" +
		 * dateHelper.format(dateHelper.getLastDayOfMonth(calendar.getTime()),
		 * DateHelperConstant.DATETIME_FORMAT)); System.out.println("获得年的第一天：" +
		 * dateHelper.format(dateHelper.getFirstDayOfYear(calendar.getTime()),
		 * DateHelperConstant.DATETIME_FORMAT)); System.out.println("获得年的最后一天：" +
		 * dateHelper.format(dateHelper.getLastDayOfYear(calendar.getTime()),
		 * DateHelperConstant.DATETIME_FORMAT)); System.out.println("获当前时间所在周：" +
		 * dateHelper.getWeekOfYear(calendar.getTime())); ArrayList list =
		 * dateHelper.getDaysOfWeek(calendar.getTime(), firstDay); for (int i =
		 * 0; i < list.size(); i++) {
		 * System.out.println(dateHelper.format((Date) list.get(i),
		 * DateHelperConstant.DATETIME_FORMAT)); }
		 * System.out.println(dateHelper.format(dateHelper.setYear(calendar.getTime(),
		 * 1997), DateHelperConstant.DATETIME_FORMAT)); ArrayList list2 =
		 * DateHelper.getDaysOfMonths(calendar.getTime()); for (int i = 0; i <
		 * list2.size(); i++) { System.out.println("getDaysOfMonths : " +
		 * dateHelper.format((Date) list2.get(i),
		 * DateHelperConstant.DATETIME_FORMAT)); }
		 */
		// System.out.println(NumberHelper.roundValue(300,2,2));
		System.out.println(DateHelper.compareDate(date, DateHelper.addDays(
				date, -1), DateHelperConstant.RETURNTYPE_SECOND, 2, -1));
		System.out.println(DateHelper.compareDate(date, DateHelper.addHours(
				date, -4), DateHelperConstant.RETURNTYPE_DAY, 8, -1));

	}
}
