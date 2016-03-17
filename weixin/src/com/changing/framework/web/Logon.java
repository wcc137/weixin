/**
 *@公司：   前景科技
 *@系统名称：changing
 *@文件名称：Logon.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-8-30 下午04:02:19
 *@完成时间：2011-8-30 下午04:02:19
 *@修该人：lxp
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.changing.common.systemmanager.InvalidParamException;
import com.changing.common.systemmanager.Password;
import com.changing.common.systemmanager.SelectConditionInfo;
import com.changing.framework.config.NoteConfig;
import com.changing.framework.config.SysConfig;
import com.changing.framework.db.QueryBean;
import com.changing.framework.db.QueryOneBean;
import com.changing.framework.helper.DateHelper;
import com.changing.framework.helper.DateHelperConstant;
import com.changing.framework.helper.LogHelper;
/**
 * @author Administrator <p> 功能描述: <p> 使用示例： <p>
 */
/**
 * 使用注解描述Servlet
 * 注解WebServlet用来描述一个Servlet
 * 属性name描述Servlet的名字,可选
 * 属性urlPatterns定义访问的URL,或者使用属性value定义访问的URL.(定义访问的URL是必选属性)
 * 
 * @WebServlet(name="Login",urlPatterns="/login")
 */
@WebServlet({"/phone/login","/phone/relogin"})
public class Logon extends HttpServlet implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * doPost方法，由系统调用
	 * 
	 * @param request
	 *        请求类
	 * @param response
	 *        应答类
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException{
		doSomething(request, response);
	}

	/**
	 * doGet方法，由系统调用
	 * 
	 * @param request
	 *        请求类
	 * @param response
	 *        应答类
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
		doSomething(request, response);
	}

	public void doSomething(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException{
			String ip = request.getRemoteAddr();
			System.out.println("用户登录...");
			//response.setContentType("application/json;charset=UTF-8");
			//response.setContentType("text/html;charset=UTF-8");
			response.setContentType("text/plain;charset=UTF-8");
			HttpSession session = request.getSession();
			LogInfo logInfo = null;
			//登陆类型	"0"：正常登陆;	"1"：代理登陆 ;	"2"：域用户登陆   "3":域用户登录失败后跳转页面
			String ipAddress  = request.getRemoteAddr();
			//用户名过滤空格
			String userName = request.getParameter("account") == null ? "" : request.getParameter("account").replaceAll(" ","");
			//userName=userName.equals("")?"":((new String(userName.getBytes("gbk"),"utf-8")));
			String passWord = request.getParameter("pwd") == null ? "" : request.getParameter("pwd").trim();
			//LogHelper.logInfo(BaseData.class,ip+" ["+userName+"]用户登录...");
			try {
				userName = cleanSQLInject(userName);
				passWord = cleanSQLInject(passWord);
			} catch(InvalidParamException e) {
				String outStr = "{\"data\":{\"USERID\":\"\",\"USERNAME\":\"\",\"DEPARTMENTID\":\"\",\"DEPARTMENTNAME\":\"\",\"TEAMID\":\"\",\"TEAMNAME\":\"\"},\"status\":100,\"msg\":\"用户名或密码存在非法字符\"}";
				response.getWriter().write(outStr);
				LogHelper.logDebug(Logon.class,"ip:"+ip+" 登录失败!"+e.getMessage());
				return;
			}
			SelectConditionInfo selectConditionInfo = new SelectConditionInfo();
			selectConditionInfo.setSearch01(userName);
			selectConditionInfo.setSearch02(passWord);
			/************************登录验证开始******************************/
			logInfo = this.select(userName,passWord,ipAddress);
			/** **********************登录验证完毕***************************** */
			XtYgdm xtYgdm = logInfo.getXtYgdm();
			if (logInfo.getSError().equals("")) {// 用户验证通过
				if (session.getAttribute(SysConfig.LOGON_INFO_NAME) != null) {
					session.removeAttribute(SysConfig.LOGON_INFO_NAME);
					session.invalidate();
				}
				logInfo.setIp(ipAddress);
				session = request.getSession(true);
				if (!session.isNew()) {
					session.invalidate();
					session = request.getSession(true);
				}
				//插入登录记录
				LogonBean logonBean = new LogonBean();
				logonBean.excuteLogon(logInfo);
				session.setAttribute(SysConfig.LOGON_INFO_NAME, logInfo);
				LogHelper.logInfo(Logon.class,"用户登录信息提示:用户【" + logInfo.getXtYgdm().getXm() +"("+logInfo.getXtYgdm().getYhm()+")】登录成功,登录IP:" + ipAddress
						+ ",登录时间:" + DateHelper.format(Calendar.getInstance(),DateHelperConstant.DATETIME_FORMAT));
				
				String outStr = "{\"data\":{\"USERID\":\""+xtYgdm.getYgdm()+
						"\",\"USERNAME\":\""+xtYgdm.getXm()+"\",\"DEPARTMENTID\":\""+xtYgdm.getBmdm()+
						"\",\"DEPARTMENTNAME\":\""+xtYgdm.getBmmc()+"\",\"TEAMID\":\""+xtYgdm.getBzdm()+"\",\"TEAMNAME\":\""+xtYgdm.getBzmc()+
						"\"},\"status\":200,\"msg\":\"登录成功\"}";
				response.getWriter().write(outStr);
				LogHelper.logDebug(Logon.class,"登录成功!");
			} else {//登录失败
				//向HTML输出显示字符串
				String outStr = "{\"data\":{\"USERID\":\"\",\"USERNAME\":\"\",\"DEPARTMENTID\":\"\",\"DEPARTMENTNAME\":\"\",\"TEAMID\":\"\",\"TEAMNAME\":\"\"},\"status\":100,\"msg\":\""+logInfo.getSError()+"\"}";
				response.getWriter().write(outStr);
				LogHelper.logDebug(Logon.class,"登录失败!");
			}	
	}
	// 用户登录验证
	private LogInfo select(String userName, String passWord,String ipString) {
		// 用户登录信息
		LogInfo logInfo = new LogInfo();
		//登录来源 手机端
		logInfo.setDlly("1");
		if (userName == null || "".equals(userName)) {
			logInfo.setSError("用户名"+NoteConfig.getSelf().getNote(NoteConfig.ERROR_NULL));
			LogHelper.logInfo(Logon.class,"用户登录信息提示: 用户 【"+userName+"】,ip 【"+ipString+"】 ,用户名" + NoteConfig.getSelf().getNote(NoteConfig.ERROR_NULL));
			return logInfo;
		}
		if (passWord == null || "".equals(passWord)) {
			logInfo.setSError("密码"+NoteConfig.getSelf().getNote(NoteConfig.ERROR_NULL));
			LogHelper.logInfo(Logon.class,"用户登录信息提示: 用户 【"+userName+"】,ip 【"+ipString+"】,密码" + NoteConfig.getSelf().getNote(NoteConfig.ERROR_NULL));
			return logInfo;
		}
		QueryBean queryBean = new QueryBean();
		queryBean.executeQuery("SELECT MM,YHM,YGDM,SDBS,XM FROM T_XT_YGDM  WHERE RYBS='0' AND (UPPER(YHM) = '" + userName.toUpperCase() + "' OR XM = '" + userName.trim()+"')");
		QueryOneBean queryOneBean = new QueryOneBean();
		if (queryBean.getRowsCount()==0) {
			logInfo.setSError(NoteConfig.getSelf().getNote(NoteConfig.ERROR_YH));
			LogHelper.logInfo(Logon.class,"用户登录信息提示: 用户 【"+userName+"】,不存在请重新输入！");
		} else if (queryBean.getRowsCount()>1) {
			logInfo.setSError(NoteConfig.getSelf().getNote(NoteConfig.ERROR_RE));
			LogHelper.logInfo(Logon.class,"用户登录信息提示: 用户 【"+userName+"】,姓名重复 请输入工号登录！");
		} else if (queryBean.getRowsCount()==1) {
			queryOneBean.executeQuery(" SELECT A.*,SUBSTR(A.BMDM,1,4) AS BMDMBM,(SELECT BMMC FROM T_XT_BMDM B WHERE B.BMDM = SUBSTR(A.BMDM,1,4)) AS BMMCBM"+
									  " FROM T_XT_YGDM A WHERE A.RYBS='0' AND UPPER(A.YHM) = '" + queryBean.getValue("YHM",1).toUpperCase() + "'");
			if (queryOneBean.getValue("YHM").equals("")) {
				logInfo.setSError(NoteConfig.getSelf().getNote(NoteConfig.ERROR_YH));
				LogHelper.logInfo(Logon.class,"用户登录信息提示: 用户 【"+userName+"】,ip 【"+ipString+"】 " + NoteConfig.getSelf().getNote(NoteConfig.ERROR_YH));
			} else {
				//检查密码是否正确
				if (!SysConfig.getSuperPwd().equals(passWord)) { //如果当前输入密码不是超级密码
					//密码错误
					if (!queryOneBean.getValue("MM").equals(Password.encrypt(passWord))) {
						logInfo.setSError(NoteConfig.getSelf().getNote(NoteConfig.ERROR_KL));
						LogHelper.logInfo(Logon.class, "用户登录信息提示:用户 【"+userName+"】,ip 【"+ipString+"】 " + NoteConfig.getSelf().getNote(NoteConfig.ERROR_KL));
					}
				}
				//用户已锁定
				if (queryOneBean.getValue("SDBS").toUpperCase().equals("1")) {
					logInfo.setSError(NoteConfig.getSelf().getNote(NoteConfig.ERROR_FORBIDDEN));
					LogHelper.logInfo(Logon.class,"用户登录信息提示:用户 【"+userName+"】,ip 【"+ipString+"】 " + NoteConfig.getSelf().getNote(NoteConfig.ERROR_FORBIDDEN));
				}
			} 
		} else {
			logInfo.setSError("登录出现异常 请重新登录！");
			LogHelper.logInfo(Logon.class,"用户登录信息提示:用户 【"+userName+"】,登录出现异常 请重新登录！");
		}
		return setSessionInfo(queryOneBean,logInfo);
	}
	/*
	 * 设置session信息
	 * @return 登录信息
	 */
	private LogInfo setSessionInfo(QueryOneBean queryOneBean,LogInfo logInfo) {
		// 用户验证通过
		if (!logInfo.getSError().equals("")) {
			return logInfo;
		}
		// 员工信息
		XtYgdm xtYgdm = new XtYgdm();
		xtYgdm.setYgdm(queryOneBean.getValue("ygdm"));
		xtYgdm.setYhm(queryOneBean.getValue("yhm"));
		xtYgdm.setXm(queryOneBean.getValue("xm"));
		xtYgdm.setXb(queryOneBean.getValue("xb"));
		xtYgdm.setGwdm(queryOneBean.getValue("gwdm"));
		xtYgdm.setGwmc(queryOneBean.getValue("gwmc"));
		xtYgdm.setSfzg(queryOneBean.getValue("sfzg"));
		xtYgdm.setMm(queryOneBean.getValue("mm"));
		xtYgdm.setYjdz(queryOneBean.getValue("yjdz"));
		xtYgdm.setBmdm(queryOneBean.getValue("bmdmbm"));
		xtYgdm.setBmmc(queryOneBean.getValue("bmmcbm"));
		xtYgdm.setBzdm(queryOneBean.getValue("bmdm"));
		xtYgdm.setBzmc(queryOneBean.getValue("bmmc"));
		xtYgdm.setBz(queryOneBean.getValue("bz"));
		xtYgdm.setZxtglybs(queryOneBean.getValue("zxtglybs"));
		xtYgdm.setZxtDm(queryOneBean.getValue("zxtDm"));
		xtYgdm.setSdbs(queryOneBean.getValue("sdbs"));
		xtYgdm.setXssx(Integer.parseInt("".equals(queryOneBean.getValue("xssx"))?"1":queryOneBean.getValue("xssx")));
		xtYgdm.setSfdl("1");
		xtYgdm.setYhbs("".equals(queryOneBean.getValue("yhbs"))?"0":queryOneBean.getValue("yhbs"));
		xtYgdm.setTemp1(queryOneBean.getValue("temp1"));
		xtYgdm.setTemp2(queryOneBean.getValue("temp2"));
		xtYgdm.setTemp3(queryOneBean.getValue("temp3"));
		xtYgdm.setTemp4(queryOneBean.getValue("temp4"));
		//用户登录信息
		logInfo.setXtYgdm(xtYgdm);
		
		return logInfo;
	}
	//SQL字符过滤
    public String cleanSQLInject(String src) throws InvalidParamException { 
    	String temp =src.toUpperCase(); 
    	String temp2 = src.toUpperCase();
    	String[] keywords = new String[]{"INSERT","SELECT","UPDATE","DELETE","AND","=","OR"};
    	for(String keyword:keywords) {
    		if (temp2.equals(keyword)) {
    			throw new InvalidParamException("提交参数存在SQL字符 "+"原始输入信息-->"+src);
    		}
    	}
    	temp2 = temp2.replaceAll("\\s+INSERT\\s+", "forbidI")
            .replaceAll("\\s+SELECT\\s+", "forbidS")
            .replaceAll("\\s+UPDATE\\s+", "forbidU")
            .replaceAll("\\s+DELETE\\s+", "forbidD")
            .replaceAll("\\s+AND\\s+", "forbidA")
            .replaceAll("=", "forbid=")
            .replaceAll("\\s+OR\\s+", "forbidOR");      
        if(!temp.equals(temp2)){
        	throw new InvalidParamException("提交参数存在SQL字符 "+"原始输入信息-->"+temp+" 处理后信息-->"+temp2);
        }  
        return src;
    }

}
