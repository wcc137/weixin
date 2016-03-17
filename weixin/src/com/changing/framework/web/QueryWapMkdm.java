package com.changing.framework.web;

import java.io.Serializable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changing.common.systemmanager.InvalidParamException;
import com.changing.common.systemmanager.ParentServlet;
import com.changing.framework.db.QueryBean;
import com.changing.framework.helper.LogHelper;
/**
 * 查询登录用户所拥有的模块
 * @author Administrator
 *
 */
@WebServlet({"/phone/queryWapMk"})
public class QueryWapMkdm extends ParentServlet implements Serializable {

	private static final long serialVersionUID = 1L;
	@Override
	public void doSomething(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String ip = request.getRemoteAddr();
		LogHelper.logInfo(QueryWapMkdm.class, ip+" 获取授权模块...");
		String strSql = "",zxtdm = "";
		try {
			zxtdm = get(request,"dm");
			if (zxtdm.equals("")) {
				throw new Exception(" 参数 dm 子系统代码 为空");
			}
			QueryBean queryBean = new QueryBean();
			if ("1".equals(isZxtgly)) {//子系统管理员
				strSql = " SELECT MKDM,MKMC,MKBM,LV,QSYM,WXQSYM,FN_GET_WAP_MKDMLVSTR(MKDM) AS LVSTR FROM T_XT_WAP_MKDM "+
				 		 " WHERE SFXS='1' AND FXTDM = '" + zxtdm + "' ORDER BY LVSTR";
			} else {
				strSql =  " SELECT * FROM ( "+
						  " SELECT DISTINCT A.MKDM,A.MKMC,A.MKBM,A.QSYM,WXQSYM,A.LV,FN_GET_WAP_MKDMLVSTR(A.MKDM) AS LVSTR FROM T_XT_WAP_MKDM A,T_XT_WAP_MKZLSB B"+
						  " WHERE A.SFXS='1' AND A.FXTDM='"+zxtdm+"'"+
						  " AND A.MKDM = B.FMKDM AND B.XTMKDM IN "+
						  " (SELECT MKDM FROM T_XT_DXQX WHERE DXFL = 'y' AND DXDM = '"+gh+"'"+
						  " UNION "+
						  " SELECT MKDM FROM T_XT_DXQX WHERE DXFL = 'j' AND DXDM IN (SELECT JSDM FROM T_XT_JSYG WHERE YGDM =  '"+gh+"'))) D"+ 
						  " ORDER BY LVSTR";
			}
			queryBean.executeQuery(strSql);
			int count = queryBean.getRowsCount();
			if (count == 0) {
				throw new Exception(" 没有分配权限  ");
			}
					
			StringBuffer dataBuffer = new StringBuffer();
			dataBuffer.append("{\"data\":{");
		
			for (int i = 1; i <= count;i++) {
				String lv = queryBean.getValue("LV",i);
				String qsym = queryBean.getValue("QSYM",i);
				String wxqsym = queryBean.getValue("WXQSYM",i);
				if (qsym.equals("")) {
					qsym = "#";
				}
				//下一条记录
				String nextLv = "";
				if ( i < count) {
					nextLv = queryBean.getValue("LV",i+1);
				}
				if (i == 1) {//系统根节点 lv = 1
					dataBuffer.append("\"rootbm\":\""+queryBean.getValue("MKDM",i)+"\",");
					dataBuffer.append("\"rootmc\":\""+queryBean.getValue("MKMC",i)+"\",");
					dataBuffer.append("\"rooturi\":\""+qsym+"\",");
					dataBuffer.append("\"rootwxuri\":\""+wxqsym+"\",");
					dataBuffer.append("\"children\":[");
					if ( i == count) {
						dataBuffer.append("]");
					}
				} else if (lv.equals("2")){//一级菜单
					dataBuffer.append("{");
					dataBuffer.append("\"dm\":\""+queryBean.getValue("MKDM",i)+"\",");
					dataBuffer.append("\"mc\":\""+queryBean.getValue("MKMC",i)+"\",");
					dataBuffer.append("\"bm\":\""+queryBean.getValue("MKBM",i)+"\",");
					dataBuffer.append("\"url\":\""+qsym+"\",");
					dataBuffer.append("\"wxurl\":\""+wxqsym+"\",");
					if (nextLv.equals("3")) {//下一个是二级菜单
						dataBuffer.append("\"lv\":\""+lv+"\",");
						dataBuffer.append("\"haschildren\":true,");
						dataBuffer.append("\"children\":[");
					} else if(nextLv.equals("2")) {////下一个是一级菜单
						dataBuffer.append("\"lv\":\""+lv+"\"},");
					} else if ( i == count) {
						dataBuffer.append("\"lv\":\""+lv+"\"}");
						dataBuffer.append("]");
					}
				} else if (lv.equals("3")) {//二级菜单
					dataBuffer.append("{");
					dataBuffer.append("\"dm\":\""+queryBean.getValue("MKDM",i)+"\",");
					dataBuffer.append("\"mc\":\""+queryBean.getValue("MKMC",i)+"\",");
					dataBuffer.append("\"bm\":\""+queryBean.getValue("MKBM",i)+"\",");
					dataBuffer.append("\"url\":\""+qsym+"\",");
					dataBuffer.append("\"wxurl\":\""+wxqsym+"\",");
					if (nextLv.equals("4")) {//下一个是三级菜单
						dataBuffer.append("\"lv\":\""+lv+"\",");
						dataBuffer.append("\"haschildren\":true,");
						dataBuffer.append("\"children\":[");
					}else if (nextLv.equals("3")) {//下一个是二级菜单
						dataBuffer.append("\"lv\":\""+lv+"\"},");
					} else if(nextLv.equals("2")) {////下一个是一级菜单
						dataBuffer.append("\"lv\":\""+lv+"\"");
						dataBuffer.append("}],");
					} else if ( i == count) {
						dataBuffer.append("\"lv\":\""+lv+"\"}");
						dataBuffer.append("]}]");
					}
				}  else if (lv.equals("4")) {//三级级菜单
					dataBuffer.append("{");
					dataBuffer.append("\"dm\":\""+queryBean.getValue("MKDM",i)+"\",");
					dataBuffer.append("\"mc\":\""+queryBean.getValue("MKMC",i)+"\",");
					dataBuffer.append("\"bm\":\""+queryBean.getValue("MKBM",i)+"\",");
					dataBuffer.append("\"url\":\""+qsym+"\",");
					dataBuffer.append("\"wxurl\":\""+wxqsym+"\",");
					if (nextLv.equals("4")) {//下一个是三级菜单
						dataBuffer.append("\"lv\":\""+lv+"\"},");
					} else if(nextLv.equals("3")) {////下一个是二级菜单
						dataBuffer.append("\"lv\":\""+lv+"\"");
						dataBuffer.append("}]},");
					} else if(nextLv.equals("2")) {////下一个是一级菜单
						dataBuffer.append("\"lv\":\""+lv+"\"");
						dataBuffer.append("}]},");
					} else if ( i == count) {
						dataBuffer.append("\"lv\":\""+lv+"\"}");
						dataBuffer.append("]}]}]");
					}
				} 
				
				
				
			}
			dataBuffer.append("},");
			dataBuffer.append("\"status\":200,");
			dataBuffer.append("\"msg\": \"获取数据成功\"");
			dataBuffer.append("}");
			showSuccessJsonMsg(response,dataBuffer.toString());
			LogHelper.logInfo(QueryWapMkdm.class, ip+" 获取子系统 "+zxtdm+" 授权模块!");
			LogHelper.logDebug(QueryWapMkdm.class, "QueryWapMkdm 获取子系统 "+zxtdm+" 授权模块成功="+dataBuffer.toString());
			
		} catch (InvalidParamException  e) {
			showErrorJsonMsg(response,"{\"data\":{},\"status\":100,\"msg\":\"提交内容含有非法参数\"}");
			LogHelper.logError(QueryWapMkdm.class, "获取！"+zxtdm+" 模块失败!"+e.getMessage()); 
		} catch(Exception e) {
			showErrorJsonMsg(response,"{\"data\":{},\"status\":100,\"msg\":\""+e.getMessage()+"\"}");
			LogHelper.logError(QueryWapMkdm.class, "获取！"+zxtdm+" 模块失败!:"+e.getMessage()); 
		}
		
		
	}

}
