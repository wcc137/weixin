/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：QueryOneBean.java
 *@功能描述:单条数据查询，返回单条数据
 *@创建人  ：zn
 *@创建时间: 2011-8-8 上午09:20:06
 *@完成时间：2011-8-8 上午09:20:06
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
public class QueryOneBean {
	private Vector fieldVector = new Vector();
	private Vector valueVector = new Vector();
	int fieldCount = 0;// 对应字段数
	int valueCount = 0;// 数据值条数
	private boolean outDebug = SysConfig.isOutDebug();
	private boolean outError = SysConfig.isOutError();

	public QueryOneBean() {

	}

	public QueryOneBean(boolean outDebug, boolean outError) {
		this.outDebug = outDebug;
		this.outError = outError;
	}

	/**
	 * 执行数据查询
	 * 
	 * @return
	 */
	public boolean executeQuery(String strParamSQL) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsMetaData = null;
		int columnCount = 0;
		String strError = "";
		if (strParamSQL.trim().equals("")) {
			return false;
		}
		try {
			conn = DbConn.getConn();
			stmt = conn.createStatement();
			long lm = System.currentTimeMillis();
			rs = stmt.executeQuery(strParamSQL);
			lm = System.currentTimeMillis() - lm;
			LogHelper.logDebug(QueryOneBean.class,"单条记录查询SQL:"+strParamSQL);
			if (lm > 5000) {
				LogHelper.logFile("badSQL.log", "com.changing.framework.db.QueryOneBean.executeQuery(String strParamSQL)方法执行SQL语句耗时： " + lm
						+ " SQL语句为:" + strParamSQL);
			}
			rsMetaData = rs.getMetaData();
			columnCount = rsMetaData.getColumnCount(); // 获得结果集中一条数据的字段数
			if (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String temp = "";
					fieldVector.addElement(rsMetaData.getColumnName(i)
							.toUpperCase()); // 字段名
					try {
						temp = rs.getString(i); // 值
					} catch (Exception e) {
						temp = "";
					}
					try {
						if (rs.getString(i).substring(0, 1).equals(".")) {
							temp = "0" + rs.getString(i); // 值
						}

					} catch (Exception e) {
					}

					if (temp == null) {
						temp = "";
					}
					valueVector.addElement(temp);
				}
			}
			setFieldCount(this.fieldVector.size());
			setValueCount(this.valueVector.size());
			if (fieldVector.size() != valueVector.size()) {
				return false;
			}

		} catch (Exception e) {
			strError = "QueryOneBean的executeQuery方法创建语句失败:" + strParamSQL
					+ "   " + e.toString();
			LogHelper.logError(QueryOneBean.class,strError);
			return false;
		} finally {
			try {
				DbConn.close(conn, stmt, rs);
			} catch (Exception ex) {
				LogHelper.logError(QueryOneBean.class,"QueryOneBean 关闭数据库出现异常！"+ex.getMessage());
			}
		}
		return true;
	}

	/**
	 * 获取指定字段名称的字段值
	 * 
	 * @param strFieldName
	 * @return
	 */
	public String getValue(String strFieldName) {
		int intIndex = 0;
		intIndex = fieldVector.indexOf(strFieldName.toUpperCase());
		if (intIndex == -1) {
			return "";
		} else {
			return (String) valueVector.get(intIndex);
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
	public int getIntValue(String strFieldName, int defaultValue) {
		String str = StringHelper.trimToEmpty(getValue(strFieldName));
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
	public float geFloatValue(String strFieldName, float defaultValue) {
		String str = StringHelper.trimToEmpty(getValue(strFieldName));
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
	public float geFloatValue(String strFieldName, int fraction,
			float defaultValue) {
		String str = StringHelper.trimToEmpty(getValue(strFieldName));
		if (str.equals("")) {
			return defaultValue;
		} else {
			return NumberHelper.roundValue(str, fraction, defaultValue);
		}
	}

	/**
	 * 获取指定序号的字段值
	 * 
	 * @param intCol
	 * @return
	 */
	public String getValue(int intCol) {
		String strValue = "";

		if ((intCol < 1) || (intCol > getValueCount())) {
			return "错误：输入参数超出结果集范围：1－" + getValueCount();
		}
		try {
			strValue = (String) valueVector.get(intCol - 1);
		} catch (Exception e) {
			return "";
		}
		return strValue;
	}

	/**
	 * 查询某一单独字段的值
	 * 
	 * @param columnName
	 * @param tableName
	 * @param sqlWhere
	 *            需要加where
	 * @return
	 */
	public String selectOnly(String columnName, String tableName,
			String sqlWhere) throws SQLException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String returnValue = "";
		try {
			conn = DbConn.getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select " + columnName + " from "
					+ tableName + " " + sqlWhere);

			if (rs.next()) {
				returnValue = rs.getString(1);
				if (returnValue == null) {
					returnValue = "";
				}
			}
		} catch (Exception ex) {
			LogHelper.logError(QueryOneBean.class,ex);
		} finally {
			DbConn.close(conn, stmt, rs);
		}
		return returnValue;
	}

	/**
	 * bean清空，以便下次调用
	 */
	public void clear() {
		if (fieldVector != null) {
			fieldVector.clear();
		}
		if (valueVector != null) {
			valueVector.clear();
		}
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

	public int getFieldCount() {
		return fieldCount;
	}

	public Vector getFieldVector() {
		return fieldVector;
	}

	public void setFieldVector(Vector fieldVector) {
		this.fieldVector = fieldVector;
	}

	public Vector getValueVector() {
		return valueVector;
	}

	public void setValueVector(Vector valueVector) {
		this.valueVector = valueVector;
	}

	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}

	public int getValueCount() {
		return valueCount;
	}

	public void setValueCount(int valueCount) {
		this.valueCount = valueCount;
	}

}
