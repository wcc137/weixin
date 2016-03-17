/**
 * 系统主页菜单子系统模块树生成类
 * 递归调用一次性加载系统下所有的模块
 * 性能、速度可行 
 */
package com.changing.framework.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changing.common.systemmanager.ParentServlet;
import com.changing.framework.config.SysConfig;
import com.changing.framework.db.QueryBean;
import com.changing.framework.helper.StringHelper;

public class CreateMkTree extends ParentServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void doSomething(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException{
		String id = request.getParameter("id");
		String isAsny = request.getParameter("isAsny") == null ?"0": request.getParameter("isAsny");
		//向HTML输出显示字符串
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(getTree(id,isAsny,""));
	}
	private String getTree(String id,String isAsny, String idTemp) {
		XtYgdm xtYgdm = logInfo.getXtYgdm();
		StringBuffer treeBuffer = new StringBuffer();
		if (id != null) {
			String strSql = "";
			if ("1".equals(xtYgdm.getZxtglybs())
					&& ("".equals(xtYgdm.getZxtDm()))) {//系统管理员,没有指定具体的子系统
				strSql = " SELECT A.MKDM,A.MKMC,A.SJMKDM,NVL(A.CXXH,0) AS CXXH,A.XSSX,A.QSYM,A.LV,"+
					 	 " (SELECT COUNT(1) FROM T_XT_MKDMS B WHERE  B.SJMKDM = A.MKDM AND B.SFSY='1' AND B.SFXS='1') AS CC," +
					 	 " LJDKFS,SFWBLJ FROM T_XT_MKDMS A WHERE A.SFSY='1' AND A.SFXS='1'"+
					 	 " AND SJMKDM='"+id+"'"+
					 	 " ORDER BY XSSX ";
			} else if ("1".equals(xtYgdm.getZxtglybs()) && !"".equals(xtYgdm.getZxtDm())) {//系统管理员 指定了子系统
				strSql = " SELECT A.MKDM,A.MKMC,A.SJMKDM,NVL(A.CXXH,0) AS CXXH,A.XSSX,A.QSYM,A.LV,"+
				 		 " (SELECT COUNT(1) FROM T_XT_MKDMS B WHERE  B.SJMKDM = A.MKDM AND B.SFSY='1' AND B.SFXS='1') AS CC," +
				 		 " LJDKFS,SFWBLJ FROM T_XT_MKDMS A"+
				 		 " WHERE A.SFSY='1' AND A.SFXS='1' AND A.FXTDM = '" + xtYgdm.getZxtDm() + "'"+
				 		 " AND SJMKDM='"+id+"'"+
				 		 " ORDER BY XSSX ";
			} else {
				strSql = "  SELECT * FROM ( "+
						 "  SELECT DISTINCT A.MKDM,A.MKMC,A.SJMKDM,NVL(A.CXXH,0) AS CXXH,A.XSSX,A.QSYM,A.LV,"+
					 	 "  (SELECT COUNT(1) FROM T_XT_MKDMS C WHERE  C.SJMKDM = A.MKDM AND C.SFSY='1' AND C.SFXS='1') AS CC,"+
					 	 "  LJDKFS,SFWBLJ FROM T_XT_MKDMS A,T_XT_MKZLSB B"+
						 "  WHERE A.SFSY = '1' AND A.SFXS = '1'"+
						 "  AND SJMKDM='"+id+"'"+
						 "  AND A.MKDM = B.FMKDM AND B.MKDM in "+
						 "  (select mkdm from t_xt_dxqx where dxfl = 'y' and dxdm = '"+xtYgdm.getYgdm()+"'"+
						 "  union select mkdm from t_xt_dxqx where dxfl = 'j' and dxdm in (select jsdm from t_xt_jsyg where ygdm =  '"+xtYgdm.getYgdm()+"')"+ 
						 " )) D ORDER BY XSSX";
			}
			String mkdmString="",mkmcString="",qsymString="",ljdkfString="",CC="",cxxhString="";
			QueryBean queryBean = new QueryBean();
			queryBean.executeQuery(strSql);
			treeBuffer.append("[");
			for (int i = 1 ; i <= queryBean.getRowsCount(); i++) {
				mkdmString = queryBean.getValue("MKDM",i).trim();
				mkmcString = queryBean.getValue("MKMC",i).trim();
				qsymString = queryBean.getValue("QSYM",i).trim();
				cxxhString =queryBean.getValue("CXXH",i).trim();
				CC = queryBean.getValue("CC", i);
				ljdkfString = "".equals(queryBean.getValue("LJDKFS",i))?"navTab":queryBean.getValue("LJDKFS",i).trim();
				if (!StringHelper.isBlank(qsymString)) {
					if ("0".equals(queryBean.getValue("SFWBLJ",i))) {
						if(qsymString.indexOf("?")>-1){
							if("0".equals(cxxhString)) {
								qsymString=SysConfig.getSERVER()+qsymString+"&mkdm="+mkdmString+"&mkmc="+mkmcString;
							} else {
								qsymString=SysConfig.getSERVER()+qsymString+"&id="+queryBean.getValue("CXXH",i)+"&mkdm="+mkdmString+"&mkmc="+mkmcString;
							}
						}else{
							if("0".equals(cxxhString)) {
								qsymString=SysConfig.getSERVER()+qsymString+"?mkdm="+mkdmString+"&mkmc="+mkmcString;
							} else {
								qsymString=SysConfig.getSERVER()+qsymString+"?id="+queryBean.getValue("CXXH",i)+"&mkdm="+mkdmString+"&mkmc="+mkmcString;
							}
						}
					}
				}
				qsymString = qsymString.replace("\\","\\\\");
				qsymString = qsymString.replace("'","%27");
				treeBuffer.append("{\"id\":\""+mkdmString+"\"");
				treeBuffer.append(",\"text\":\""+mkmcString+"\"");
				treeBuffer.append(",\"value\":\""+qsymString+"\"");
				treeBuffer.append(",\"ljdkfs\":\""+ljdkfString+"\"");
				treeBuffer.append(",\"sfwblj\":\""+queryBean.getValue("SFWBLJ",i).trim()+"\"");
				treeBuffer.append(",\"isexpand\":false");
				if (CC.equals("0")) {
					treeBuffer.append(",\"hasChildren\":false");
					treeBuffer.append(",\"ChildNodes\":null");
					treeBuffer.append(",\"complete\":true");
				} else {
					treeBuffer.append(",\"hasChildren\":true");
					if (isAsny.equals("1")) {
						treeBuffer.append(",\"ChildNodes\":null");
						treeBuffer.append(",\"complete\":false");
					} else {
						treeBuffer.append(",\"ChildNodes\":"+getTree(mkdmString,isAsny,mkdmString));
						treeBuffer.append(",\"complete\":true");
					}
				}
				treeBuffer.append("},");
			}
			if (treeBuffer.length() > 1) {
				treeBuffer.delete(treeBuffer.length()-1,treeBuffer.length());
			}
			treeBuffer.append("]");
		}
		//System.out.println(treeBuffer.toString());
		return treeBuffer.toString();
	}
}