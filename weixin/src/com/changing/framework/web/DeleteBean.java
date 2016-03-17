package com.changing.framework.web;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

import com.changing.framework.db.DbDateTime;
import com.changing.framework.helper.StringHelper;

public class DeleteBean {
	private String sql = ""; // 数据库操作语句
	private String table = ""; // 数据库表
	private String value = "";
	/**
	 * 构造函数
	 * @param table 数据库表
	 */
	public DeleteBean(String table) {
		this.table = table;
	}
	/**
	 * 添加构造SQL语句的相关用户记录
	 * @param request 用户登录信息
	 * @param formTable 用户登录信息
	 */
	public void add(HttpServletRequest request, String formTable) throws Exception {
		int index = 0;
		String str = "";
		String type = ""; // 字段类型
		String value = ""; // 字段值
		String field = ""; // 字段对应的表单域
		String column = ""; // 字段名称

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
					index = str.indexOf('$');
					if (index > 0) {
						type = str.substring(0, index); // 取字段值类型
						str = str.substring(index + 1).toUpperCase();
						if (str.equals("KEY")) { // 如果是关键字段
							value = request.getParameter(field); // 取表单域的值
							addKey(column, type, value);
						}
					}
				}
			}
		}
	}

	/**
	 *  添加构造SQL语句的关键条件字段的字段名称，值，类型
	 * @param column 字段名称
	 * @param type 字段类型
	 * @param value 字段值
	 */
	public void addKey(String column, String type, String value) {
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
				this.value = DbDateTime.strToDbTime(value); // 日期型字段
			} else { // NUMBER 数值型字段
				this.value = value; // 数值型字段
			}
		}
		sql += " AND " + column + "=";
		if (StringHelper.isEmpty(this.value)) { // 如果字段值为空，则直接置为空
			sql += "null";
		} else {
			sql += this.value;
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
	 * 根据表名获取生成的SQL语句
	 * @param table 表名称
	 */
	public String getSQL() throws Exception {
		if (sql.equals("")) {
			String str = "获取对表" + table + "执行删除操作的SQL语句时出错：没有获取到删除表数据所需要的条件域。";
			throw new Exception(str);
		} else {
			sql = sql.substring(5); // 去掉最后的“ AND ”
			return "DELETE FROM " + table + " WHERE " + sql;
		}
	}

	/**
	 * 清空现有数据，以便新的调用
	 */
	public void clear() {
		sql = ""; // 数据库操作语句
	}

	/**
	 * 清空现有数据，以便新的调用
	 * @param table 数据库表
	 */
	public void clear(String table) {
		sql = ""; // 数据库操作语句
		this.table = table; // 数据库表
	}
}