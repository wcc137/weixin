/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：CreateTable.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-9-13 下午03:12:33
 *@完成时间：2011-9-13 下午03:12:33
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.common.systemmanager;

import com.changing.framework.db.BatchBean;
import com.changing.framework.db.QueryBean;
import com.changing.framework.helper.LogHelper;

/**
 * @author Administrator <p> 功能描述: <p> 使用示例：<p>
 */
public class CreateTable {
	QueryBean queryBean=new QueryBean();
	private boolean outDebug = false;//是否打印调试日志
	private boolean outError = false;//是否打印错误日志
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public boolean createNewTable(String tableName) {
		String tableSql = "CREATE TABLE `" + tableName + "` (";
		String keyStr="";
		StringBuffer keySql = new StringBuffer();
		keySql.append("PRIMARY KEY  (`SEQ_NUMBER`,");
		String sql = "SELECT * FROM  T_XT_ZDMXB WHERE  BDM = '" + tableName + "' AND SFZY='1' ORDER BY  PLSX";
		
		//queryBean.clear();
		System.out.println(sql+queryBean.getRowsCount() );
		queryBean.executeQuery(sql);
		if (queryBean.getRowsCount() > 0) {
			for (int i = 1; i <= queryBean.getRowsCount(); i++) {
				//`QSYM` varchar(600) default NULL,
				if (queryBean.getValue("ZDLX", i).equals("DATE") || queryBean.getValue("ZDLX", i).equals("DATETIME")) {//如果为日期或时间型
					tableSql =tableSql+ " `" + queryBean.getValue("ZDDM", i)+"` " +queryBean.getValue("ZDLX", i) ;
				} else if (queryBean.getValue("ZDLX", i).equals("FLOAT")) {//如果为FLOAT
					tableSql =tableSql+ " `" + queryBean.getValue("ZDDM", i) +"` " +queryBean.getValue("ZDLX", i) +"(" + queryBean.getValue("ZDCD", i) + ","
							+ queryBean.getValue("XSCD", i) + ")";
				} else {//其他类型
					tableSql =tableSql+ " `" + queryBean.getValue("ZDDM", i) +"` "+ queryBean.getValue("ZDLX", i) +"(" + queryBean.getValue("ZDCD", i) + ") ";
				}
				if (queryBean.getValue("YXK", i).equals("1")) {//如果为允许空
					tableSql =tableSql+ " default NULL ";
				} else {
					tableSql =tableSql+ " NOT NULL ";
					if(!queryBean.getValue("MRZ", i).equals("")){
						tableSql =tableSql+ " default  '"+queryBean.getValue("MRZ", i)+"' ";
					}
				}
				if( queryBean.getValue("ZDZZ", i).equals("1")){//自动增长，必须设置为索引
					tableSql =tableSql+ "  auto_increment ";
					keyStr=keyStr+"`" + queryBean.getValue("ZDDM", i) + "`,";
					
				}else if (queryBean.getValue("ZJBS", i).equals("1")) {//如果为主键
					keyStr=keyStr+"`" + queryBean.getValue("ZDDM", i) + "`,";
				}
				tableSql =tableSql+" COMMENT '"+queryBean.getValue("ZDMC", i)+"',";
			}	
			tableSql=tableSql+"`SEQ_NUMBER` int(11) NOT NULL auto_increment,`C_ID` varchar(10) default NULL COMMENT '创建人',`C_AGENT_ID` varchar(10) default NULL COMMENT '创建代理人',`C_DEPT` varchar(10) default NULL COMMENT '创建部门',"+
			  "`C_DATE` date default NULL  COMMENT '创建日期',`M_ID` varchar(10) default NULL  COMMENT '最近修改人', `M_AGENT_ID` varchar(10) default NULL  COMMENT '最近修改代理人',"+
			  "`M_DATE` date default NULL  COMMENT '最近修改日期',`M_CN` varchar(50) default NULL  COMMENT '最近修改机器',`MD_PROT` varchar(1) default NULL  COMMENT '并发保护',`D_DEL` varchar(1) default NULL  COMMENT '删除标志',";
			if(!keyStr.equals("")){
				keySql.append(keyStr);
				tableSql =tableSql+ keySql.toString().substring(0,keySql.toString().length() - 1) + ")";
			}else{
				tableSql=tableSql+ keySql.toString().substring(0,keySql.toString().length() - 1)+")";
			}
		}

		tableSql =tableSql+ ") ENGINE=InnoDB DEFAULT CHARSET=gb2312";
		BatchBean batchBean=new BatchBean();
		//batchBean.addSQL("drop table "+tableName);
		batchBean.addSQL(tableSql);
		//LogHelper.logDebug(outError,"drop table "+tableName);
		LogHelper.logDebug(outDebug,tableSql);
		System.out.println(tableSql);
		return batchBean.execute();
		
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