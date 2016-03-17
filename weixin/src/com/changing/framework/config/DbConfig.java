package com.changing.framework.config;

import java.sql.Connection;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.changing.framework.db.DbConn;
import com.changing.framework.helper.LogHelper;
import com.changing.framework.helper.StringHelper;
import com.changing.framework.helper.XmlHelper;
/**
 * 保存数据库的配置信息
 */
public class DbConfig
{
	public static final String ORACLE = "ORACLE"; // 数据库种类为ORACLE
	public static final String MYSQL = "MYSQL"; // 数据库种类为MYSQL
	public static final String SQLSERVER = "SQLSERVER"; // 数据库种类为sqlserver
	public static final String	WEBSPHERE = "WEBSPHERE";
	public static final String	TOMCAT = "TOMCAT";
	private static String db_database; // 数据库种类，如ORACLE,MYSQL,SQLSERVER等
	private static String conn_type; // 数据库连接方式：WEBSPHERE 、TOMCAT 为空 则直接连
	private static String name; // 数据库名称
	private static String url; // 数据源名称
	private static String driver; // 数据库驱动程序
	private static String user; // 数据库访问用户
	private static String password; // 数据库访问密码
	private static String datasource; // 使用连接池时的数据源名称
	private static javax.naming.Context context; // 数据库连接
	/**
	 * 获取系统默认的数据库连接
	 * @return 数据库连接
	 */
	public static Connection getConn() {
		if (conn_type.equals(""))
			return DbConn.getConn(driver, url, user, password); // 直接获取连接
		try {
			return ((DataSource)context.lookup(datasource)).getConnection();
		} catch (Exception e) {
			LogHelper.logError(DbConfig.class,"从连接池获取连接失败!"+e.getMessage()+"从db.xml中读取数据库配置文件,直接获取连接!");
			return DbConn.getConn(driver, url, user, password); // 直接获取连接;
		}
	}
	/**
	 * 获取系统默认的数据库连接
	 * @param _user 数据库访问用户
	 * @param _password 数据库访问密码
	 * @return 数据库连接
	 */
	public static Connection getConn(String _user, String _password) {
		if (user.equals(_user))
			return getConn();
		return DbConn.getConn(driver, url, _user, _password);
	}
	/**
	 * @return 数据库名称
	 */
	public static String getName() {
		return name;
	}
	/**
	 * 获取数据库访问用户
	 * @return 数据库访问用户
	 */
	public static String getUser() {
		return user;
	}
	/**
	 * 初始化相关属性
	 */
	public static void init() throws Exception {
		if (context != null) {
			try {
				context.close(); // 关闭
			} catch (NamingException e) {
				throw new Exception("javax.naming.Context context 关闭失败!"+e.getMessage());
			}
		}
		try {
			XmlHelper xmlHelper = new XmlHelper(SysConfig.getWebPath() + "/WEB-INF/resource/db.xml");
			// 数据库种类,如ORACLE,MYSQL,SQLSERVER等
		    db_database = StringHelper.ConvertStrNull(xmlHelper.getValue("db_database"));//数据库类型
			url = StringHelper.ConvertStrNull(xmlHelper.getValue("db_url")); // 数据源名称
			driver = StringHelper.ConvertStrNull(xmlHelper.getValue("db_driver")); // 数据库驱动程序
			user = StringHelper.ConvertStrNull(xmlHelper.getValue("db_user")); // 数据库访问用户
			password = StringHelper.ConvertStrNull(xmlHelper.getValue("db_password")); // 数据库访问密码
			datasource = StringHelper.ConvertStrNull(xmlHelper.getValue("db_datasource")); // 使用连接池时的数据源名称
			conn_type = StringHelper.ConvertStrNull(xmlHelper.getValue("conn_type")); // 连接方式 WEBSPHERE  、TOMCAT、为空则直连-
			if (conn_type.equals(WEBSPHERE)) {
				//webshpere连接池
				Hashtable parms = new Hashtable();
				parms.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
				try {
					context = new javax.naming.InitialContext(parms);
				} catch (NamingException e) {
					LogHelper.logError(DbConfig.class,e.getMessage());
				}
			} else if (conn_type.equals(TOMCAT)) {//tomcat连接池
				try {
					context = new javax.naming.InitialContext();
				} catch (NamingException e) {
					LogHelper.logError(DbConfig.class,e.getMessage());
				}
				datasource = "java:comp/env/" + datasource;
			}
		} catch (Exception e) {
			throw new Exception("系统数据库配置文件db.xml初始化失败!"+e.getMessage());
		}
	}
	public static String getDb_database() {
		return db_database;
	}
	public static void setDb_database(String db_database) {
		DbConfig.db_database = db_database;
	}
	public static String getConn_type() {
		return conn_type;
	}
	public static void setConn_type(String conn_type) {
		DbConfig.conn_type = conn_type;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		DbConfig.url = url;
	}
	public static String getDriver() {
		return driver;
	}
	public static void setDriver(String driver) {
		DbConfig.driver = driver;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		DbConfig.password = password;
	}
	public static String getDatasource() {
		return datasource;
	}
	public static void setDatasource(String datasource) {
		DbConfig.datasource = datasource;
	}
	public static javax.naming.Context getContext() {
		return context;
	}
	public static void setContext(javax.naming.Context context) {
		DbConfig.context = context;
	}
	public static void setName(String name) {
		DbConfig.name = name;
	}
	public static void setUser(String user) {
		DbConfig.user = user;
	}
}
