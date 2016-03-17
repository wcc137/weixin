/**
 *@公司：          前景科技
 *@系统名称：changing_framework
 *@文件名称：DateHelperConstant.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2010-10-27 上午10:18:21
 *@完成时间：2010-10-27 上午10:18:21
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.helper;


/**
 * @author watermelon
 *         <p>
 *         功能描述:
 *         <p>
 *         使用示例：
 *         <p>
 */
public abstract class DateHelperConstant {
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_FORMAT_NO_SEPARATOR = "yyyyMMdd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String TIME_FORMAT_NO_SEPARATOR = "HHmmss";
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_FORMAT_NO_SEPARATOR = "yyyyMMddHHmmss";
	public static final String YEAR_FORMAT = "yyyy";
	public static final String YEARHOURS_FORMAT = "yyyy-MM";
	public static final String MONTH_FORMAT = "MM";
	public static final String DAY_FORMAT = "dd";
	public static final String HOURS_FORMAT = "HH";
	public static final String MINUTES_FORMAT = "mm";
	public static final String SECOND_FORMAT = "ss";
	public static final int WEEKFIRSTDAY_SUNDAY = 0;
	public static final int WEEKFIRSTDAY_MONDAY = -1;
	public static final int WEEKFIRSTDAY_TUESDAY = -2;
	public static final int WEEKFIRSTDAY_WEDNESDAY = -3;
	public static final int WEEKFIRSTDAY_THURSDAY = -4;
	public static final int WEEKFIRSTDAY_FRIDAY = -5;
	public static final int WEEKFIRSTDAY_SATURDAY = -6;
	public static final int RETURNTYPE_DAY = 1000* 60 * 60 * 24;
	public static final int RETURNTYPE_HOURS = 1000 * 60 * 60;
	public static final int RETURNTYPE_MINUTES = 1000 * 60;
	public static final int RETURNTYPE_SECOND = 1000;

}
