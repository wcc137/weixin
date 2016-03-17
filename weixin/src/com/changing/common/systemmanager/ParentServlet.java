package com.changing.common.systemmanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import com.changing.framework.config.SysConfig;
import com.changing.framework.helper.LogHelper;
import com.changing.framework.web.LogInfo;
import com.changing.framework.web.XtYgdm;
public abstract class ParentServlet extends HttpServlet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected LogInfo logInfo = null;
	protected ParentApplication parentapplication = null;
	protected String gh = "";
	protected String yhm = "";
	protected String xm = "";
	protected String bmdm = "";
	protected String bmmc = "";
	protected String bzdm = "";
	protected String bzmc = "";
	
	//是否锁定
	protected String sfsd = "0";
	//是否外单位人员
	protected String rybs = "0";
	//子系统管理员标识
	protected String isZxtgly = "0";
	/**
	 * doPost方法，由系统调用
	 * @param request 请求类
	 * @param response 应答类
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		performTask(request, response);
	}

	/**
	 * doGet方法，由系统调用
	 * @param request 请求类
	 * @param response 应答类
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		performTask(request, response);
	}

	/**
	 * 执行用户请求操作
	 * @param request 请求类
	 * @param response 应答类
	 */
	public void performTask(HttpServletRequest request, HttpServletResponse response) {
		try {
			JspFactory _jspxFactory = null;
			PageContext pageContext = null;
			ServletContext application = null;
			_jspxFactory = JspFactory.getDefaultFactory();
			pageContext = _jspxFactory.getPageContext(this, request, response, null, false, 8192, true);
			application = pageContext.getServletContext();
			synchronized (application) {
				parentapplication = (ParentApplication) pageContext.getAttribute(
						"parentapplication", PageContext.APPLICATION_SCOPE);
				if (parentapplication == null) {
					parentapplication = new ParentApplication();
					pageContext.setAttribute("parentapplication", parentapplication, PageContext.APPLICATION_SCOPE);
				}
			}
		} catch (Exception e0) {
			e0.printStackTrace();
		}
		try {
			logInfo = (LogInfo) request.getSession().getAttribute(SysConfig.LOGON_INFO_NAME);
			if (logInfo == null) {
				//response.sendRedirect(SysConfig.getSERVER() + SysConfig.getPAGE_NOT_SESSION());
				String pid = request.getParameter("pid") == null ?"":request.getParameter("pid");
				String id = request.getParameter("id") == null ?"":request.getParameter("id");
				showErrorJsonMsg(response,"{\"data\":[],\"status\":101,\"pid\":\""+pid+"\",\"id\":\""+id+"\",\"msg\":\"用户认证失效\"}");
			} else {
				//变量赋值
				XtYgdm xtYgdm = logInfo.getXtYgdm();
				gh = xtYgdm.getYgdm();
				yhm = xtYgdm.getYhm();
				xm = xtYgdm.getXm();
				bmdm = xtYgdm.getBmdm();
				bmmc = xtYgdm.getBmmc();
				bzdm = xtYgdm.getBzdm();
				bzmc = xtYgdm.getBzmc();
				sfsd = xtYgdm.getSdbs();
				isZxtgly = xtYgdm.getZxtglybs();
				doSomething(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.logError(ParentServlet.class,e.getMessage());
			showErrorJsonMsg(response,"{\"data\":[],\"status\":100,\"msg\":\""+e.getMessage()+"\"}"); // 导向执行出错页面，向用户显示出错信息
		}
	}

	/**
	 * 处理方法，由具体Ctrl类实现此方法
	 * @param request 请求类
	 * @param response 应答类
	 */
	public abstract void doSomething(HttpServletRequest request, HttpServletResponse response) throws Exception;

	/**
	 * 取得页面表单单个元素数据
	 * @param request 请求类
	 * @param strName 表单元素名称
	 * @return 表单元素对应的值(已去掉空格)，如果为空，就返回空值
	 */
	protected String getParameter(HttpServletRequest request, String strName) {
		String strValue = request.getParameter(strName);
		if (strValue == null) {
			return null;
		} else {
			return strValue.trim();
		}
	}

	/**
	 * 取得页面表单单个元素数据
	 * @param request 请求类
	 * @param strName 表单元素名称
	 * @return 表单元素对应的值(已去掉空格)，如果值不存在，就返回空字符串
	 */
	protected String get(HttpServletRequest request,String strName) throws InvalidParamException {
		String strValue = request.getParameter(strName);
		if (strValue == null || strValue.equals("null")) {
			return "";
		} else {
			strValue = cleanXSS(strValue);
			strValue = cleanSQLInject(strValue);
		}
		return strValue;
	}

	/**
	 * 取得页面表单数组元素值
	 * @param request 请求类
	 * @param strName 表单元素名称
	 * @return 表单元素对应的值(已去掉空格)，如果某元素值为空，则返回值为null
	 */
	protected String[] getParamValues(HttpServletRequest request, String strName) {
		String[] strValues = request.getParameterValues(strName);
		if (strValues == null || strValues.length == 0) {
			return null;
		}
		for (int i = 0; i < strValues.length; i++) {
			if (strValues[i] == null) {
				strValues[i] = null;
			} else {
				strValues[i] = strValues[i].trim();
				if (strValues[i].equals("")) {
					strValues[i] = null;
				}
			}
		}
		return strValues;
	}
	/**
	 * 显示处理结果
	 * @param msg 显示内容
	 */
	protected void showSuccessJsonMsg(HttpServletResponse response, String msg)  {
		//System.out.println("获取点餐前基础信息...");
		//response.setContentType("application/json;charset=UTF-8");
		//response.setContentType("text/html;charset=UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		try{
			PrintWriter out = response.getWriter();
			out.write(msg);
		}catch(IOException ex){ex.printStackTrace();}
	}

	/**
	 * 显示错误信息界面
	 * @param pmHtmlError 要在页面显示的html文本错误信息
	 */
	protected void showErrorJsonMsg(HttpServletResponse response, String msg) {
		//System.out.println("获取点餐前基础信息...");
		//response.setContentType("application/json;charset=UTF-8");
		//response.setContentType("text/html;charset=UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		try{
			PrintWriter out = response.getWriter();
			out.write(msg);
		}catch(IOException ex){ex.printStackTrace();}
	}
	/**
	 * 根据Cookie名称取Cookie
	 * @param request 请求类
	 * @param str Cookie的名称
	 */
	protected Cookie getCookie(HttpServletRequest request, String str) {
		if (str == null)
			return null;
		Cookie[] ck = request.getCookies();
		int len = ck.length;
		for (int i = 0; i < len; i++) {
			if (ck[i].getName().equals(str))
				return ck[i];
		}
		return null;
	}

	/**
	 * 检查一个String对象是否为空或者为空串，为空，返回true
	 */
	protected boolean isEmpty(String str) {
		return (str == null) || (str.trim().length() == 0);
	}

	/**
	 * 检查一个String对象是否为空或者为空串
	 */
	public static boolean isNotEmpty(String str) {
		return (str != null) && (str.trim().length() > 0);
	}

	/**
	 * 写入错误日志
	 * @param str 要写入日志文件的语句
	 */
	public void logError(String str) {
		//LogTools.logError(str);
		LogHelper.logError(str);
	}

	/**
	 * 写入执行信息日志
	 * @param str 要写入日志文件的语句
	 */
	public void logMsg(String str) {
		//LogTools.logMsg(str);
		
	}
	//替换特殊字符
	public String cleanXSS(String src) throws InvalidParamException{  
        String temp =src;
        src = src.replaceAll("<", "<").replaceAll(">", ">");
        src = src.replaceAll("\\(", "(").replaceAll("\\)", ")");
        src = src.replaceAll("'", "'");
        Pattern pattern=Pattern.compile("(eval\\((.*)\\)|script)",Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(src);
        src = matcher.replaceAll("");
        pattern=Pattern.compile("[\\\"\\'][\\s]*javascript:(.*)[\\\"\\']",Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(src);
        src = matcher.replaceAll("\"\"");
        //增加脚本   
        /**
        src = src.replaceAll("script", "").replaceAll(";", "")
            .replaceAll("\"", "").replaceAll("@", "")
            .replaceAll("0x0d", "")
            .replaceAll("0x0a", "").replaceAll(",", "");
        **/
        src = src.replaceAll("script", "")
        		.replaceAll("@", "")
        		.replaceAll(";", "")
                .replaceAll("0x0d", "")
                .replaceAll("0x0a", "");
        
        if(!temp.equals(src)) {
            throw new InvalidParamException("提交参数存在xss攻击 "+"原始输入信息-->"+temp+" 处理后信息-->"+src);
        }
        return src;
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