/**
 *@公司：   前景科技
 *@系统名称：
 *@文件名称：DbDateTime.java
 *@功能描述:数据库时间类型转换
 *@创建人  ：zn
 *@创建时间: 2012-3-12 下午08:53:03
 *@完成时间：2012-3-12 下午08:53:03
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;

import com.changing.framework.config.DbConfig;
import com.changing.framework.helper.LogHelper;

/**
 * @author Administrator
 *         <p>
 *         功能描述:
 *         <p>
 *         使用示例：
 *         <p>
 */
public class DbDateTime {

	private Timestamp tpSystemDate; // Timestamp
	private String strSystemDate; // yyyy-mm-dd hh24:mi:ss 的 String
	private String strShortSystemDate; // yyyy-mm-dd 的 String
	private java.sql.Date dtSystemDate; // yyyy-mm-dd 的 Date
	private Time tSystemDate; // hh24:mi:ss 的Time
	private java.util.Date dtSystemUtilDate; // yyyy-mm-dd hh24:mi:ss
	private String strSQL = null;
	private ResultSet rs = null;
	private static String dbType = DbConfig.getDb_database();
	public DbDateTime() {
		Connection conn = null;
		PreparedStatement pStmt = null;
		try {
			conn = DbConn.getConn();

			if (dbType.equals(DbConfig.MYSQL)) {
				this.strSQL = " SELECT NOW() AS NOW ,DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%s') as strtp,"
						+ " DATE_FORMAT(NOW(),'%Y-%m-%d') as asstrdt,DATE_FORMAT(now(),'%Y-%m-%d') as dt,"
						+ " DATE_FORMAT(NOW(),'%H:%i:%s') as tp";
			} else if (dbType.equals(DbConfig.ORACLE)) {
				this.strSQL = " SELECT SYSDATE AS NOW ,TO_CHAR(SYSDATE ,'yyyy-mm-dd hh24:mi:ss') as strtp,"
						+ " to_char(sysdate,'yyyy-mm-dd') as asstrdt,to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd') as dt,"
						+ " to_date(to_char(sysdate,'hh24:mi:ss'),'hh24:mi:ss') as tp from dual ";
			} else if (dbType.equals(DbConfig.SQLSERVER)) {
				this.strSQL = "SELECT getdate() AS NOW,CONVERT(varchar, getdate(), 120 ) AS strtp,"
						+ "CONVERT(varchar(10), getdate(), 120 ) AS asstrdt,CONVERT(varchar(10), getdate(), 120 ) AS dt,"
						+ "getdate()";
			} else {

			}
			pStmt = conn.prepareStatement(strSQL);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				tpSystemDate = rs.getTimestamp("NOW");
				strSystemDate = rs.getString("strtp");
				strShortSystemDate = rs.getString("asstrdt");
				dtSystemDate = rs.getDate("dt");
				tSystemDate = rs.getTime("tp");
				dtSystemUtilDate = DateFormat.getDateTimeInstance().parse(
						rs.getString("strtp"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LogHelper.logError(DbDateTime.class,ex,"DbDateTime构造方法失败!");
		} finally {
			DbConn.close(conn, pStmt, rs);
		}

	}
	/**
	 * 日期字符串转换成对应数据库的日期格式
	 * 如:2012-03-08
	 * @return
	 */
	public static String strToDbDate(String dt) {
		if (dbType.equals(DbConfig.ORACLE)) {
			return "to_date('"+dt+"','yyyy-mm-dd')";
		} else if (dbType.equals(DbConfig.MYSQL)) {
			return "'"+dt+"'";
		} else if (dbType.equals(DbConfig.SQLSERVER)) {
			return "'"+dt+"'";
		} else {
			return "'"+dt+"'";
		}
	}
	/**
	 * 日期时间字符串转换成对应数据库的日期时间格式
	 * 2012-03-08 13:10:10
	 * @return
	 */
	public static String strToDbTime (String dtime) {
		if (dbType.equals(DbConfig.ORACLE)) {
			return "to_date('"+dtime+"','yyyy-mm-dd hh24:mi:ss')";
		} else if (dbType.equals(DbConfig.MYSQL)) {
			return "'"+dtime+"'";
		} else if (dbType.equals(DbConfig.SQLSERVER)) {
			return "'"+dtime+"'";
		} else {
			return "'"+dtime+"'";
		}
	}
	/**
	 * 返回系统时间函数字符串
	 * 
	 * @return
	 */
	public static String getDbTimeStr() {
		if (DbConfig.getDb_database().equals(DbConfig.MYSQL)) {
			return " NOW() ";
		} else if (DbConfig.getDb_database().equals(DbConfig.ORACLE)) {
			return " SYSDATE ";
		} else if (DbConfig.getDb_database().equals(DbConfig.SQLSERVER)) {
			return " GETDATE() ";
		} else {
			return " NULL ";
		}
	}
	/**
	 * 获得数据库的当前时间 返回类型：Timestamp 例子：2004-07-21 13:55:52.0
	 * 
	 * @return
	 */
	public Timestamp getTpSystemDate() {
		return this.tpSystemDate;
	}

	/**
	 * 获得数据库的当前时间 返回类型：String 例子：2004-07-21 13:55:52
	 * 
	 * @return
	 */
	public String getStrSystemDate() {
		return this.strSystemDate;
	}

	/**
	 * 获得数据库的当前时间 返回类型：String 例子：2004-07-21
	 * 
	 * @return
	 */
	public String getStrShortSystemDate() {
		return this.strShortSystemDate;
	}

	/**
	 * 获得数据库的当前时间 返回类型：Date 例子：2004-07-21
	 * 
	 * @return
	 */
	public Date getDtSystemDate() {
		return this.dtSystemDate;
	}

	/**
	 * 获得数据库的当前时间 返回类型：Time 例子：13:55:52
	 * 
	 * @return
	 */
	public Time getTSystemDate() {
		return this.tSystemDate;
	}

	/**
	 * 获得数据库的当前时间 返回类型：yyyy-mm-dd hh24:mi:ss 的 java.util.Date 例子：Mon Aug 16
	 * 14:20:13 CST 2004
	 * 
	 * @return
	 */
	public java.util.Date getDtSystemUtilDate() {
		return this.dtSystemUtilDate;
	}

}
