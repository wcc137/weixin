/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：Dbconn.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-8-5 下午04:23:20
 *@完成时间：2011-8-5 下午04:23:20
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.changing.framework.config.DbConfig;
import com.changing.framework.helper.LogHelper;

/**
 * @author Administrator <p> 功能描述: <p> 使用示例：<p>
 */
public class DbConn {
	
	/**
	 * 获取系统默认的数据库连接
	 * @return 数据库连接
	 */
	public static Connection getConn() {
		return DbConfig.getConn();
	}
	/**
	 * 根据指定的数据库连接参数获取数据库连接
	 * @param driver 数据库连接驱动
	 * @param url 数据库连接地址
	 * @param user 数据库访问用户
	 * @param pwd 数据访问密码
	 * @return 数据库连接，如果获取不到，那么返回null
	 */
	public static Connection getConn(String driver, String url, String user, String pwd) {
		try {
			Class.forName(driver); // 加载数据库驱动，直接获取连接
		} catch (ClassNotFoundException e) {
			LogHelper.logError(DbConfig.class,"获取数据库连接失败:\n" + e.getMessage());
			return null;
		}
		try {
			return DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			LogHelper.logError(DbConfig.class,"获取数据库连接失败:\n" + e.getMessage());
			return null;
		}
	}
	/**
	 * 执行数据库事务回滚操作
	 * @param conn 数据库连接
	 */
	public static void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			LogHelper.logError(DbConfig.class,"rollback数据回滚失败!"+e.getMessage());
		}
	}

	/**
	 * 关闭数据库连接
	 * @param conn 数据库连接
	 */
	public static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.setAutoCommit(true); // 设置成自动提交方式
				if (!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				LogHelper.logError(DbConfig.class,"Connection 关闭失败"+e.getMessage());
			}
		}
	}
	/**
	 * 关闭数据库资源
	 * @param stmt 数据库资源
	 */
	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				LogHelper.logError(DbConfig.class,"Statement 关闭失败"+e.getMessage());
			}
		}
	}
	/**
	 * 关闭数据库资源
	 * @param stmt 数据库资源
	 */
	public static void close(PreparedStatement pStmt) {
		if (pStmt != null) {
			try {
				pStmt.close();
			} catch (SQLException e) {
				LogHelper.logError(e.getMessage());
			}
		}
	}
	/**
	 * 关闭数据库资源
	 * @param rs 数据查询结果集
	 */
	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LogHelper.logError(DbConfig.class,"ResultSet 关闭失败"+e.getMessage());
			}
		}
	}
	/**
	 * 关闭数据查询结果集
	 * @param conn 数据库连接
	 * @param stmt 数据库资源
	 */
	public static void close(Connection conn, Statement stmt) {
		close(stmt); // 关闭数据库资源
		close(conn); // 关闭数据库连接
	}

	/**
	 * 关闭数据查询结果集
	 * @param stmt 数据库资源
	 * @param rs 数据查询结果集
	 */
	public static void close(Statement stmt, ResultSet rs) {
		close(rs); // 关闭数据查询结果集
		close(stmt); // 关闭数据库资源
	}

	/**
	 * 关闭数据查询结果集
	 * @param conn 数据库连接
	 * @param stmt 数据库资源
	 * @param rs 数据查询结果集
	 */
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		close(rs); // 关闭数据查询结果集
		close(stmt); // 关闭数据库资源
		close(conn); // 关闭数据库连接
	}

	public static void close(Connection conn, Statement stmt, PreparedStatement pStmt, ResultSet rs) {
		close(rs); // 关闭数据查询结果集
		close(stmt); // 关闭数据库资源
		close(pStmt); // 关闭数据库资源
		close(conn); // 关闭数据库连接
	}
	/**
	 * 
	 * @param conn
	 * @param pStmt
	 * @param rs
	 */
	public static void close(Connection conn,PreparedStatement pStmt, ResultSet rs) {
		close(rs); // 关闭数据查询结果集
		close(pStmt); // 关闭数据库资源
		close(conn); // 关闭数据库连接
	}
}
