/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：InsertBean.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-8-11 上午11:18:57
 *@完成时间：2011-8-11 上午11:18:57
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.changing.framework.db.DbDateTime;
import com.changing.framework.helper.StringHelper;

public class InsertBean {
	
	private String sql1 = ""; // 数据库操作语句
	private String sql2 = ""; // 数据库操作语句
	private String table = ""; // 数据库表
	private String value = "";
	
	public InsertBean() {
	
	}
	/**
	 * 构造函数
	 * 
	 * @param table
	 *        数据库表
	 */
	public InsertBean(String table) {
		this.table = table;
	}
	/**
	 * 添加构造SQL语句的字段的字段名称，值，类型
	 * 
	 * @param column
	 *        字段名称
	 * @param type
	 *        字段类型
	 * @param value
	 *        字段值
	 */
	public void add(String column, String type, String value) {
		this.value = "";
		if (StringHelper.isEmpty(value)) {
			this.value = "";
		} else {
			type = type.toUpperCase();
			if (type.equals("VARCHAR2") || type.equals("CHAR")) {
				this.value = "'" + filter(value) + "'"; // 字符串型字段
			} else if (type.equals("DATE")) {
				this.value = DbDateTime.strToDbDate(value); // 短日期型字段
			} else if (type.equals("DATETIME")) {
				this.value = DbDateTime.strToDbTime(value);// 日期型字段
			} else { // NUMBER 数值型字段
				this.value = value; // 数值型字段
			}
		}
		if (!StringHelper.isEmpty(this.value)) {
			sql1 += "," + column;
			sql2 += "," + this.value;
		}
	}
	/**
	 * 添加构造SQL语句的相关用户记录
	 * @param LogInfo 用户登录信息
	 */
	public void add(LogInfo logInfo) {
		if (!sql1.equals("")) { // 要在保证sql1，sql2不为空的情况下才能添加附加的字段信息
			ActRecord actRecord = new ActRecord(logInfo);
			actRecord.setInsert();
			sql1 += actRecord.getSql1();
			sql2 += actRecord.getSql2();
		}
	}
	/**
	 * 将字符串做符合SQL标准的处理
	 * 
	 * @param str
	 *        待处理的字符串
	 */
	public String filter(String str) {
		if (str == null)
			return "";
		String rtn = str.replaceAll("\'", "''"); // 将单引号“'”做“''”处理
		return rtn;
	}

	/**
	 * 添加构造SQL语句的相关用户记录
	 * 
	 * @param request
	 *        用户登录信息
	 * @param formTable
	 *        用户登录信息
	 * @param mapValue
	 *        可能取到的外键信息
	 */
	public void add(HttpServletRequest request, String formTable, HashMap mapValue) throws Exception {
		int index = 0;
		String str = "";
		String type = ""; // 字段类型
		String value = ""; // 字段值
		String field = ""; // 字段对应的表单域
		String column = ""; // 字段名称
		ArrayList listForeign = new ArrayList();

		String compare = formTable + "$"; // 用来比较是否当前操作表的域
		int lenCut = compare.length(); // 每次截取表名加“$”字符的下一个位置
		Enumeration enumField = request.getParameterNames(); // 表单域数据
		while (enumField.hasMoreElements()) {
			field = enumField.nextElement().toString(); // t_ajxx$xh$char$key
			if (field.startsWith(compare)) {
				str = field.substring(lenCut); // xh$char$key
				index = str.indexOf('$');
				if (index > 0) {
					column = str.substring(0, index); // 取字段名，如“xh”
					str = str.substring(index + 1); // 如“char$key”
					value = request.getParameter(field); // 取表单域的值
					index = str.indexOf('$');
					if (index == -1) {
						add(column, str, value); // type = str，如“char”
					} else {
						type = str.substring(0, index); // 取字段值类型
						str = str.substring(index + 1).toUpperCase();
						if (str.equals("AUTO")) {
							value = "8888"; // 取值方法暂且不知，故先置为空值
							mapValue.put(field, value); // 将该值插入值集合中，方便后面调用
							add(column, type, value);
						} else if (str.equals("FOREIGN")) { // 如果是需要取外键的值，那么从自动域或表单域中获取
							if (value != null && !(value = value.trim()).equals("")) {
								listForeign.add(column);
								listForeign.add(type);
								listForeign.add(value);
							}
						}
					}
				}
			}
		}

		if (!listForeign.isEmpty()) {
			Iterator it = listForeign.iterator();
			while (it.hasNext()) {
				column = it.next().toString();
				type = it.next().toString();
				value = it.next().toString();
				Object obj = mapValue.get(value);
				if (obj == null) {
					value = request.getParameter(value);
				} else {
					value = obj.toString();
				}
				add(column, type, value);
			}
		}
	}

	/**
	 * 根据表名获取生成的SQL语句
	 */
	public String getSQL() throws Exception {
		if (sql1.equals("") || sql2.equals("")) {
			String str = "获取对表" + table + "执行插入操作的SQL语句时出错：没有获取到要插入的有效字段（字段缺失或字段值无效）。";
			throw new Exception(str);
		} else {
			return "INSERT INTO " + table + "(" + sql1.substring(1) + ") VALUES(" + sql2.substring(1) + ")";
		}
	}

	/**
	 * 清空现有数据，以便新的调用
	 * 
	 * @param table
	 *        数据库表
	 */
	public void clear(String table) {
		sql1 = ""; // 数据库操作语句
		sql2 = ""; // 数据库操作语句
		this.table = table; // 数据库表
	}
}