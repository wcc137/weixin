/**
 * 修改系统用户密码
 */
package com.changing.common.systemmanager;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changing.framework.db.QueryOneBean;
import com.changing.framework.db.SqlExeBean;
import com.changing.framework.helper.LogHelper;

@WebServlet({"/phone/changePWD"})
public class UpdatePassword extends HttpServlet implements Serializable {
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
	/**
	 * 
	 * @param request
	 * @param response
	 */
	public void doSomething(HttpServletRequest request,
			HttpServletResponse response)  {
		try {
			chkPassword(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//核对用户 密码
	public void chkPassword (HttpServletRequest request,HttpServletResponse response) {
		String ip = request.getRemoteAddr();
		LogHelper.logDebug(UpdatePassword.class,ip+"UpdatePassword 修改密码...");
		//System.out.println("获取点餐统计前基础信息...");
		//response.setContentType("application/json;charset=UTF-8");
		//response.setContentType("text/html;charset=UTF-8");
		response.setContentType("text/plain;charset=UTF-8");
		//旧密码
		String jmm = request.getParameter("oldPwd")== null ?"": request.getParameter("oldPwd");
		//新密码
		String xmm = request.getParameter("newPwd") == null ? "" : request.getParameter("newPwd");
		//员工代码
		String ygdm = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		LogHelper.logInfo(UpdatePassword.class, ip+"["+ygdm+"] 修改密码...");
		try {
			if (ygdm.equals("")) {
				throw new Exception("密码修改失败,用户ID参数错误");
			}
			if (jmm.equals("")) {
				throw new Exception("密码修改失败,请输入原始密码");
			}
			if (xmm.equals("")) {
				throw new Exception("密码修改失败,请输入新密码");
			}
			String jmmMd5 = Password.encrypt(jmm);
			//查询员工代码记录
			QueryOneBean queryOneBean = new QueryOneBean();
			queryOneBean.executeQuery("SELECT YGDM,XM,MM FROM T_XT_YGDM WHERE YGDM ='"+ygdm+"'");
			String xmmStringMd5 = Password.encrypt(xmm);
			if (!jmmMd5.equals(queryOneBean.getValue("MM"))) {
				throw new Exception("密码修改失败,请输入正确的原始密码");
			} else {//更新系统员工密码
				SqlExeBean sqlExeBean = new SqlExeBean();
				if (sqlExeBean.executeUpdate("UPDATE T_XT_YGDM SET MM='"+xmmStringMd5+"' where ygdm ='"+ygdm+"'") == -1) {
					throw new Exception("密码修改失败 数据库错误");
				}
			}
			response.getWriter().write("密码修改成功");
		} catch(Exception e) {
			LogHelper.logInfo(UpdatePassword.class, ip+"["+ygdm+"] 密码修改失败！"+e.getMessage());
			LogHelper.logError(UpdatePassword.class, "UpdatePassword 密码修改出错了:"+e.getMessage());
			e.printStackTrace();
			try{
				response.getWriter().write(e.getMessage());
			}catch(IOException ex){}
		}
	}
}
