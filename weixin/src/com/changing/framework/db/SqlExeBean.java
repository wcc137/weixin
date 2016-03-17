/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：SqlExeBean.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-8-10 下午04:03:35
 *@完成时间：2011-8-10 下午04:03:35
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.db;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.changing.framework.config.SysConfig;
import com.changing.framework.helper.LogHelper;
import com.changing.framework.helper.StringHelper;

/**
 * @author Administrator <p> 功能描述: <p> 使用示例：<p>
 */
public class SqlExeBean {
	private String strUpdateSQL = null; 
	private int intUpdateCount = -1;
	private boolean outDebug = SysConfig.isOutDebug();
	private boolean outError = SysConfig.isOutError();
	
	public SqlExeBean(boolean outDebug, boolean outError) {
		this.outDebug = outDebug;
		this.outError = outError;
	}
	public SqlExeBean()
    {
        strUpdateSQL = "";
    }
	/**
     * 执行数据操作
     * @return 更新记录条数
     */
    public int executeUpdate()
    {
    	Connection conn = null;
    	Statement stmt = null;
        if (strUpdateSQL.trim().equals("")) {
            return -1;
        }
        try {
            conn = DbConn.getConn();
            stmt = conn.createStatement();
            intUpdateCount = stmt.executeUpdate(strUpdateSQL);
        } catch (SQLException e) {
			LogHelper.logError(QueryBean.class,e,"SQL更新失败!SQL:"+this.getStrQuerySQL());
            return -1;
        }
        finally {
           DbConn.close(conn, stmt);
         }
        return intUpdateCount;
    }
    /**
     * 执行存储过程、DDL等没有返回值的SQL
     * @return
     */
    public boolean execute()
    {
    	Connection conn = null;
        CallableStatement cStmt = null;
        if (strUpdateSQL.trim().equals("")) {
            return false;
        }
        try {
            conn = DbConn.getConn();
            cStmt = conn.prepareCall(strUpdateSQL);
            cStmt.execute();
            LogHelper.logDebug(SqlExeBean.class,"SqlExeBean的execute方法 strSql:[" + this.getStrQuerySQL() + "]");
        } catch (SQLException e) {
            LogHelper.logError(SqlExeBean.class,e,"SqlExeBean的execute方法执行语句失败:" + e.toString() + "[" + this.getStrQuerySQL() + "]");
            return false;
        }
        finally {
           DbConn.close(conn, cStmt);
        }
        return true;
    }
    /**
     * 综合函数setQuerySQL(String strParamSQL)和executeQuery()的功能
     * @param strParamSQL
     * @return 更新记录条数
     */
    public int executeUpdate(String strParamSQL)
    {
        if (StringHelper.isEmpty(strParamSQL)) {
            return -1;
        } else {
            setStrQuerySQL(strParamSQL);
            return executeUpdate();
        }
    }

    /**
     * 综合函数execute(String strParamSQL)和execute()的功能
     * 执行存储过程、DDL等没有返回值的SQL
     * eg. execute("call test_proc()");
     *     execute("alter table add c1 char(1)");
     * @param strParamSQL
     * @return
     */
    public boolean execute(String strParamSQL)
    {
        if (StringHelper.isEmpty(strParamSQL)) {
            return false;
        } else {
            setStrQuerySQL(strParamSQL);
            return execute();
        }
    }

    /**
     * bean清空，以便下次调用
     */
    public void clear()
    {
        intUpdateCount = -1;
        setStrQuerySQL("");
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
		return strUpdateSQL;
	}
	public void setStrQuerySQL(String strUpdateSQL) {
		this.strUpdateSQL = strUpdateSQL;
	}
	public int getIntUpdateCount() {
		return intUpdateCount;
	}
	public void setIntUpdateCount(int intUpdateCount) {
		this.intUpdateCount = intUpdateCount;
	}

}
