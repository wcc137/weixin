/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：SystemInitialization.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-8-5 下午03:22:07
 *@完成时间：2011-8-5 下午03:22:07
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.common.systemmanager;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;

import com.changing.framework.config.DbConfig;
import com.changing.framework.config.DomainConfig;
import com.changing.framework.config.InterfaceConfig;
import com.changing.framework.config.PiOdbcInterfaceConfig;
import com.changing.framework.config.SysConfig;
/**
 * @author Administrator <p> 功能描述: <p> 使用示例：<p>
 */
@WebListener
public class SystemInitialization implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		//应用退出时的善后处理
		System.out.println("正在执行SystemInitialization中的Destroy ...");
		//销毁
		System.out.println("完成SystemInitialization中的Destroy...");
	}
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		try {
			System.out.println("正在执行SystemInitialization中的Initializ...");
			String path = servletContext.getRealPath("/WEB-INF/resource");
			String webPath = path.substring(0, path.indexOf("WEB-INF") - 1);
			System.out.println(webPath);
			
			//系统提示消息配置文件初始化
			ErrorMessage.setPath( webPath + "/WEB-INF/resource/ErrorMessage.properties");
			System.out.println("系统错误消息配置类ErrorMessage初始化...");
			ErrorMessage.reInits();
			
			//系统配置信息初始化
			SysConfig.setWebPath(webPath);
			System.out.println("系统信息配置类SysConfig初始化...");
			SysConfig.init();
			
			//log4j日志配置文件路径初始化
		 	System.out.println("设置log4j日志配置文件路径...");
		 	Properties prop = new Properties();      
		 	FileInputStream fileInputStream = new FileInputStream(webPath+"/WEB-INF/resource/log4j.properties");
		 	prop.load(fileInputStream);
		 	prop.setProperty("log4j.appender.ERROR_FILE.File", SysConfig.getLogPath()+ prop.getProperty("log4j.appender.ERROR_FILE.File")); //设置错误日志文件的输出路径     
		 	prop.setProperty("log4j.appender.WARN_FILE.File",  SysConfig.getLogPath() + prop.getProperty("log4j.appender.WARN_FILE.File")); //设置警告日志文件的输出路径   
		 	prop.setProperty("log4j.appender.INFO_FILE.File",  SysConfig.getLogPath() + prop.getProperty("log4j.appender.INFO_FILE.File")); //设置消息日志文件的输出路径   
		 	prop.setProperty("log4j.appender.DEBUG_FILE.File", SysConfig.getLogPath() + prop.getProperty("log4j.appender.DEBUG_FILE.File")); //设置调试日志文件的输出路径
		 	PropertyConfigurator.configure(prop); //加载配置项
		 	fileInputStream.close(); 
		 	//System.setProperty("LogFilePath",SysConfig.getLogPath());
		 	
			if (SysConfig.isDomain()) {//域用户验证
				System.out.println("域登录信息配置类DomainConfig初始化...");
				DomainConfig.setWebPath(webPath);
				DomainConfig.init();
			}
			//接口配置文件初始化
			System.out.println("接口配置类InterfaceConfig.init()初始化...");
			InterfaceConfig.setWebPath(webPath);
			InterfaceConfig.init();
			
			//PI_ODBC接口配置文件初始化
			System.out.println("接口配置类pi_odbc_InterfaceConfig.init()初始化...");
			PiOdbcInterfaceConfig.setWebPath(webPath);
			PiOdbcInterfaceConfig.init();
			
			
			//数据库信息初始化
			System.out.println("系统数据库配置类DbConfig.init()初始化...");
			DbConfig.init();
			if (DbConfig.getConn() == null) {
				System.out.println("系统数据库配置类DbConfig.init()初始化失败,无法连接数据库!");
				throw new SQLException("系统数据库配置类DbConfig.init()初始化失败,无法连接数据库!");
			}
			/**
			System.out.println("系统基础数据类parentApplication.initialize()初始化...");
			//系统代码初始化工作 员工代码 部门代码 以及 标准代码初始化
			ParentApplication parentApplication = new ParentApplication();
			parentApplication.initialize();
			if (!parentApplication.isIsStart()) {
				throw new SQLException("系统基础数据类parentApplication.initialize()初始化失败!");
			} else {
				servletContext.setAttribute("parentapplication", parentApplication);	
			}
			**/
			System.out.println("完成SystemInitialization的初始化工作!");
		} catch (Exception e) {
			System.out.println("初始化工作未完成，出错信息为：\n" + e.toString());
			servletContext.setAttribute("appStartError", "系统启动初始化失败，请联系管理员! 错误信息："+e.getMessage());
		}
	}
}
