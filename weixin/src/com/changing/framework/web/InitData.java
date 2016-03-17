package com.changing.framework.web;

import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changing.common.systemmanager.InvalidParamException;
import com.changing.common.systemmanager.ParentServlet;
import com.changing.framework.db.QueryOneBean;
import com.changing.framework.helper.LogHelper;
/**
 * 点餐初始化数据 如初始日期、当前日期、送餐点、餐别
 * @author Administrator
 *
 */
@WebServlet({"/phone/InitData"})
public class InitData extends ParentServlet implements Serializable {
	private static final long serialVersionUID = 1L;
	public void doSomething(HttpServletRequest request, HttpServletResponse response){
		try {
		
			String msg = "提示: 数据初始化成功";
			StringBuffer dataBuffer = new StringBuffer();
			dataBuffer.append("{");
			//1.日期时间数据
			QueryOneBean queryOneBean = new QueryOneBean();
			//当前日期  页面日期和星期提示信息
			queryOneBean.executeQuery(" SELECT TO_CHAR(SYSDATE,'YYYY-MM-DD HH24:MI:SS') AS SYSDT,"+
									  " TO_CHAR(SYSDATE,'YYYY-MM-DD') AS SYSDD,"+
									  " TO_CHAR(SYSDATE,'YYYY') AS SYSYEAR,"+
									  " TO_CHAR(SYSDATE,'YYYY-MM') AS SYSMONTH,"+
									  " TO_CHAR(SYSDATE-1,'YYYY-MM-DD') AS PREDATE,"+
									  " TO_CHAR(SYSDATE+1,'YYYY-MM-DD') AS NEXTDATE,"+
									  " TO_CHAR(SYSDATE+6,'YYYY-MM-DD') AS NEXTWEEKDATE,"+
								      " '今天是 '||TO_CHAR(SYSDATE,'YYYY-MM-DD')||' '||to_char(SYSDATE,'day') AS TOPMSG FROM DUAL");
			if (queryOneBean.getValue("SYSDT").equals("")){
				throw new Exception("数据初始化失败");
			}
			dataBuffer.append("\"gh\":\""+gh+"\",");
			dataBuffer.append("\"yhm\":\""+yhm+"\",");
			dataBuffer.append("\"xm\":\""+xm+"\",");
			dataBuffer.append("\"bmdm\":\""+bmdm+"\",");
			dataBuffer.append("\"bmmc\":\""+bmmc+"\",");
			dataBuffer.append("\"bzdm\":\""+bzdm+"\",");
			dataBuffer.append("\"bzmc\":\""+bzmc+"\",");
			dataBuffer.append("\"sysdatetime\":\""+queryOneBean.getValue("SYSDT")+"\",");
			dataBuffer.append("\"sysdate\":\""+queryOneBean.getValue("SYSDD")+"\",");
			dataBuffer.append("\"sysyear\":\""+queryOneBean.getValue("SYSYEAR")+"\",");
			dataBuffer.append("\"sysmonth\":\""+queryOneBean.getValue("SYSMONTH")+"\",");
			dataBuffer.append("\"predate\":\""+queryOneBean.getValue("PREDATE")+"\",");
			dataBuffer.append("\"nextdate\":\""+queryOneBean.getValue("NEXTDATE")+"\",");
			dataBuffer.append("\"nextweekdate\":\""+queryOneBean.getValue("NEXTWEEKDATE")+"\",");
			dataBuffer.append("\"topmsg\":\""+xm+" "+queryOneBean.getValue("TOPMSG")+"\",");
			dataBuffer.append("\"status\":200,");
			dataBuffer.append("\"msg\":\""+msg+"\"");
			dataBuffer.append("}");
			showSuccessJsonMsg(response,dataBuffer.toString());
			LogHelper.logDebug(InitData.class, "初始化数据  InitData 成功="+dataBuffer.toString());
		}catch (InvalidParamException  e) {
			showErrorJsonMsg(response,"{\"data\":{},\"status\":100,\"msg\":\"提交内容含有非法参数\"}");
			LogHelper.logError(InitData.class, "初始化数据 InitData 出错了:"+e.getMessage()); 
		} catch (Exception e) {
			showErrorJsonMsg(response,"{\"data\":{},\"status\":100,\"msg\":\""+e.getMessage()+"\"}");
			LogHelper.logError(InitData.class, "初始化数据 InitData 出错了:"+e.getMessage()); 
			
		}
	}
}
