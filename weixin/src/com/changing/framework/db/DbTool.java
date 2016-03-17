/**
 * 按照不同的条件获取表的组合ID
 */
package com.changing.framework.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.changing.framework.config.DbConfig;
import com.changing.framework.helper.LogHelper;
import com.changing.framework.helper.NumberHelper;
import com.changing.framework.helper.StringHelper;

public class DbTool {
	private String strSQL = null;
	/**
	 * 功能描叙: 得到某表的ID最大值，
	 * 
	 * @param strTableName
	 *        该表名称
	 * @param strCol
	 *        ID列名称
	 * @return ID最大值
	 * @throws SQLException
	 * @remark : 注意，该ID必须为整数类型，并且返回值是其最大值而不是最大值加一
	 */
	public long getMaxID(String strTableName, String strCol) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		long lMax = 0;
		try {
			conn = DbConn.getConn();
			String strSQL = "SELECT MAX(" + strCol + ") AS max FROM " + strTableName;
			LogHelper.logDebug(DbTool.class,"getMaxID方法sql:"+strSQL);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSQL);
			if (rs.next()) {
				lMax = rs.getLong("max");
			}
		} catch (SQLException sqlEx) {
			LogHelper.logError(DbTool.class,sqlEx);
			sqlEx.printStackTrace();
		} finally {
			DbConn.close(conn, stmt, rs);
		}
		return lMax;
	}

	/**
	 * 功能描叙: 得到某表的ID最大值(string)，存在限制条件
	 * 
	 * @param strTableName
	 *        该表名称
	 * @param strCol
	 *        ID列名称
	 * @param strWhere
	 *        sql 中的限制条件
	 * @return ID最大值
	 * @throws SQLException
	 * @remark : 注意，该ID必须为整数类型，并且返回值是其最大值而不是最大值加一
	 */
	public String getMaxIDwhere(String strTableName, String strCol, String strWhere) {
		Connection conn = null;
		Statement stmt = null;

		ResultSet rs = null;
		String lMax = "";
		try {
			
			conn = DbConn.getConn();
			String strSQL = "SELECT MAX(" + strCol + ") AS max FROM " + strTableName + " " + strWhere;
			LogHelper.logDebug(DbTool.class,"getMaxIDwhere方法sql:"+strSQL);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSQL);
			if (rs.next()) {
				lMax = rs.getString("max");
			}
		} catch (SQLException sqlEx) {
			LogHelper.logError(DbTool.class,sqlEx);
			sqlEx.printStackTrace();
		} finally {
			DbConn.close(conn, stmt, rs);
		}
		return lMax;
	}
	/**
	 * 获得表中某列的最大值然后加上间隔 
	 * @param strTableName 表名称
	 * @param strCol 对应列
	 * @param strWhere where条件
	 * @param jg 间隔
	 * @return
	 */
	public String getMaxIDwhere(String strTableName, String strCol, String strWhere,int jg) {
		return ""+(NumberHelper.toInt(getMaxIDwhere(strTableName,strCol,strWhere), 0)+jg);
	}
	
	/**
	 * 功能描叙: 得到某表的ID最大值，
	 * 
	 * @param strTableName
	 *        该表名称
	 * @param strCol
	 *        ID列名称
	 * @param aa
	 *        补齐的长度
	 * @return ID最大值
	 * @throws SQLException
	 * @remark : 注意，该ID必须为整数类型，并且返回值是其最大值而不是最大值加一
	 */
	public String getMaxID(String strTableName, String strCol, int aa) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String lMax = "";

		try {
			conn = DbConn.getConn();
			String strSQL = "SELECT MAX(LPAD(" + strCol + "," + aa + ",'0')) AS max FROM " + strTableName;
			LogHelper.logDebug(DbTool.class,"getMaxID方法sql:"+strSQL);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSQL);

			if (rs.next()) {
				lMax = rs.getString("max");
			}
		} catch (SQLException sqlEx) {
			LogHelper.logError(DbTool.class,sqlEx);
			sqlEx.printStackTrace();
			
		} finally {
			DbConn.close(conn, stmt, rs);
		}
		return lMax;
	}

	/**
	 * 取得到某表ID的下一个值(临时指定格式，不需要预先注册) 格式中年月日需以字符
	 * "yyyymmdd"、"yyyymm"、"yyyy-mm-dd"、"yyyy-mm"、"yyyy"、"yy-mm-dd"、"yy-mm"、"yymmdd"、"yymm"、 "yy"等开头
	 * getNextID("T_AJ_XTSZ_YIELD_MANUFACTURER", "SCCJBM","AA","ZZ","yymmdd0000")
	 * 
	 * @param strTableName
	 *        表名称
	 * @param strCol
	 *        列名称
	 * @param strPrefix
	 *        前缀
	 * @param strSubfix
	 *        后缀
	 * @param strFormat
	 *        格式："yyyymm00000"
	 * @return 下一个值ID值
	 */

	public String getNextID(String strTableName, String strCol, String strPrefix, String strSubfix, String strFormat) {
		String dbType = DbConfig.getDb_database();
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		// 参数错误
		if (strTableName == null || strTableName.equals("")) {
			return "参数错误: 表名不能为空";
		}

		if (strCol == null || strCol.equals("")) {
			return "参数错误: 列名不能为空";
		}

		if (strFormat == null || strFormat.equals("")) {
			return "参数错误: 格式不能为空";
		}

		// 年月日
		String strYear = "";
		String strShortYear = "";
		String strMonth = "";
		String strDay = "";
		if (dbType.equals(DbConfig.MYSQL)) {
			strSQL = "SELECT DATE_FORMAT(now(),'%Y') as yyyy,DATE_FORMAT(now(),'%y') as yy,DATE_FORMAT(now(),'%m') as mm,DATE_FORMAT(now(),'%d') as dd";
		} else if (dbType.equals(DbConfig.ORACLE)) {
			strSQL = "select to_char(sysdate,'yyyy') as yyyy, to_char(sysdate,'yy') as yy,to_char(sysdate,'mm') as mm, to_char(sysdate,'dd') as dd from dual ";
		} else if (dbType.equals(DbConfig.SQLSERVER)) {
			strSQL = " SELECT CONVERT(VARCHAR(4),getdate(),120) as yyyy,CONVERT(VARCHAR(2),getdate(),120) as yy,"
					+ " Right(100+DATEPART(m,GETDATE()),2) as mm,Right(100+DATEPART(d,GETDATE()),2) as dd";
		} else {
			strSQL = "SELECT DATE_FORMAT(now(),'%Y') as yyyy,DATE_FORMAT(now(),'%y') as yy,DATE_FORMAT(now(),'%m') as mm,DATE_FORMAT(now(),'%d') as dd";
		}
	
		try {
			LogHelper.logDebug(DbTool.class,"getNextID()方法sql:"+strSQL);
			// 创建数据库连接
			conn = DbConn.getConn();
			pStmt = conn.prepareStatement(this.strSQL);
			rs = pStmt.executeQuery();
			// 查记录
			if (rs.next()) {
				// 赋值
				strYear = rs.getString("yyyy");
				strShortYear = rs.getString("yy");
				strMonth = rs.getString("mm");
				strDay = rs.getString("dd");
			} else {
				return "取数据库年月日错误!";
			}
		} catch (Exception e) {
			LogHelper.logError(DbTool.class,e);
			e.printStackTrace();
			return "数据库例外";
		} finally {
			DbConn.close(conn, pStmt, rs);
		}
		// 前后缀输入初值
		if (strSubfix == null) {
			strSubfix = "";
		}
		if (strPrefix == null) {
			strPrefix = "";

		}
		// 流水号长度
		int aa = 0;

		// 格式分析 "yyyymmdd00000","yyyymm00000","yyyy00000","00000"
		String s1 = "yyyymmdd";
		String s2 = "yyyymm";
		String s3 = "yyyy-mm-dd";
		String s4 = "yyyy-mm";
		String s5 = "yyyy";
		String s6 = "yy-mm-dd";
		String s7 = "yy-mm";
		String s8 = "yymmdd";
		String s9 = "yymm";
		String s10 = "yy";

		if (strFormat.startsWith(s1) || strFormat.startsWith(s1.toUpperCase())) { // "yyyymmdd";
			aa = strFormat.length() - s1.length();
			strPrefix += (strYear + strMonth + strDay); // "yyyymmdd";
		} else if (strFormat.startsWith(s2) || strFormat.startsWith(s2.toUpperCase())) {
			aa = strFormat.length() - s2.length();
			strPrefix += (strYear + strMonth); // "yyyymm";
		} else if (strFormat.startsWith(s3) || strFormat.startsWith(s3.toUpperCase())) {
			aa = strFormat.length() - s3.length();
			strPrefix += (strYear + "-" + strMonth + "-" + strDay); // "yyyy-mm-dd";
		} else if (strFormat.startsWith(s4) || strFormat.startsWith(s4.toUpperCase())) {
			aa = strFormat.length() - s4.length();
			strPrefix += (strYear + "-" + strMonth); // "yyyy-mm";
		} else if (strFormat.startsWith(s5) || strFormat.startsWith(s5.toUpperCase())) {
			aa = strFormat.length() - s5.length();
			strPrefix += strYear; // "yyyy"
		} else if (strFormat.startsWith(s6) || strFormat.startsWith(s6.toUpperCase())) {
			aa = strFormat.length() - s6.length();
			strPrefix += (strShortYear + "-" + strMonth + "-" + strDay); // "yy-mm-dd";
		} else if (strFormat.startsWith(s7) || strFormat.startsWith(s7.toUpperCase())) {
			aa = strFormat.length() - s7.length();
			strPrefix += (strShortYear + "-" + strMonth); // "yy-mm";
		} else if (strFormat.startsWith(s8) || strFormat.startsWith(s8.toUpperCase())) {
			aa = strFormat.length() - s8.length();
			strPrefix += (strShortYear + strMonth + strDay); // "yymmdd";
		} else if (strFormat.startsWith(s9) || strFormat.startsWith(s9.toUpperCase())) {
			aa = strFormat.length() - s9.length();
			strPrefix += (strShortYear + strMonth); // "yymm";
		} else if (strFormat.startsWith(s10) || strFormat.startsWith(s10.toUpperCase())) {
			aa = strFormat.length() - s10.length();
			strPrefix += (strShortYear); // "yy";
		} else if (strFormat.indexOf(s1) > 0 || strFormat.indexOf(s1.toUpperCase()) > 0) { // "yyyymmdd";
			aa = strFormat.length() - s1.length();
			strSubfix += (strYear + strMonth + strDay); // "yyyymmdd";
		} else if (strFormat.indexOf(s2) > 0 || strFormat.indexOf(s2.toUpperCase()) > 0) {
			aa = strFormat.length() - s2.length();
			strSubfix += (strYear + strMonth); // "yyyymm";
		} else if (strFormat.indexOf(s3) > 0 || strFormat.indexOf(s3.toUpperCase()) > 0) {
			aa = strFormat.length() - s3.length();
			strSubfix += (strYear + "-" + strMonth + "-" + strDay); // "yyyy-mm-dd";
		} else if (strFormat.indexOf(s4) > 0 || strFormat.indexOf(s4.toUpperCase()) > 0) {
			aa = strFormat.length() - s4.length();
			strSubfix += (strYear + "-" + strMonth); // "yyyy-mm";
		} else if (strFormat.indexOf(s5) > 0 || strFormat.indexOf(s5.toUpperCase()) > 0) {
			aa = strFormat.length() - s5.length();
			strSubfix += strYear; // "yyyy"
		} else if (strFormat.indexOf(s6) > 0 || strFormat.indexOf(s6.toUpperCase()) > 0) {
			aa = strFormat.length() - s6.length();
			strSubfix += (strShortYear + "-" + strMonth + "-" + strDay); // "yy-mm-dd";
		} else if (strFormat.indexOf(s7) > 0 || strFormat.indexOf(s7.toUpperCase()) > 0) {
			aa = strFormat.length() - s7.length();
			strSubfix += (strShortYear + "-" + strMonth); // "yy-mm";
		} else if (strFormat.indexOf(s8) > 0 || strFormat.indexOf(s8.toUpperCase()) > 0) {
			aa = strFormat.length() - s8.length();
			strSubfix += (strShortYear + strMonth + strDay); // "yymmdd";
		} else if (strFormat.indexOf(s9) > 0 || strFormat.indexOf(s9.toUpperCase()) > 0) {
			aa = strFormat.length() - s9.length();
			strSubfix += (strShortYear + strMonth); // "yymm";
		} else if (strFormat.indexOf(s10) > 0 || strFormat.indexOf(s10.toUpperCase()) > 0) {
			aa = strFormat.length() - s10.length();
			strSubfix += (strShortYear); // "yy";
		} else

		{ // "其它";
			aa = strFormat.length();
		}
		return getNextID(strTableName, strCol, strPrefix, strSubfix, aa);
	}

	/**
	 * 取得到某表ID的下一个值(不指定格式,但指定流水号长度,不需要预先注册) getNextID("TABLE_NAME", "PDIR_ID","M","",4)
	 * 
	 * @param strTableName
	 *        表名称
	 * @param strCol
	 *        列名称
	 * @param strPrefix
	 *        前缀
	 * @param strSubfix
	 *        后缀
	 * @return 下一个值ID值
	 */

	public String getNextID(String strTableName, String strCol, String strPrefix, String strSubfix, int aa) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		// 参数错误
		if (strTableName == null || strTableName.equals("")) {
			return "参数错误: 表名不能为空";
		}
		if (strCol == null || strCol.equals("")) {
			return "参数错误: 列名不能为空";
		}
		if (aa < 0) {
			return "参数错误: 流水号位数不能小于0";
		}

		String strReturn = "";
		String strLine = "";
		String strLike = "";
		// 通配字符串
		for (int i = 1; i <= aa; i++) {
			strLine += "_";
		}

		if (strPrefix == null) {
			strPrefix = "";
		}
		if (strPrefix == null) {
			strPrefix = "";
		}
		boolean hasPrefix = !(strPrefix == null || strPrefix.equals(""));
		boolean hasSubfix = !(strSubfix == null || strSubfix.equals(""));

		if (hasPrefix && hasSubfix) { // 前后缀都存在
			strLike = strPrefix + strLine + strSubfix;
			strSQL = "SELECT " + "'" + strPrefix + "'||LPAD(MAX(substr(" + strCol + ",1+length('" + strPrefix + "'),"
					+ aa + ")) + 1," + aa + ",'0')||'" + strSubfix + "'" + " AS max, count(" + strCol
					+ ") as count FROM " + strTableName + " WHERE " + strCol + " like '" + strLike + "'";
		} else if (hasPrefix && !hasSubfix) { // 前缀存在，后缀不存在
			strLike = strPrefix + strLine;
			strSQL = "SELECT " + "'" + strPrefix + "'||LPAD(MAX(substr(" + strCol + ",1+length('" + strPrefix + "'),"
					+ aa + ")) + 1," + aa + ",'0') " + " AS max, count(" + strCol + ") as count FROM " + strTableName
					+ " WHERE " + strCol + " like '" + strLike + "'";
		} else if (!hasPrefix && hasSubfix) { // 后缀存在，前缀不存在
			strLike = strLine + strSubfix;
			strSQL = "SELECT " + " LPAD(MAX(substr(" + strCol + ",1," + aa + ")) + 1," + aa + ",'0')||'" + strSubfix
					+ "'" + " AS max, count(" + strCol + ") as count FROM " + strTableName + " WHERE " + strCol
					+ " like '" + strLike + "'";
		} else { // 后缀不存在，前缀不存在
			strLike = strLine;
			strSQL = "SELECT " + " LPAD(MAX(" + strCol + ")+1," + aa + ",'0') " + " AS max, count(" + strCol
					+ ") as count FROM " + strTableName + " WHERE " + strCol + " like '" + strLike + "'";
		}
		
		try {
			LogHelper.logDebug(DbTool.class,"getNextID()方法sql:"+strSQL);
			// 创建数据库连接
			conn = DbConn.getConn();
			pStmt = conn.prepareStatement(this.strSQL);
			rs = pStmt.executeQuery();
			// 查单条记录
			if (rs.next()) {
				// 赋值
				strReturn = rs.getString("max");
				int intCount = rs.getInt("count");
				// 空值时的处理 strPrefix + "0001" + strSubfix
				if (strReturn == null || intCount == 0) {
					String s = "1";
					for (int j = 1; j < aa; j++) {
						s = "0" + s;
					}
					strReturn = strPrefix + s + strSubfix;
				}
			}
		} catch (Exception e) {
			LogHelper.logError(DbTool.class,e);
		} finally {
			DbConn.close(conn, pStmt, rs);
		}
		return strReturn;
	}

	//---------------------------工作流需要添加------------------------
	/**
	 * 取得到某表ID的下一个值(要先在t_xt_dmgsdy中注册代码格式) getNextID("T_AJ_XTSZ_YIELD_MANUFACTURER", "SCCJBM")
	 * 
	 * @param strTableName
	 *        表名称
	 * @param strCol
	 *        列名称
	 * @return 下一个值ID值
	 */
	public String getNextID(String strTableName, String strCol) {
		Connection conn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		//参数错误
		if (strTableName == null || strTableName.equals("")) {
			return "参数错误: 表名不能为空";
		}

		if (strCol == null || strCol.equals("")) {
			return "参数错误: 列名不能为空";
		}

		String strReturn = "";

		strSQL = "select dmmc,qz,hz,gs from t_xt_dmgsdy where bmc = '" + strTableName + "' and lmc = '" + strCol + "'";
		
		try {
			//前后缀及格式
			String strPrefix = "";
			String strSubfix = "";
			String strFormat = "";
			conn = DbConn.getConn();
			pStmt = conn.prepareStatement(this.strSQL);
			rs = pStmt.executeQuery();
			//查单条记录
			if (rs.next()) {
				//赋值
				strPrefix = rs.getString("qz");
				strSubfix = rs.getString("hz");
				strFormat = rs.getString("gs");
				//调用方法赋值
				strReturn = getNextID(strTableName, strCol, strPrefix, strSubfix, strFormat);

			} else {
				return "在t_xt_dmgsdy中不存在!";
			}
		} catch (Exception e) {
			strReturn = "数据库例外";
			//LogBean.getLogBean().writeLog(strSQL);
			e.printStackTrace();
		} finally {
			try {
				DbConn.close(conn, pStmt, rs);
			} catch (Exception ex2) {
			}
		}
		return strReturn;
	}
	//------------------------------------------------------------------------------------------------------------
}
