package com.changing.common.systemmanager;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changing.framework.config.SysConfig;
import com.changing.framework.db.BatchBean;
import com.changing.framework.db.DbDateTime;
import com.changing.framework.db.QueryOneBean;
import com.changing.framework.helper.LogHelper;
import com.changing.framework.web.WriterLogInfo;
import com.changing.framework.web.XtYgdm;

public class User extends ParentServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doSomething(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
			String str = "";
			XtYgdm xtYgdm = logInfo.getXtYgdm();
			WriterLogInfo writerLogInfo = new WriterLogInfo();
			writerLogInfo.setCId(xtYgdm.getYgdm());
			writerLogInfo.setCzr(xtYgdm.getXm());
			writerLogInfo.setCzmk(request.getParameter("mkdm")); // 设置操作模块
			writerLogInfo.setSzjq(request.getRemoteAddr()); // 设置所在机器
			ArrayList listSQL = new ArrayList(); // SQL语句容器
			String action = request.getParameter("dataAction"); // 用户提交的操作命令
			if (action == null || (action = action.trim()).equals("")) {
				throw new Exception("参数设置不正确！");
			} else {
				if (action.equals("INSERT")) {
					QueryOneBean queryOneBean = new QueryOneBean();
					queryOneBean.executeQuery("select count(1) cc from t_xt_ygdm where (UPPER(yhm)='"+request.getParameter("YHM").toUpperCase()+"') or (UPPER(YGDM) = '"+request.getParameter("YGDM").toUpperCase()+"')");
					//验证用户名 工号是否重复 
					if (!"0".equals(queryOneBean.getValue("cc"))) {
						throw new Exception("用户名或工号重复,请重新输入");
					} else {
						str = getSql(request,"INSERT");
						listSQL.add(str);
						writerLogInfo.setCznr(str);
						writerLogInfo.setCzlx("INSERT"); 
						listSQL.add(writerLogInfo.getLogSQL());
					}
				} else if (action.equals("UPDATE")) {
					//验证用户名 工号是否重复 
					QueryOneBean queryOneBean = new QueryOneBean();
					queryOneBean.executeQuery("select count(1) cc from t_xt_ygdm where ((UPPER(yhm)='"+request.getParameter("YHM").toUpperCase()+"') or (UPPER(YGDM) = '"+request.getParameter("YGDM").toUpperCase()+"')) AND SEQ_NUMBER <> "+request.getParameter("SEQ_NUMBER"));
					if (!"0".equals(queryOneBean.getValue("cc"))) {
						throw new Exception("用户名或工号重复,请重新修改");
					} else {
						str = getSql(request,"UPDATE");
						listSQL.add(str);
						writerLogInfo.setCznr(str);
						writerLogInfo.setCzlx("UPDATE");
						listSQL.add(writerLogInfo.getLogSQL());
					}
				}  else if (action.equals("DELETE")) {
					str = getSql(request,"DELETE");
					listSQL.add(str);
					writerLogInfo.setCznr(str);
					writerLogInfo.setCzlx("DELETE");
					listSQL.add(writerLogInfo.getLogSQL());
					
				} else {
					throw new Exception("暂时不能处理针对数据库的操作"); // 其他操作类型，暂时不予处理
				}
			}
			BatchBean batchBean = new BatchBean(); // 准备执行批处理
			if (batchBean.execute(listSQL)) {
				str = request.getParameter("pageURL"); // 执行完成后要指向的页面链接
				if (str == null || (str = str.trim()).equals("")) {
					throw new Exception("参数设置不正确！");
				}
				showHtml(request, response, str, request.getParameter("alertMsg")); // 导向执行成功页面，向用户显示相关信息！
			} else {
				LogHelper.logError(User.class,"执行错误SQL:"+listSQL);
				
				throw new Exception("数据提交出错，请查看错误日志或与系统管理员联系！");
			}
			
	}
	/**
	 * 获取sql语句
	 * @param request
	 * @return
	 */
	private String getSql(HttpServletRequest request,String czBs) {
		String sqlString = "";
		String yhm = request.getParameter("YHM");
		String ygdm = request.getParameter("YGDM");
		String xm= request.getParameter("XM");
		String xssx = request.getParameter("XSSX");
		String yhbs = request.getParameter("YHBS");
		String xb = request.getParameter("XB");
		String mm = request.getParameter("MM");
		String sdbs = request.getParameter("SDBS");
		String zxtglybs = request.getParameter("ZXTGLYBS");
		String zxtdm = request.getParameter("ZXTDM");
		String bmdm = request.getParameter("BMDM");
		String bmmc = request.getParameter("BMMC");
		
		String gwdm = request.getParameter("GWDM");
		String gwmc = request.getParameter("GWMC");
		String sfzg = request.getParameter("SFZG");
		
		String yjdz = request.getParameter("YJDZ");
		String bz = request.getParameter("BZ");
		
		
		String seq_number = request.getParameter("SEQ_NUMBER");
		if ("INSERT".equals(czBs)) {
			sqlString = " INSERT INTO T_XT_YGDM(SEQ_NUMBER,YGDM,YHM,XM,XSSX,YHBS,XB,SDBS,ZXTGLYBS,MM,ZXTDM,BMDM,BMMC,GWDM,GWMC,SFZG,YJDZ,bz,C_ID,C_DATE,C_DEPT)"+
						" VALUES("+seq_number+",'"+ygdm+"','"+yhm+"','"+xm+"',"+xssx+",'"+yhbs+"','"+xb+"','"+sdbs+"','"+zxtglybs+"','"+
							Password.encrypt(mm)+"','"+zxtdm+"','"+bmdm+"','"+bmmc+"','"+gwdm+"','"+gwmc+"','"+sfzg+"','"+yjdz+"','"+
							bz+"','"+logInfo.getXtYgdm().getYgdm()+"',"+DbDateTime.getDbTimeStr()+",'"+logInfo.getXtYgdm().getBmdm()+"')";
			
		} else if ("UPDATE".equals(czBs)) {
			sqlString = "UPDATE T_XT_YGDM SET YGDM='"+ygdm+"',XM ='"+xm+"',"+
						"YHM='"+yhm+"',"+
						"XSSX ="+xssx+","+
						"SDBS='"+sdbs+"',"+
						"YHBS='"+yhbs+"',"+
						"XB ='"+xb+"',"+
						"ZXTGLYBS ='"+zxtglybs+"',"+
						"ZXTDM ='"+zxtdm+"',"+
						"BMDM = '"+bmdm+"',"+
						"BMMC = '"+bmmc+"',"+
						"GWDM = '"+gwdm+"',"+
						"GWMC = '"+gwmc+"',"+
						"SFZG = '"+sfzg+"',"+
						"YJDZ ='"+yjdz+"',"+
						"bz ='"+bz+"',"+
						"m_id ='"+logInfo.getXtYgdm().getYgdm()+"',"+
						"m_date ="+DbDateTime.getDbTimeStr()+" "+
						"where seq_number ="+seq_number;
		} else if ("DELETE".equals(czBs)) {
			sqlString = "DELETE FROM T_XT_YGDM where seq_number ="+seq_number;
		}
		return sqlString;
	}
	/**
	 * 显示处理结果界面。
	 */
	private void showHtml(HttpServletRequest request,
			HttpServletResponse response, String pageURL, String msg) throws Exception {
		
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
		
			response.setContentType("text/html;charset=GBK");

			java.io.PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<LINK href=\"" + SysConfig.getSERVER()
					+ "/css/css/main.css\" type=text/css rel=stylesheet>");
			out.println("</head>");
			out.println("<body>");
			out.println("<form name=\"frm\" method=\"post\" action=\""
					+ pageURL + "\">");
			out.println("<input type=\"hidden\" name=\"id\" value=\"" + id
					+ "\">");
			out.println("<input type=\"hidden\" name=\"where\" value=\""
					+ where + "\">");
			out.println("<input type=\"hidden\" name=\"type\" value=\"0\">");
			out.println("<input type=\"hidden\" name=\"AscOrDesc\" value=\""
					+ AscOrDesc + "\">");
			out.println("<input type=\"hidden\" name=\"SQLS\" value=\"" + SQLS
					+ "\">");
			out.println("<input type=\"hidden\" name=\"showtype\" value=\""
					+ showtype + "\">");
			out.println("<input type=\"hidden\" name=\"codp\" value=\"" + codp
					+ "\">");
			out.println("<input type=\"hidden\" name=\"pageNumber\" value=\""
					+ pageNumber + "\">");
			out.println("<input type=\"hidden\" name=\"totalNumber\" value=\""
					+ totalNumber + "\">");
			out.println("<input type=\"hidden\" name=\"power\" value=\""
					+ power + "\">");
			out.println("<input type=\"hidden\" name=\"sfzb\" value=\"" + sfzb
					+ "\">");
			out.println("<input type=\"hidden\" name=\"mkdm\" value=\"" + mkdm
					+ "\">");
			out.println("<input type=\"hidden\" name=\"mkmc\" value=\""
					+ mkmc + "\">");
			out.println("<input type=\"hidden\" name=\"temp1\" value=\""+temp1+"\">");
			out.println("<input type=\"hidden\" name=\"temp2\" value=\""+temp2+"\">");
			out.println("<input type=\"hidden\" name=\"temp3\" value=\""+temp3+"\">");
			out.println("<input type=\"hidden\" name=\"temp4\" value=\""+temp4+"\">");
			out.println("<input type=\"hidden\" name=\"temp5\" value=\""+temp5+"\">");
			out.println("<input type=\"hidden\" name=\"grqxzd\" value=\"" + grqxzd + "\">");
			out.println("<input type=\"hidden\" name=\"bmqxzd\" value=\"" + bmqxzd + "\">");
			
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			out.println("<script LANGUAGE=\"JavaScript\">");
			out.println("<!--");

			if (isNotEmpty(msg)) { // 如果提示信息不为空，那么弹出提示信息框
				out.println("alert(\"" + msg + "\");");
			}
			out.println("frm.submit();");
			out.println("//-->");
			out.println("</script>");
	}
}
