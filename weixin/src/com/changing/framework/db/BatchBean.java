/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：BatchBean.java
 *@功能描述:执行批处理
 *@创建人  ：zn
 *@创建时间: 2011-8-10 下午03:46:55
 *@完成时间：2011-8-10 下午03:46:55
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import com.changing.framework.config.SysConfig;
import com.changing.framework.helper.LogHelper;

/**
 * @author Administrator
 *         <p>
 *         功能描述:
 *         <p>
 *         使用示例：
 *         <p>
 */
public class BatchBean {
	// 保存数据库批处理操作SQL语句的集合
	private ArrayList sqlList = new ArrayList();
	private boolean outDebug = SysConfig.isOutDebug();
	private boolean outError = SysConfig.isOutError();

	public BatchBean() {

	}

	public BatchBean(boolean outDebug, boolean outError) {
		this.outDebug = outDebug;
		this.outError = outError;
	}

	/**
	 * 添加批处理SQL语句
	 * 
	 * @param str
	 *            SQL语句
	 */
	public void addSQL(String str) {
		sqlList.add(str);
	}

	/**
	 * 添加批处理SQL语句
	 * 
	 * @param list
	 *            SQL语句集合
	 */
	public void addSQL(ArrayList list) {
		if (list != null)
			sqlList.addAll(list);
	}

	/**
	 * 提交数据库批处理操作，返回操作成功否
	 */
	public boolean execute() {
		String sql = "";
		boolean isTrue = true;
		Statement stmt = null;
		Connection conn = null;
		LogHelper.logDebug(BatchBean.class,"批处理SQL:	"+sqlList);
		try {
			conn = DbConn.getConn();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			Iterator it = sqlList.iterator();
			while (it.hasNext()) {
				sql = (String) it.next();
				if (sql != null && !(sql = sql.trim()).equals("")) {
					stmt.addBatch(sql);
				}
			}
			stmt.executeBatch(); // 执行数据库批处理操作
			conn.commit(); // 如果批处理SQL语句不为空，则提交操作
		} catch (Exception e) {
			isTrue = false;
			DbConn.rollback(conn); // 执行数据库回退操作
			LogHelper.logError(BatchBean.class,"批处理执行出错,出错SQL:"+sqlList+e.getMessage());
		} finally {
			DbConn.close(conn, stmt); // 关闭数据库资源
		}
		sqlList.clear();
		return isTrue;
	}

	/**
	 * 设置sqlList，并执行execute()
	 * 
	 * @param listSQL
	 *            SQL语句集合
	 * @return
	 */
	public boolean execute(ArrayList listSQL) {
		sqlList = listSQL;
		return execute();
	}
	/**
     * 设置vectorSQL，并执行executeQuery(),传Connection ,非自动提交
     * @param _conn
     * @param paramVectorSQL
     * @param writerLogInfo
     * @return
     */
    public void executeQuery(Vector paramVectorSQL, Connection _conn) throws Exception
    {
    	String sql = "";
		boolean isTrue = true;
		Statement stmt = null;
		Connection conn = null;
		LogHelper.logDebug(BatchBean.class,"批处理SQL:	"+sqlList);
		try {
			conn = _conn;
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for (int i = 0; i < paramVectorSQL.size(); i++) {
				sql = paramVectorSQL.get(i).toString();
				if (sql != null && !(sql = sql.trim()).equals("")) {
					stmt.addBatch(sql);
				}
			}
			stmt.executeBatch(); // 执行数据库批处理操作
			//conn.commit(); // 如果批处理SQL语句不为空，则提交操作
		} catch (Exception e) {
			isTrue = false;
			DbConn.rollback(conn); // 执行数据库回退操作
			LogHelper.logError(BatchBean.class,"批处理执行出错,出错SQL:"+sqlList+e.getMessage());
			throw e;
		} finally {
			// 关闭数据库资源
			if(stmt!=null){
				stmt.close();
			}
		}
    }
	/**
	 * 设置sqlList，并执行execute()
	 * 
	 * @param vec
	 *            SQL语句集合
	 * @return
	 */
	public boolean execute(Vector vec) {
		sqlList.addAll(vec);
		return execute();
	}

	/**
	 * bean清空，以便下次调用
	 */
	public void clear() {
		if (sqlList != null)
			sqlList.clear(); // 保存数据库批处理操作SQL语句的集合需要清空
	}

	/**
	 * 检查一个String对象是否为空或者为空串
	 * 
	 * @param str
	 *            String对象
	 */
	public boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
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
}
