/**
*@公司：          前景科技
*@系统名称：ftxt
*@文件名称：Sequence.java
*@功能描述:
*@创建人  ：zn
*@创建时间: 2012-3-14 上午09:40:21
*@完成时间：2012-3-14 上午09:40:21
*@修该人：
*@修改内容：
*@修改日期：
*/
package com.changing.framework.db;

import com.changing.framework.config.DbConfig;

/**
 * @author Administrator <p>	
 * 功能描述:
 * <p>	
 * 使用示例：<p>	
 */
public class Sequence {
	private static String dbType = DbConfig.getDb_database();
	/**
	 * 获得序列字符串，请先注册序列
	 * @param str MYSQL、SQLSERVER 为表名称 ORACLE 为序列名称 
	 * @return
	 */
	public static String getSequenceStr(String str) {
		if (dbType.equals(DbConfig.ORACLE)) {
			return str.toUpperCase()+".nextval";
		} else if (dbType.equals(DbConfig.MYSQL)) {
			return "nextval('"+str.toUpperCase()+"')";
		} else if (dbType.equals(DbConfig.SQLSERVER)) {
			return "nextval('"+str.toUpperCase()+"')";
		} else {
			return "nextval('"+str.toUpperCase()+"')";
		}
	}
	/**
	 * 获得当前序列生成字符串
	 * 主要用于在插入一条记录时 多个字段存在相同的序列值
	 * 如oracle 执行nextval之后调用currval,则返回上次的序列值
	 * @return
	 */
	public static String getCurrentSequenceStr(String str) {
		if (dbType.equals(DbConfig.ORACLE)) {
			return str.toUpperCase()+".currval";
		} else if (dbType.equals(DbConfig.MYSQL)) {
			return "currval('"+str.toUpperCase()+"')";
		} else if (dbType.equals(DbConfig.SQLSERVER)) {
			return "currval('"+str.toUpperCase()+"')";
		} else {
			return "currval('"+str.toUpperCase()+"')";
		}
		
		
	}
	/**
	 * 获取序列的值
	 * @param str
	 * @return
	 */
	public static String getSequenceValue(String str) {
		if (dbType.equals(DbConfig.ORACLE)) {
			QueryOneBean queryOneBean= new QueryOneBean();
			queryOneBean.executeQuery("SELECT "+str.toUpperCase()+".NEXTVAL AS SEQ FROM DUAL ");
			return queryOneBean.getValue(1);
		} else if (dbType.equals(DbConfig.MYSQL)) {
			QueryOneBean queryOneBean= new QueryOneBean();
			queryOneBean.executeQuery("SELECT nextval('"+str.toUpperCase()+"')  ");
			return queryOneBean.getValue(1);
		} else if (dbType.equals(DbConfig.SQLSERVER)) {
			QueryOneBean queryOneBean= new QueryOneBean();
			queryOneBean.executeQuery("SELECT nextval('"+str.toUpperCase()+"')  ");
			return queryOneBean.getValue(1);
		} else {
			return "";
		}
	}
	
}
