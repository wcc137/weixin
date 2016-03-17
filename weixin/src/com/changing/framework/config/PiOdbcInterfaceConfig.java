package com.changing.framework.config;

import com.changing.framework.helper.StringHelper;
import com.changing.framework.helper.XmlHelper;

/**
 * 存储接口相关信息
 * 
 * @author Administrator
 */
public class PiOdbcInterfaceConfig {
	/**
	 * PI_ODBC接口信息配置
	 */
	// PI_ODBC接口程序部署地址
	private static String pi_odbc_db_url;
	private static String pi_odbc_db_driver;
	private static String pi_odbc_db_user;
	private static String pi_odbc_db_password;
	// 当前系统运行绝对路径
	private static String webPath;

	private PiOdbcInterfaceConfig() {

	}

	/**
	 * 初始化相关属性
	 */
	public static void init() throws Exception {
		try {
			XmlHelper xmlHelper = new XmlHelper(webPath + "/WEB-INF/resource/interface_pi_Odbc.xml");
			pi_odbc_db_url = StringHelper.ConvertStrNull(xmlHelper.getValue("pi_odbc_db_url"));
			pi_odbc_db_driver = StringHelper.ConvertStrNull(xmlHelper.getValue("pi_odbc_db_driver"));
			pi_odbc_db_user = StringHelper.ConvertStrNull(xmlHelper.getValue("pi_odbc_db_user"));
			pi_odbc_db_password = StringHelper.ConvertStrNull(xmlHelper.getValue("pi_odbc_db_password"));

		} catch (Exception e) {
			throw new Exception("接口配置文件interface_pi_Odbc.xml初始化失败!" + e.getMessage());
		}
	}

	public static String getWebPath() {
		return webPath;
	}

	public static void setWebPath(String webPath) {
		PiOdbcInterfaceConfig.webPath = webPath;
	}

	public static String getPi_odbc_db_url() {
		return pi_odbc_db_url;
	}

	public static void setPi_odbc_db_url(String pi_odbc_db_url) {
		PiOdbcInterfaceConfig.pi_odbc_db_url = pi_odbc_db_url;
	}

	public static String getPi_odbc_db_driver() {
		return pi_odbc_db_driver;
	}

	public static void setPi_odbc_db_driver(String pi_odbc_db_driver) {
		PiOdbcInterfaceConfig.pi_odbc_db_driver = pi_odbc_db_driver;
	}

	public static String getPi_odbc_db_user() {
		return pi_odbc_db_user;
	}

	public static void setPi_odbc_db_user(String pi_odbc_db_user) {
		PiOdbcInterfaceConfig.pi_odbc_db_user = pi_odbc_db_user;
	}

	public static String getPi_odbc_db_password() {
		return pi_odbc_db_password;
	}

	public static void setPi_odbc_db_password(String pi_odbc_db_password) {
		PiOdbcInterfaceConfig.pi_odbc_db_password = pi_odbc_db_password;
	}
}
