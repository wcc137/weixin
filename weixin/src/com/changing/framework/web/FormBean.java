/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：FormBean.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-8-11 上午11:18:57
 *@完成时间：2011-8-11 上午11:18:57
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changing.common.systemmanager.ParentServlet;
import com.changing.framework.config.SysConfig;
import com.changing.framework.db.BatchBean;
import com.changing.framework.helper.LogHelper;

/**
 * @author Administrator <p> 功能描述: <p> 使用示例： <p>
 */
public class FormBean extends ParentServlet {
	private static final long serialVersionUID = 1L;

	public void doSomething(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList listSQL=execute( request,  response);
		BatchBean batchBean = new BatchBean(); // 准备执行批处理
		if (batchBean.execute(listSQL)) {
			String str = request.getParameter("pageURL"); // 执行完成后要指向的页面链接
			if (str == null || (str = str.trim()).equals("")) {
				throw new Exception("pageURL参数设置不正确！");
			}
			showHtml(request, response, str, request.getParameter("alertMsg")); // 导向执行成功页面，向用户显示相关信息！
		} else {
			LogHelper.logError(FormBean.class,"执行错误SQL:" + listSQL);
			throw new Exception("数据提交出错，请查看错误日志或与系统管理员联系！");
		}
	}

	public ArrayList execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		XtYgdm xtYgdm = logInfo.getXtYgdm();
		WriterLogInfo writerLogInfo = new WriterLogInfo();
		writerLogInfo.setCId(xtYgdm.getYgdm());
		writerLogInfo.setCzr(xtYgdm.getXm());
		writerLogInfo.setCzmk(request.getParameter("mkdm")); // 设置操作模块
		writerLogInfo.setSzjq(request.getRemoteAddr()); // 设置所在机器
		ArrayList listSQL = new ArrayList(); // SQL语句容器
		String str = request.getParameter("firstSQL"); // 直接在页面指定的首先执行的SQL语句
		if (str != null && !(str = str.trim()).equals("")) {
			StringTokenizer st = new StringTokenizer(str, "$$");
			while (st.hasMoreTokens())
				listSQL.add(st.nextToken()); // 将首先执行的SQL语句存入集合
		}

		HashMap tableTrace = new HashMap(); // 需要进行痕迹保留的表
		str = request.getParameter("tableTrace"); // 需要痕迹保留的表
		if (str != null && !(str = str.trim()).equals("")) {
			int index = 0;
			String table = "";
			StringTokenizer st = new StringTokenizer(str, ",");
			while (st.hasMoreTokens()) {
				str = st.nextToken();
				index = str.indexOf('$');
				if (index == -1) { // 如果没有定义要进行数据库数据对照的字段
					tableTrace.put(str.toUpperCase(), null); // 将需要痕迹保留的表存入集合
				} else {
					table = str.substring(0, index); // 获得表名
					str = str.substring(index + 1); // 获得用户定义的要进行数据库数据对照的字段
					ArrayList tmpList = new ArrayList(); // 用来保存字段信息
					StringTokenizer st2 = new StringTokenizer(str, "$");
					while (st2.hasMoreTokens()) {
						tmpList.add(st2.nextToken());
					}
					tableTrace.put(table.toUpperCase(), tmpList); // 将需要痕迹保留的表存入集合
				}
			}
		}
		str = request.getParameter("dataAction"); // 用户提交的操作命令，如："table1$update,table2$update"
		if (str == null || (str = str.trim()).equals("")) {
			throw new Exception("dataAction参数设置不正确！");
		} else {
			int index = 0;
			String table = "";
			String action = "";
			HashMap mapValue = new HashMap(); // 需要进行痕迹保留的表
			StringTokenizer st = new StringTokenizer(str, ",");
			while (st.hasMoreTokens()) {
				str = st.nextToken();
				index = str.indexOf('$');
				if (index != -1) {
					table = str.substring(0, index); // 获得表名
					action = str.substring(index + 1).toUpperCase(); // 获得操作类型insert、update、delete
					if (action.equals(SysConfig.DB_CONTROL_INSERT)) {
						InsertBean sqlInsert = new InsertBean(getTable(table));
						sqlInsert.add(request, table, mapValue);
						sqlInsert.add(logInfo); // 写入用户操作数据记录的相关信息
						try {
							str = sqlInsert.getSQL();
						} catch (Exception e2) {
							str = "构造表" + table + "的SQL语句失败！具体原因为" + e2.getMessage();
							throw new Exception(str); // 构造表的SQL语句失败
						}
						listSQL.add(str);
						writerLogInfo.setCznr(str);
						writerLogInfo.setCzlx(SysConfig.DB_CONTROL_INSERT);
						listSQL.add(writerLogInfo.getLogSQL());
					} else if (action.equals(SysConfig.DB_CONTROL_UPDATE)) {
						UpdateBean sqlUpdate = new UpdateBean(getTable(table));
						sqlUpdate.add(request, table, mapValue);
						sqlUpdate.add(logInfo); // 写入用户操作数据记录的相关信息
						try {
							str = sqlUpdate.getSQL();
							listSQL.add(str);
							writerLogInfo.setCznr(str);
							writerLogInfo.setCzlx(SysConfig.DB_CONTROL_UPDATE);
							listSQL.add(writerLogInfo.getLogSQL());
						} catch (Exception e2) {
							str = "构造表" + table + "的SQL语句失败！具体原因为" + e2.getMessage();
							throw new Exception(str); // 构造表的SQL语句失败
						}
					} else if (action.equals(SysConfig.DB_CONTROL_DELETE)) {
						DeleteBean sqlDelete = new DeleteBean(getTable(table));
						sqlDelete.add(request, table);
						try {
							str = sqlDelete.getSQL();
							listSQL.add(str);
							writerLogInfo.setCznr(str);
							writerLogInfo.setCzlx(SysConfig.DB_CONTROL_DELETE);
							listSQL.add(writerLogInfo.getLogSQL());
						} catch (Exception e2) {
							str = "构造表" + table + "的SQL语句失败！具体原因为" + e2.getMessage();
							e2.printStackTrace();
							throw new Exception(str); // 构造表的SQL语句失败
						}
					} else {
						throw new Exception("FormBean暂时不能处理针对数据库的" + action + "操作"); // 其他操作类型，暂时不予处理
					}
				}
			}
		}
		str = request.getParameter("lastSQL"); // 直接在页面指定的最后执行的SQL语句
		if (str != null && !(str = str.trim()).equals("")) {
			StringTokenizer st = new StringTokenizer(str, "$$");
			while (st.hasMoreTokens())
				listSQL.add(st.nextToken()); // 将首先执行的SQL语句存入集合
		}
		return listSQL;
	}

	/**
	 * 根据表单域的表名获取数据库的表名
	 */
	private String getTable(String str) {
		int index = str.indexOf("__");
		if (index == -1) {
			return str;
		} else {
			return str.substring(0, index); // 取正式表名，如t_web__1，取t_web
		}
	}

	/**
	 * 显示处理结果界面。
	 */
	public void showHtml(HttpServletRequest request, HttpServletResponse response, String pageURL, String msg) throws Exception{
		try {
			String strHostKey = ""; // 其他参数，用hostkey接收
			if (request.getParameter("txtHostKey") != null) {
				strHostKey = request.getParameter("txtHostKey");
			}

			String mkdm = "";
			if (request.getParameter("mkdm") != null) {
				mkdm = request.getParameter("mkdm");
			}
			String mkmc = "";
			if (request.getParameter("mkmc") != null) {
				mkmc = request.getParameter("mkmc");
			}
			String temp1 = "";
			if (request.getParameter("temp1") != null) {
				temp1 = request.getParameter("temp1");
			}
			String temp2 = "";
			if (request.getParameter("temp2") != null) {
				temp2 = request.getParameter("temp2");
			}
			String temp3 = "";
			if (request.getParameter("temp3") != null) {
				temp3 = request.getParameter("temp3");
			}
			String temp4 = "";
			if (request.getParameter("temp4") != null) {
				temp4 = request.getParameter("temp4");
			}
			String temp5 = "";
			if (request.getParameter("temp5") != null) {
				temp5 = request.getParameter("temp5");
			}
			String grqxzd = "";
			if (request.getParameter("grqxzd") != null) {
				grqxzd = request.getParameter("grqxzd");
			}
			String bmqxzd = "";
			if (request.getParameter("bmqxzd") != null) {
				bmqxzd = request.getParameter("bmqxzd");
			}
			String id = "";
			if (request.getParameter("id") != null) {
				id = request.getParameter("id");
			}
			String AscOrDesc = "";
			if (request.getParameter("AscOrDesc") != null) {
				AscOrDesc = request.getParameter("AscOrDesc");
			}
			String SQLS = "";
			if (request.getParameter("SQLS") != null) {
				SQLS = request.getParameter("SQLS");
			}
			String showtype = "";
			if (request.getParameter("showtype") != null) {
				showtype = request.getParameter("showtype");
			}
			String codp = "";
			if (request.getParameter("codp") != null) {
				codp = request.getParameter("codp");
			}
			String pageNumber = "";
			if (request.getParameter("pageNumber") != null) {
				pageNumber = request.getParameter("pageNumber");
			}
			String totalNumber = "";
			if (request.getParameter("totalNumber") != null) {
				totalNumber = request.getParameter("totalNumber");
			}
			String where = "";
			if (request.getParameter("where") != null) {
				where = request.getParameter("where");
			}
			String power = "";
			if (request.getParameter("power") != null) {
				power = request.getParameter("power");
			}
			String sfzb = "";
			if (request.getParameter("sfzb") != null) {
				sfzb = request.getParameter("sfzb");
			}
			//---2012-12-7添加  用于左边带树的LIST页面
			String bm=getParameter(request, "bm");
			if (request.getParameter("bm") != null) {
				bm = request.getParameter("bm");
			}
			String isleaf=getParameter(request, "isleaf");
			if (request.getParameter("isleaf") != null) {
				isleaf = request.getParameter("isleaf");
			}
			String mc=getParameter(request, "mc");
			if (request.getParameter("mc") != null) {
				mc = request.getParameter("mc");
			}
			String jbmc=getParameter(request, "jbmc");
			if (request.getParameter("jbmc") != null) {
				jbmc = request.getParameter("jbmc");
			}
			String listTitle=getParameter(request, "listTitle");
			if (request.getParameter("listTitle") != null) {
				listTitle = request.getParameter("listTitle");
			}
			String tableName=getParameter(request, "tableName");
			if (request.getParameter("tableName") != null) {
				tableName = request.getParameter("tableName");
			}
			String isShowAll=getParameter(request, "isShowAll");
			if (request.getParameter("isShowAll") != null) {
				isShowAll = request.getParameter("isShowAll");
			}
			String showSql=getParameter(request, "showSql");
			if (request.getParameter("showSql") != null) {
				showSql = request.getParameter("showSql");
			}
			String listNodeWhere=getParameter(request, "listNodeWhere");
			if (request.getParameter("listNodeWhere") != null) {
				listNodeWhere = request.getParameter("listNodeWhere");
			}
			String columnParam=getParameter(request, "columnParam");
			if (request.getParameter("columnParam") != null) {
				columnParam = request.getParameter("columnParam");
			}
			String showAllBs=getParameter(request, "showAllBs");
			if (request.getParameter("showAllBs") != null) {
				showAllBs = request.getParameter("showAllBs");
			}
			String rootBm=getParameter(request, "rootBm");
			if (request.getParameter("rootBm") != null) {
				rootBm = request.getParameter("rootBm");
			}
			String rootMc=getParameter(request, "rootMc");
			if (request.getParameter("rootMc") != null) {
				rootMc = request.getParameter("rootMc");
			}
			//--end---
			response.setContentType("text/html;charset=utf-8");

			java.io.PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			out.println("<head>");
			out.println("<LINK href=\"" + SysConfig.getSERVER() + "/css/css/main.css\" type=text/css rel=stylesheet>");
			out.println("</head>");
			out.println("<body>");
			out.println("<form name=\"frm\" method=\"post\" action=\"" + pageURL + "\">");
			out.println("<input type=\"hidden\" id=\"id\" 		 	 name=\"id\" 				value=\"" + id + "\">");
			out.println("<input type=\"hidden\" id=\"where\" 		 name=\"where\" 			value=\"" + where + "\">");
			out.println("<input type=\"hidden\" id=\"type\" 		 name=\"type\" 				value=\"0\">");
			out.println("<input type=\"hidden\" id=\"mkdm\" 		 name=\"mkdm\" 				value=\"" + mkdm + "\">");
			out.println("<input type=\"hidden\" id=\"mkmc\" 		 name=\"mkmc\" 				value=\"" + mkmc + "\">");
			out.println("<input type=\"hidden\" id=\"temp1\" 		 name=\"temp1\" 			value=\"" + temp1 + "\">");
			out.println("<input type=\"hidden\" id=\"temp2\" 		 name=\"temp2\" 			value=\"" + temp2 + "\">");
			out.println("<input type=\"hidden\" id=\"temp3\" 		 name=\"temp3\" 			value=\"" + temp3 + "\">");
			out.println("<input type=\"hidden\" id=\"temp4\" 		 name=\"temp4\" 			value=\"" + temp4 + "\">");
			out.println("<input type=\"hidden\" id=\"temp5\" 		 name=\"temp5\" 			value=\"" + temp5 + "\">");
			out.println("<input type=\"hidden\" id=\"grqxzd\" 	 	 name=\"grqxzd\" 			value=\"" + grqxzd + "\">");
			out.println("<input type=\"hidden\" id=\"bmqxzd\" 	 	 name=\"bmqxzd\" 			value=\"" + bmqxzd + "\">");
			out.println("<input type=\"hidden\" id=\"hostkey\" 	 	 name=\"hostkey\" 			value=\"" + strHostKey + "\">");
			out.println("<input type=\"hidden\" id=\"AscOrDesc\" 	 name=\"AscOrDesc\" 		value=\"" + AscOrDesc + "\">");
			out.println("<input type=\"hidden\" id=\"SQLS\" 		 name=\"SQLS\" 				value=\"" + SQLS + "\">");
			out.println("<input type=\"hidden\" id=\"showtype\" 	 name=\"showtype\" 			value=\"" + showtype + "\">");
			out.println("<input type=\"hidden\" id=\"codp\" 		 name=\"codp\" 				value=\"" + codp + "\">");
			out.println("<input type=\"hidden\" id=\"pageNumber\"    name=\"pageNumber\" 		value=\"" + pageNumber + "\">");
			out.println("<input type=\"hidden\" id=\"totalNumber\"   name=\"totalNumber\" 		value=\"" + totalNumber + "\">");
			out.println("<input type=\"hidden\" id=\"power\" 		 name=\"power\" 			value=\"" + power + "\">");
			out.println("<input type=\"hidden\" id=\"sfzb\" 		 name=\"sfzb\" 				value=\"" + sfzb + "\">");
			//---2012-12-7添加                  id
			out.println("<input type=\"hidden\" id=\"bm\" 		 	 name=\"bm\" 				value=\""+bm+"\"/>");
			out.println("<input type=\"hidden\" id=\"isleaf\" 	 	 name=\"isleaf\" 			value=\""+isleaf+"\"/>");
			out.println("<input type=\"hidden\" id=\"mc\" 		 	 name=\"mc\" 				value=\""+mc+"\"/>");
			out.println("<input type=\"hidden\" id=\"jbmc\" 		 name=\"jbmc\" 				value=\""+jbmc+"\"/>");
			out.println("<input type=\"hidden\" id=\"listTitle\" 	 name=\"listTitle\" 		value=\""+listTitle+"\"/>");
			out.println("<input type=\"hidden\" id=\"tableName\" 	 name=\"tableName\" 		value=\""+tableName+"\"/>");
			out.println("<input type=\"hidden\" id=\"isShowAll\" 	 name=\"isShowAll\" 		value=\""+isShowAll+"\"/>");
			out.println("<input type=\"hidden\" id=\"showSql\" 	 	 name=\"showSql\" 			value=\""+showSql+"\"/>");
			out.println("<input type=\"hidden\" id=\"listNodeWhere\" name=\"listNodeWhere\"	    value=\""+listNodeWhere+"\"/>");
			out.println("<input type=\"hidden\" id=\"columnParam\" 	 name=\"columnParam\" 		value=\""+columnParam+"\"/>");
			out.println("<input type=\"hidden\" id=\"showAllBs\" 	 name=\"showAllBs\" 		value=\""+showAllBs+"\"/>");
			out.println("<input type=\"hidden\" id=\"rootBm\" 		 name=\"rootBm\" 			value=\""+rootBm+"\"/>");
			out.println("<input type=\"hidden\" id=\"rootMc\" 		 name=\"rootMc\" 			value=\""+rootMc+"\"/>");
			//---end---

			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			out.println("<script type=\"text/javascript\">");
			out.println("<!--");

			if (isNotEmpty(msg)) { // 如果提示信息不为空，那么弹出提示信息框
				out.println("alert(\"" + msg + "\");");
			}
			out.println("frm.submit();");
			out.println("//-->");
			out.println("</script>");
		} catch (Exception e) {
			LogHelper.logError(FormBean.class,e);
			throw new Exception("showHtml出错");
		}
	}
}