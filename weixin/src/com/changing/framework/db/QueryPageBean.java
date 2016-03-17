/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：QueryPageBean.java
 *@功能描述:查询数据翻页Bean
 *@创建人  ：zn
 *@创建时间: 2011-8-9 下午09:19:55
 *@完成时间：2011-8-9 下午09:19:55
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;
import java.util.Vector;

import com.changing.framework.config.DbConfig;
import com.changing.framework.config.SysConfig;
import com.changing.framework.helper.LogHelper;
import com.changing.framework.helper.NumberHelper;
import com.changing.framework.helper.StringHelper;

/**
 * @author Administrator
 *         <p>
 *         功能描述:
 *         <p>
 *         使用示例：
 *         <p>
 */
public class QueryPageBean {

	private int currentPage = 1; // 当前所在页码
	private int perPage = 15; // 每页行数
	private int intRowsCount = 0; // 当前所在页码行数
	private int allRowsCount = 0; // 总行数
	private int count_page = 0; // 总页数
	private String strQuerySQL = null;
	private Vector valueVector = null;
	private Vector vector = null;
	public String dbType = DbConfig.getDb_database();// 数据库类型
	private boolean outDebug = SysConfig.isOutDebug();// 是否打印调试日志
	private boolean outError = SysConfig.isOutError();// 是否打印错误日志

	public QueryPageBean() {
		strQuerySQL = "";
		this.valueVector = new Vector();
	}

	/**
	 * 设置bean要执行的SQL语句
	 * 
	 * @param strParamSQL
	 */
	public void setQuerySQL(String strParamSQL) {
		this.strQuerySQL = strParamSQL;
	}

	public void setQuerySQL(String strParamSQL, boolean bl) {
		this.strQuerySQL = strParamSQL;
	}

	/**
	 * 获取bean要执行的SQL语句
	 * 
	 * @return
	 */
	public String getQuerySQL() {
		return this.strQuerySQL;
	}

	/**
	 * 设置当前页的行数
	 * 
	 * @param paramNextPage
	 */
	public void setCurrentPage(int paramNextPage) {
		this.currentPage = paramNextPage;
	}

	/**
	 * 取当前页的行数
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 设置每页行数
	 * 
	 * @param paramPerPage
	 */
	public void setPerPage(int paramPerPage) {
		this.perPage = paramPerPage;
	}

	/**
	 * 取每页行数
	 * 
	 * @return
	 */
	public int getPerPage() {
		return perPage;
	}

	/**
	 * 设置结果条数
	 * 
	 * @param intParamCount
	 */
	private void setRowsCount(int intParamCount) {
		this.intRowsCount = intParamCount;
	}

	/**
	 * 获取结果条数
	 * 
	 * @return
	 */
	public int getRowsCount() {
		return this.intRowsCount;
	}

	/**
	 * 设置最大行数
	 * 
	 * @param intParamCount
	 */
	private void setAllRowsCount(int intParamCount) {
		this.allRowsCount = intParamCount;
	}

	/**
	 * 取最大行数
	 * 
	 * @return
	 */
	public int getAllRowsCount() {
		return allRowsCount;
	}

	/**
	 * 取最大行数
	 * 
	 * @return
	 */
	public int getMaxPage() {
		return count_page;
	}

	/**
	 * 是否还有下一页
	 * 
	 * @return
	 */
	public boolean ifNext() {
		return currentPage < getMaxPage();
	}

	public boolean executeQuery(String strParamSQL) {
		if (StringHelper.isEmpty(strParamSQL)) {
			return false;
		} else {
			setQuerySQL(strParamSQL);
			return executeQuery();
		}
	}

	public boolean executePageQuery(String strParamSQL) {
		if (StringHelper.isEmpty(strParamSQL)) {
			return false;
		} else {
			setQuerySQL(strParamSQL);
			return executePageQuery();
		}
	}

	public QueryPageBean(boolean outDebug, boolean outError) {
		this.outDebug = outDebug;
		this.outError = outError;
	}

	public boolean executeQuery() {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsMetaData = null;
		int columnCount = 0;
		String strError = "";
		int beginNum = 0;
		int endNum = 0;
		int tempNum = 0;

		if (strQuerySQL.trim().equals("")) {
			return false;
		}

		if (currentPage < 1) { // 页码不符合自然规律
			return false;
		}
		try {
			LogHelper.logDebug(QueryPageBean.class, "分页查询SQL:"+this.getQuerySQL());
			conn = DbConn.getConn();
			pStmt = conn
					.prepareStatement(this.getQuerySQL(),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			long lm = System.currentTimeMillis();
			rs = pStmt.executeQuery();
			lm = System.currentTimeMillis() - lm;
			if (lm > 5000) {
				LogHelper.logFile("badSQL.log", "com.changing.framework.db.QueryPageBean.executeQuery()方法执行SQL语句耗时： " + lm
						+ " SQL语句为:" + strQuerySQL);
			}
			rsMetaData = rs.getMetaData();
			columnCount = rsMetaData.getColumnCount();
			rs.last();
			tempNum = rs.getRow();
			if (tempNum / perPage < currentPage - 1) {
				currentPage = 1;
			}
			if (tempNum % perPage == 0)
				count_page = tempNum / perPage;
			else
				count_page = tempNum / perPage + 1;
			if (count_page > 0) {
				if (currentPage > count_page)
					currentPage = count_page; // 当前页超过最大页，则置为最后一页
			} else {
				currentPage = 1; // 如果查询结果为一页，则置为第一页
			}

			beginNum = (currentPage - 1) * perPage;
			endNum = currentPage * perPage;

			vector = new Vector(columnCount);

			for (int j = beginNum; j < endNum; j++) {
				if (rs.absolute(j + 1)) {
					Hashtable tempHashtable = new Hashtable();
					Object tempObject = new Object();
					String tempColumn = "";
					for (int i = 1; i <= columnCount; i++) {
						tempColumn = rsMetaData.getColumnName(i);
						vector.add(tempColumn);
						tempObject = rs.getObject(i);
						if (tempObject == null) {
							tempHashtable.put(tempColumn.toUpperCase(), "");
						} else {
							tempHashtable.put(tempColumn.toUpperCase(),
									tempObject);
						}
					}
					valueVector.addElement(tempHashtable);
				}
			}
			setRowsCount(valueVector.size()); // 设置当前行数
			setAllRowsCount(tempNum); // 设置总行数
		} catch (java.lang.Exception e) {
			strError = "QueryPageBean的executeQuery的executeQuery方法执行查询失败:"
					+ e.toString();
			LogHelper.logError(QueryPageBean.class,strError);
			e.printStackTrace();
			return false;
		} finally {
			try {
				DbConn.close(conn, pStmt, rs);
			} catch (Exception ex) {
				LogHelper.logError(QueryPageBean.class,strError);
				ex.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 */
	public boolean executePageQuery() {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsMetaData = null;
		int columnCount = 0;
		String strError = "";
		int beginNum = 0;
		int endNum = 0;
		int tempNum = 0;
		if (currentPage < 1) { // 页码不符合自然规律
			return false;
		}
		try {
			LogHelper.logDebug(QueryPageBean.class, "分页查询SQL:"+this.getQuerySQL());
			conn = DbConn.getConn();
			pStmt = conn.prepareStatement("select count(0) from ("
					+ this.getQuerySQL() + ") a");
			rs = pStmt.executeQuery();
			if (rs.next()) {
				tempNum = rs.getInt(1);
			}

			if (tempNum / perPage < currentPage - 1) {
				currentPage = 1;
			}
			beginNum = (currentPage - 1) * perPage;
			endNum = currentPage * perPage;
			if (tempNum % perPage == 0)

				count_page = tempNum / perPage;
			else
				count_page = tempNum / perPage + 1;
			if (count_page > 0) {
				if (currentPage > count_page)
					currentPage = count_page; // 当前页超过最大页，则置为最后一页
			} else {
				currentPage = 1; // 如果查询结果为一页，则置为第一页
			}
			String strTemp = "";
			if (dbType.equals(DbConfig.ORACLE)) {
				strTemp = "select * from (select t__.*,rownum r__ from ("
						+ this.getQuerySQL() + " ) t__  ) where r__ > "
						+ beginNum + " and r__<= " + endNum;
			} else if (dbType.equals(DbConfig.MYSQL)) {
				strTemp = getQuerySQL() + " limit " + beginNum + ","
						+ (endNum - beginNum);
			} else if (dbType.equals(DbConfig.SQLSERVER)) {
				// sqlserver翻页代码
			}
			pStmt = conn.prepareStatement(strTemp);
			long lm = System.currentTimeMillis();
			rs = pStmt.executeQuery();
			lm = System.currentTimeMillis() - lm;
			if (lm > 5000) {
				LogHelper.logFile("badSQL.log", "com.changing.framework.db.QueryPageBean.executeQuery()方法执行SQL语句耗时： " + lm
						+ " SQL语句为:" + strQuerySQL);
			}
			rsMetaData = rs.getMetaData();
			columnCount = rsMetaData.getColumnCount();

			vector = new Vector(columnCount);
			while (rs.next()) {
				Hashtable tempHashtable = new Hashtable();
				Object tempObject = new Object();
				String tempColumn = "";
				for (int i = 1; i <= columnCount; i++) {
					tempColumn = rsMetaData.getColumnName(i);
					vector.add(tempColumn);
					tempObject = rs.getObject(i);
					if (tempObject == null) {
						tempHashtable.put(tempColumn.toUpperCase(), "");
					} else {
						tempHashtable.put(tempColumn.toUpperCase(), tempObject);
					}
				}
				valueVector.addElement(tempHashtable);
			}
			setIntRowsCount(valueVector.size()); // 设置当前行数
			setAllRowsCount(tempNum); // 设置总行数
		} catch (Exception e) {
			strError = "QueryPageBean的executePageQuery的executeQuery方法执行查询失败:"
					+ e.toString();
			LogHelper.logError(QueryPageBean.class,strError);
			e.printStackTrace();
			return false;
		} finally {
			DbConn.close(conn, pStmt, rs);
		}
		return true;

	}

	/**
	 * 获取指定行指定字段的字段值
	 * 
	 * @param strFieldName
	 * @param intRowsNo
	 * @return
	 */
	public String getValue(String strFieldName, int intRowsNo) {
		Hashtable tempHashtable = new Hashtable();
		Object tempObject = new Object();

		if ((intRowsNo < 1) || (intRowsNo > getRowsCount())) {
			return "错误：输入参数超出结果集范围：1－" + getRowsCount();
		}
		tempHashtable = (Hashtable) valueVector.elementAt(intRowsNo - 1);
		if (tempHashtable == null) {
			return "";
		} else {
			tempObject = tempHashtable.get(strFieldName.toUpperCase());
			if (tempObject == null) {
				return "";
			} else {
				return tempObject.toString();
			}
		}
	}

	/**
	 * 获取指定行指定字段的字段值
	 * 
	 * @param intCount
	 * @param intRowsNo
	 * @return
	 */
	public String getValue(int intColumnNo, int intRowsNo) {
		if (vector == null || vector.size() <= 0) {
			return "";
		}
		if (intColumnNo > vector.size()) {
			return "";
		} else {
			return getValue((String) vector.elementAt(intColumnNo - 1),
					intRowsNo);
		}
	}

	/**
	 * 获得指定列的INT值，如果该返回值有小数位则自动四舍五入
	 * 
	 * @param strFieldName
	 *            指定字段的名称
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public int getIntValue(String strFieldName, int intRowsNo, int defaultValue) {
		String str = StringHelper
				.trimToEmpty(getValue(strFieldName, intRowsNo));
		if (str.equals("")) {
			return defaultValue;
		} else {
			return NumberHelper.toInt(StringHelper.object45(str, 0,
					defaultValue + ""), defaultValue);
		}
	}

	/**
	 * 获得指定字段的float值，不进行四舍五入
	 * 
	 * @param strFieldName
	 *            指定字段的名称
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public float geFloatValue(String strFieldName, int intRowsNo,
			float defaultValue) {
		String str = StringHelper
				.trimToEmpty(getValue(strFieldName, intRowsNo));
		if (str.equals("")) {
			return defaultValue;
		} else {
			return NumberHelper.toFloat(str, 0);
		}
	}

	/**
	 * 获得指定字段的float值，进行四舍五入
	 * 
	 * @param strFieldName
	 *            指定字段的名称
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public float geFloatValue(String strFieldName, int intRowsNo, int fraction,
			float defaultValue) {
		String str = StringHelper
				.trimToEmpty(getValue(strFieldName, intRowsNo));
		if (str.equals("")) {
			return defaultValue;
		} else {
			return NumberHelper.roundValue(str, fraction, defaultValue);
		}
	}
	/**
	 * bean清空，以便下次调用
	 */
	public void clear() {
		setRowsCount(0);
		try {
			if (valueVector != null) {
				valueVector.clear();
			}
		} catch (Exception e) {
		}
	}
	/**
	 * 获取导航条
	 * 
	 * @param frm
	 *            表单域名称
	 * @return
	 */
	public String getNavi(String frm) {
		StringBuffer tempStr = new StringBuffer();
		tempStr.append("<div class=\"goPage\" style=\"margin:8px;\">\n");
		tempStr.append("<input type=\"hidden\" name=\"pageNumber\" value=\""
				+ this.currentPage + "\">\n");
		int max_page = getCount_page(); // 获取最大页数
		if (max_page > 1) { // 如果页数小于2，那么不显示导航条
			int all_count = getAllRowsCount();
			int first = (this.currentPage - 1) / 10 * 10 + 1; // 每次显示10页，取开始页
			int last = first + 9;
			if (last > max_page)
				last = max_page; // 如果超出最大页，则取最大页数
			if (this.currentPage == 1)
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toFirst.gif\" title=\"首页\">&nbsp;\n");
			else
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toFirst.gif\" onclick=\"navi_pageTo(1)\" title=\"首页\">&nbsp;\n");
			if (first == 1)
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toPrevTen.gif\" title=\"前10页\">&nbsp;\n");
			else
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toPrevTen.gif\" onclick=\"navi_pageTo("
								+ (first - 10) + ")\" title=\"前10页\">&nbsp;\n");
			if (this.currentPage == 1)
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toPrev.gif\" title=\"上一页\">&nbsp;\n");
			else
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toPrev.gif\" onclick=\"navi_pageTo("
								+ (this.currentPage - 1)
								+ ")\" title=\"上一页\">&nbsp;\n");
			for (int i = first; i <= last; i++) {
				if (i == this.currentPage) {
					tempStr.append("<a href=\"javascript:navi_pageTo(" + i
							+ ")\" " + "style=\"text-decoration:underline\">"
							+ i + "</a>&nbsp;\n");
				} else {
					tempStr.append("<a href=\"javascript:navi_pageTo(" + i
							+ ")\" " + "style=\"text-decoration:none\">" + i
							+ "</a>&nbsp;\n");
				}
			}
			if (this.currentPage == max_page)
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toNext.gif\" title=\"下一页\">&nbsp;\n");
			else
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toNext.gif\" onclick=\"navi_pageTo("
								+ (this.currentPage + 1)
								+ ")\" title=\"下一页\">&nbsp;\n");
			if (last == max_page)
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toNextTen.gif\" title=\"后10页\">&nbsp;\n");
			else
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toNextTen.gif\" onclick=\"navi_pageTo("
								+ (last + 1) + ")\" title=\"后10页\">&nbsp;\n");
			if (this.currentPage == max_page)
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toLast.gif\" title=\"末页\">&nbsp;\n");
			else
				tempStr
						.append("<img class=\"imgBtn\" align=\"absmiddle\" src=\""
								+ SysConfig.getSERVER()
								+ "/css/images/toLast.gif\" onclick=\"navi_pageTo("
								+ max_page + ")\" title=\"末页\">&nbsp;\n");
			tempStr.append("&nbsp;&nbsp;共" + max_page + "页" + all_count
					+ "条记录\n");
		}
		tempStr.append("</div>\n");
		tempStr.append("<script>\n");
		tempStr.append("<!--\n");
		tempStr.append("function navi_pageTo(page){\n");
		tempStr.append("if(showSending()){");
		tempStr.append("\tdocument." + frm + ".pageNumber.value=page;\n");
		tempStr.append("\tdocument." + frm + ".submit();\n");
		tempStr.append("}}\n");
		tempStr.append("-->\n");
		tempStr.append("</script>\n");
		return tempStr.toString();
	}

	/**
	 * 根据指定的数字，获取指定行的序号
	 * 
	 * @param paramNumber
	 * @return
	 */
	public int getOrder(int paramNumber) {
		return (this.currentPage - 1) * this.perPage + paramNumber;
	}

	public int getIntRowsCount() {
		return intRowsCount;
	}

	/**
	 * 设置当前页实际显示的数据条数
	 * 
	 * @param intRowsCount
	 */
	public void setIntRowsCount(int intRowsCount) {
		this.intRowsCount = intRowsCount;
	}

	public int getCount_page() {
		return count_page;
	}

	/**
	 * 设置数据页数
	 * 
	 * @param count_page
	 */
	public void setCount_page(int count_page) {
		this.count_page = count_page;
	}

	public String getstrQuerySQL() {
		return strQuerySQL;
	}

	public void setstrQuerySQL(String strQuerySQL) {
		this.strQuerySQL = strQuerySQL;
	}

	public boolean isOutDebug() {
		return outDebug;
	}

	public void setOutDebug(boolean outDebug) {
		this.outDebug = outDebug;
	}

	public boolean isOutError() {
		return outError;
	}

	public void setOutError(boolean outError) {
		this.outError = outError;
	}

	public String getStrQuerySQL() {
		return strQuerySQL;
	}

	public void setStrQuerySQL(String strQuerySQL) {
		this.strQuerySQL = strQuerySQL;
	}

	public Vector getValueVector() {
		return valueVector;
	}

	public void setValueVector(Vector valueVector) {
		this.valueVector = valueVector;
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}

}
