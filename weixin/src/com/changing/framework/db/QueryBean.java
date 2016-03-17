/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：QueryBean.java
 *@功能描述:数据查询Bean
 *@创建人  ：zn
 *@创建时间: 2011-8-5 下午07:15:21
 *@完成时间：2011-8-5 下午07:15:21
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
import java.util.Hashtable;
import java.util.Vector;
import com.changing.framework.config.SysConfig;
import com.changing.framework.helper.LogHelper;
import com.changing.framework.helper.NumberHelper;
import com.changing.framework.helper.StringHelper;
/**
 * @author Administrator <p> 功能描述: <p> 使用示例： <p>
 */
public class QueryBean {
	private Vector vector = null;
	private Vector valueVector = new Vector();
	private int intRowsCount = 0;
	private boolean outDebug = SysConfig.isOutDebug();
	private boolean outError = SysConfig.isOutError();
	
	public QueryBean() {

	}
	public QueryBean(boolean outDebug, boolean outError) {
		this.outDebug = outDebug;
		this.outError = outError;
	}

	public boolean executeQuery(String strParamSQL) {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		ResultSetMetaData rsMetaData = null;
		int columnCount = 0;
		String strError = "";
		if (StringHelper.isNum(strParamSQL)) {
			return false;
		}
		try {
			conn = DbConn.getConn();
			stmt = conn.createStatement();
			long lm = System.currentTimeMillis();
			LogHelper.logDebug(QueryBean.class, "executeQuery(String strParamSQL)查询SQL:"+strParamSQL);
			rs = stmt.executeQuery(strParamSQL);
			lm = System.currentTimeMillis() - lm;
			if (lm > 5000) {
				LogHelper.logFile("badSQL.log", "com.changing.framework.db.QueryBean.executeQuery(String strParamSQL)方法执行SQL语句耗时： " + lm + " SQL语句为:" + strParamSQL);
			}
			rsMetaData = rs.getMetaData();
			columnCount = rsMetaData.getColumnCount();
			vector = new Vector(columnCount);
			while (rs.next()) {
				Hashtable tempHashtable = new Hashtable();
				Object tempObject = new Object();
				String tempColumn = "";
				for (int i = 1; i <= columnCount; i++) {
					tempColumn = rsMetaData.getColumnName(i); // 字段名
					vector.add(tempColumn);
					tempObject = rs.getObject(i); // 值
					if (tempObject == null) {
						tempHashtable.put(tempColumn.toUpperCase(), "");
					} else {
						tempHashtable.put(tempColumn.toUpperCase(), tempObject);
					}
				}
				valueVector.addElement(tempHashtable);
			}
			setRowsCount(valueVector.size());
		} catch (Exception e) {
			strError = "QueryBean的executeQuery方法执行查询失败:" + e.toString() + "[" + strParamSQL + "]";
			LogHelper.logError(QueryBean.class, strError);
			e.printStackTrace();
			return false;
		} finally {
			DbConn.close(conn, stmt, rs);
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
		}
		tempObject = tempHashtable.get(strFieldName.toUpperCase());
		if (tempObject == null) {
			return "";
		} else {
			return tempObject.toString();
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
			return getValue((String) vector.elementAt(intColumnNo - 1), intRowsNo);
		}
	}

	/**
	 * 获得指定列的INT值，如果该返回值有小数位则自动四舍五入
	 * 
	 * @param strFieldName
	 *        指定字段的名称
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public int getIntValue(String strFieldName, int intRowsNo, int defaultValue) {
		String str = StringHelper.trimToEmpty(getValue(strFieldName, intRowsNo));
		if (str.equals("")) {
			return defaultValue;
		} else {
			return NumberHelper.toInt(StringHelper.object45(str, 0, defaultValue + ""), defaultValue);
		}
	}

	/**
	 * 获得指定字段的float值，不进行四舍五入
	 * 
	 * @param strFieldName
	 *        指定字段的名称
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public float geFloatValue(String strFieldName, int intRowsNo, float defaultValue) {
		String str = StringHelper.trimToEmpty(getValue(strFieldName, intRowsNo));
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
	 *        指定字段的名称
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public float geFloatValue(String strFieldName, int intRowsNo, int fraction, float defaultValue) {
		String str = StringHelper.trimToEmpty(getValue(strFieldName, intRowsNo));
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
	 * @param strColData
	 *        下拉框的value值
	 * @param strColLabel
	 *        下拉框的显示值
	 * @param strCol2Data
	 *        初始Value值
	 * @param strBl
	 *        是否有空值 true为有
	 * @return
	 */
	public String getSelectOption(String strColData, String strColLabel, String strCol2Data, String strBl) {
		StringBuffer tempStr = new StringBuffer();
		if (strBl.equals("true")) {
			tempStr.append("<option");
			tempStr.append(" value=\"-1\"");
			tempStr.append("></option>\n");
		}
		for (int i = 1; i <= getRowsCount(); i++) {
			if (getValue(strColData, i).equals(strCol2Data)) {
				tempStr.append("<option");
				tempStr.append(" value=\"");
				tempStr.append(getValue(strColData, i));
				tempStr.append("\" selected");
				tempStr.append(">");
				tempStr.append(getValue(strColLabel, i));
				tempStr.append("</option>\n");
			} else {
				tempStr.append("<option");
				tempStr.append(" value=\"");
				tempStr.append(getValue(strColData, i));
				tempStr.append("\"");
				tempStr.append(">");
				tempStr.append(getValue(strColLabel, i));
				tempStr.append("</option>\n");
			}
		}
		return tempStr.toString();
	}

	/**
	 * 取option项
	 * 
	 * @param strColData
	 *        下拉框的value值
	 * @param strColLabel
	 *        下拉框的显示值
	 * @return
	 */
	public String getSelectOption(String strColData, String strColLabel) {
		StringBuffer tempStr = new StringBuffer();
		for (int i = 1; i <= getRowsCount(); i++) {
			tempStr.append("<option");
			tempStr.append(" value=\"");
			tempStr.append(getValue(strColData, i));
			tempStr.append("\"");
			tempStr.append(">");
			tempStr.append(getValue(strColLabel, i));
			tempStr.append("</option>\n");
		}
		return tempStr.toString();
	}

	/**
	 * @param strColData
	 *        下拉框的value值
	 * @param strColLabel
	 *        下拉框的显示值
	 * @param strCol2Data
	 *        初始Value值
	 * @param strBl
	 *        是否有空值 true为有
	 * @return
	 */
	public String getSelectOption2(String strColData, String strColLabel, String strCol2Data, String strBl) {
		StringBuffer tempStr = new StringBuffer();
		if (strBl.equals("true")) {
			tempStr.append("<option");
			tempStr.append(" value=\"\"");
			tempStr.append("></option>\n");
		}
		for (int i = 1; i <= getRowsCount(); i++) {
			if (getValue(strColData, i).equals(strCol2Data)) {
				tempStr.append("<option");
				tempStr.append(" value=\"");
				tempStr.append(getValue(strColData, i));
				tempStr.append("\" selected");
				tempStr.append(">");
				tempStr.append(getValue(strColLabel, i));
				tempStr.append("</option>\n");
			} else {
				tempStr.append("<option");
				tempStr.append(" value=\"");
				tempStr.append(getValue(strColData, i));
				tempStr.append("\"");
				tempStr.append(">");
				tempStr.append(getValue(strColLabel, i));
				tempStr.append("</option>\n");
			}
		}
		return tempStr.toString();
	}


	public int getRowsCount() {
		return intRowsCount;
	}

	public void setRowsCount(int intRowsCount) {
		this.intRowsCount = intRowsCount;
	}

	public boolean isOutDebug() {
		return outDebug;
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}

	public Vector getValueVector() {
		return valueVector;
	}

	public void setValueVector(Vector valueVector) {
		this.valueVector = valueVector;
	}

	public int getIntRowsCount() {
		return intRowsCount;
	}

	public void setIntRowsCount(int intRowsCount) {
		this.intRowsCount = intRowsCount;
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

}
