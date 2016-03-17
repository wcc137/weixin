package com.changing.framework.web;

/**
 * 系统common_tree_json.jsp树生成类
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changing.common.systemmanager.ParentServlet;
import com.changing.framework.db.QueryBean;

public class CreateZtreeJson extends ParentServlet {
	private static final long serialVersionUID = 1L;

	public void doSomething(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.setCharacterEncoding("UTF-8");
		String rootBm = request.getParameter("rootBm") == null ? "" : request
				.getParameter("rootBm");
		String id = request.getParameter("id") == null ? rootBm : request
				.getParameter("id");
		String level = request.getParameter("level") == null ?"":request.getParameter("level");
		String isAsny = request.getParameter("isAsny") == null ? "0" : request
				.getParameter("isAsny");
		// 查找关键字
		String searchKey = request.getParameter("searchKey") == null ? ""
				: request.getParameter("searchKey").trim();
		// 是否带选择框
		String showcheck = request.getParameter("showcheck") == null ? ""
				: request.getParameter("showcheck").trim();
		// System.out.println("searchKey:"+searchKey);
		// 增加展开根节点查询条件 这样可以自由指定第一层节点展开的条件 根节点不计算在内
		String rootWhere = request.getParameter("rootWhere") == null ? ""
				: request.getParameter("rootWhere");
		// 增加展开子级展开节点条件
		String subWhere = request.getParameter("subWhere") == null ? ""
				: request.getParameter("subWhere");
		// 根排序字段
		String rootNodeOrderBy = request.getParameter("rootNodeOrderBy") == null ? ""
				: request.getParameter("rootNodeOrderBy");
		// 子节点排序字段
		String subNodeOrderBy = request.getParameter("subNodeOrderBy") == null ? ""
				: request.getParameter("subNodeOrderBy");
		String tableName = request.getParameter("tableName") == null ? ""
				: request.getParameter("tableName").trim();
		// 是否自动展开第二级节点 根节点不计算在内
		String isExpand = request.getParameter("isExpand") == null ? "0"
				: request.getParameter("isExpand").trim();
		// 返回表字段名称系列,用'@'隔开
		String returnColName = request.getParameter("returnColName") == null ? ""
				: request.getParameter("returnColName").trim();
		// 选择的字段值 必须为bm的值
		String returnParamValue = request.getParameter("returnParamValue") == null ? ""
				: request.getParameter("returnParamValue").trim();
		// 向HTML输出json对象
		//response.setContentType("application/json;charset=GBK");
		// 向HTML输出显示字符串
		response.setContentType("application/txt;charset=GBK");
		String responseStr = getTree(id, isAsny, showcheck, id, tableName,
				rootWhere, subWhere, isExpand, returnColName, returnParamValue,
				rootNodeOrderBy, subNodeOrderBy, searchKey, "1",rootBm).toString();
		response.getWriter().write(responseStr);
	}
	/**
	 * 
	 * @param id
	 * @param isAsny
	 * @param showcheck
	 * @param idTemp
	 * @param tableName
	 * @param rootWhere
	 * @param subWhere
	 * @param isExpand
	 * @param returnColName
	 * @param returnParamValue
	 * @param rootNodeOrderBy
	 * @param subNodeOrderBy
	 * @param searchKey
	 * @param lv
	 * @param chk
	 * @return
	 */
	private StringBuffer getTree(String id, String isAsny, String showcheck,
			String idTemp, String tableName, String rootWhere, String subWhere,
			String isExpand, String returnColName, String returnParamValue,
			String rootNodeOrderBy, String subNodeOrderBy, String searchKey,
			String lv,String rootBm) {
		StringBuffer treeBuffer = new StringBuffer();
			String[] returnColNames = new String[] {};
			//if (returnColName.indexOf("@") != -1) {
			returnColNames = returnColName.split("@");
			//}
			String strSql = "";
			strSql = " SELECT * FROM " + tableName +" WHERE (1=1) ";
			if (isAsny.equals("1")) {
				strSql = " SELECT A.*,"+
						 " (SELECT COUNT(1) FROM "+tableName+" B WHERE B.SJBM = A.BM) AS CC "+
						 " FROM " + tableName +" A WHERE (1=1) ";
			}
			if (!"".equals(id)) {
				if (isAsny.equals("1")) {
					strSql +=" AND A.SJBM = '"+id+"' ";
				} else {
					strSql +=" AND SJBM = '"+id+"' ";
				}
			}
			//除跟节点外 第一级节点查询条件
			if (lv.equals("1") && !"".equals(rootWhere)) {
				strSql +=  rootWhere;
			//除第一级、第二级节点 查询条件	
			} else if (!lv.equals("1") && !"".equals(subWhere)) {
				strSql += subWhere;
			}
			// 根节点排序
			if (lv.equals("1") && !"".equals(rootNodeOrderBy)) {
				strSql += " ORDER BY " + rootNodeOrderBy;
				// 叶子节点排序
			} else if (!lv.equals("1") && !"".equals(subNodeOrderBy)) {
				strSql += " ORDER BY " + subNodeOrderBy;
			}	
			QueryBean queryBean = new QueryBean();
			queryBean.executeQuery(strSql);
			//System.out.println(strSql);
			String bmString = "",mcString = "";
			// 读出来的值拼凑字符串
			StringBuffer valueString = new StringBuffer();
			if ("1".equals(isAsny)) {
				treeBuffer.append("[");
			}
			int rowsCount = queryBean.getRowsCount();
			for (int i = 1; i <= rowsCount; i++) {
				bmString =  queryBean.getValue("BM", i);
				// 替换特殊符号
				mcString = queryBean.getValue("MC", i).replace("\\", "\\\\")
						.replace("'", "%27").replace(" ", "");
				// { id:1, pId:0, name:"节点搜索演示 1",
				// t:"id=1",value:"关键字可以是level@abced", open:true},
				treeBuffer.append("{\"id\":\"" + bmString
						+ "\"");
				treeBuffer.append(",\"pId\":\"" + queryBean.getValue("SJBM", i)
						+ "\"");
				treeBuffer.append(",\"name\":\"" + mcString + "\"");
				if (isAsny.equals("1") && !"0".equals(queryBean.getValue("CC",i))) {
					treeBuffer.append(",\"isParent\":true");
				}
				//是否带多选框
				if ("1".equals(showcheck)) {
					if (returnParamValue.length() > 0) {
						if (i == 1) {
							returnParamValue="," + returnParamValue +",";
						}
						if (returnParamValue.indexOf(","+bmString+",") != -1) {
							treeBuffer.append(",\"checked\":true");
						}
					}
				}
				treeBuffer.append(",\"t\":\"id=" + bmString
						+ "\"");
				// 返回值字符串
				for (int j = 0; j < returnColNames.length; j++) {
					valueString.append(queryBean.getValue(returnColNames[j], i)
							.replace("\\", "\\\\").replace("'", "%27"));
					valueString.append("@");
				}
				if (valueString.length() > 0) {
					valueString.delete(valueString.length() - 1, valueString
							.length());
				}
				treeBuffer.append(",\"value\":\"" + valueString + "\"},");
				valueString.delete(0, valueString.length());

			}
			if (treeBuffer.length() > 1) {
				treeBuffer.delete(treeBuffer.length() - 1, treeBuffer.length());
			}
			if ("1".equals(isAsny)) {
				treeBuffer.append("]");
			}
			//System.out.println(treeBuffer);
			return treeBuffer;
	}
}