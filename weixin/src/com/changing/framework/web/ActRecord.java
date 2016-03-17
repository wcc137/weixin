package com.changing.framework.web;

import com.changing.framework.db.DbDateTime;

public class ActRecord {
	private String sql1 = ""; // 数据库操作语句
	private String sql2 = ""; // 数据库操作语句
	private LogInfo logInfo = null; // 登录信息
	private XtYgdm xtYgdm = null;// 员工信息

	/**
	 * 构造函数
	 * 
	 * @param LogInfo用户登录信息
	 */
	public ActRecord(LogInfo logInfo) {
		this.logInfo = logInfo;
		if (logInfo != null) {
			xtYgdm = logInfo.getXtYgdm();
		}
	}

	/**
	 * 添加构造SQL语句的相关用户记录
	 * 
	 * @param xtYgdm
	 *            用户登录信息
	 */
	public void setInsert() {
		sql1 = ""; // 数据库操作语句
		sql2 = ""; // 数据库操作语句
		if (xtYgdm != null) {
			String value = xtYgdm.getYgdm(); // 获取用户ID
			if (value != null && !(value = value.trim()).equals("")) { // 如果用户ID不为空
				sql1 += ",C_ID";
				sql2 += ",'" + value + "'";
			}
			value = xtYgdm.getBmdm(); // 获取用户部门
			if (value != null && !(value = value.trim()).equals("")) { // 如果用户部门不为空
				sql1 += ",C_DEPT";
				sql2 += ",'" + value + "'";
			}
			sql1 += ",C_DATE";
			sql2 += "," + DbDateTime.getDbTimeStr();
		}
	}

	/**
	 * 添加构造SQL语句的相关用户记录
	 * 
	 * @param xtYgdm
	 *            用户登录信息
	 */
	public void setUpdate() {
		sql1 = ""; // 数据库操作语句
		if (xtYgdm != null) {
			String value = xtYgdm.getYgdm(); // 获取用户ID
			if (value != null && !(value = value.trim()).equals("")) { // 如果用户ID不为空
				sql1 += ",M_ID='" + value + "'";
			}
			sql1 += ",M_DATE=" + DbDateTime.getDbTimeStr();
			value = logInfo.getIp(); // 获取用户IP
			if (value != null && !(value = value.trim()).equals("")) { // 如果用户ID不为空
				sql1 += ",M_CN='" + value + "'";
			}
		}
	}

	/**
	 * @return the sql1
	 */
	public String getSql1() {
		return sql1;
	}

	/**
	 * @return the sql2
	 */
	public String getSql2() {
		return sql2;
	}
}
