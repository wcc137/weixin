package com.changing.framework.web;

import com.changing.framework.db.DbDateTime;
import com.changing.framework.db.QueryOneBean;
import com.changing.framework.db.Sequence;
import com.changing.framework.db.SqlExeBean;
import com.changing.framework.helper.LogHelper;

public class LogonBean {

	public LogonBean() {

	}

	// 插入登录记录
	public void excuteLogon(LogInfo logInfo) {
		SqlExeBean sqlExeBean = new SqlExeBean();
		// 登录序号
		String dlXh = Sequence.getSequenceValue("S_XT_DL");
		logInfo.setDlXh(dlXh);
		if (sqlExeBean
				.executeUpdate(" insert into T_XT_DL(xh,userId,INTIME,OUTTIME,IP,DLLY,c_id,c_dept,c_date,seq_number) "
						+ " values ("
						+ dlXh
						+ ",'"
						+ logInfo.getXtYgdm().getYgdm()
						+ "'"
						+ ","
						+ DbDateTime.getDbTimeStr()
						+ ","
						+ DbDateTime.getDbTimeStr()
						+ ",'"
						+ logInfo.getIp()
						+ "','"
						+ logInfo.getDlly()
						+ "','"
						+ logInfo.getXtYgdm().getYgdm()
						+ "','"
						+ logInfo.getXtYgdm().getBmdm()
						+ "',"
						+ DbDateTime.getDbTimeStr() + "," + dlXh + ")") == -1) {
			LogHelper.logError(Logon.class, "用户登录日志记录失败,请查看日志");
		}
	}

	// 更新登录记录
	public boolean excuteUpdateLogon(LogInfo logInfo) {
		String dlxh = logInfo.getDlXh();
		SqlExeBean sqlExeBean = new SqlExeBean();
		QueryOneBean queryOneBean = new QueryOneBean();
		queryOneBean.executeQuery("SELECT * FROM T_XT_DL WHERE XH = " + dlxh);
		if (!"".equals(queryOneBean.getValue("USERID"))) {
			if (sqlExeBean.executeUpdate("UPDATE T_XT_DL SET OUTTIME ="
					+ DbDateTime.getDbTimeStr() + " where xh = " + dlxh) == -1) {
				LogHelper.logError(Logon.class, "用户登录日志更新失败,请查看日志");
				return false;
			} else {
				LogHelper.logDebug(LogonBean.class, "更新用户"
						+ logInfo.getXtYgdm().getYhm() + "登录日志退出时间,登录序号:"
						+ logInfo.getDlXh());
				return true;
			}
		} else {
			LogHelper
					.logError(Logon.class, "用户登录日志更新失败,找不到对应的记录,登录序号为:" + dlxh);
			return false;
		}
	}
}
