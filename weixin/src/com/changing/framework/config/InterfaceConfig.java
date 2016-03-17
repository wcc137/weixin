package com.changing.framework.config;

import com.changing.framework.helper.StringHelper;
import com.changing.framework.helper.XmlHelper;

/**
 * 存储接口相关信息
 * 
 * @author Administrator
 * 
 */
public class InterfaceConfig {
	/**
	 * PI接口信息配置
	 */
	// PI接口C程序部署地址
	private static String piServer;
	// 连接端口
	private static int piPort;
	// 连接超时
	private static int piSocketTimeOut;
	// 当前系统运行绝对路径
	private static String webPath;

	private InterfaceConfig() {

	}

	/**
	 * 初始化相关属性
	 */
	public static void init() throws Exception {
		try {
			XmlHelper xmlHelper = new XmlHelper(webPath
					+ "/WEB-INF/resource/interface.xml");
			piServer = StringHelper.ConvertStrNull(xmlHelper
					.getValue("PI_SERVER"));
			piPort = Integer.parseInt(StringHelper.ConvertStrNull(
					xmlHelper.getValue("PI_PORT")).equals("") ? "7885"
							: StringHelper.ConvertStrNull(xmlHelper
									.getValue("PI_PORT")));			
			piSocketTimeOut = Integer.parseInt(StringHelper.ConvertStrNull(
					xmlHelper.getValue("PI_SocketTimeout")).equals("") ? "60"
					: StringHelper.ConvertStrNull(xmlHelper
							.getValue("PI_SocketTimeout")));

		} catch (Exception e) {
			throw new Exception("接口配置文件interface.xml初始化失败!" + e.getMessage());
		}
	}

	public static String getPiServer() {
		return piServer;
	}

	public static void setPiServer(String piServer) {
		InterfaceConfig.piServer = piServer;
	}

	public static int getPiPort() {
		return piPort;
	}

	public static void setPiPort(int piPort) {
		InterfaceConfig.piPort = piPort;
	}

	public static int getPiSocketTimeOut() {
		return piSocketTimeOut;
	}

	public static void setPiSocketTimeOut(int piSocketTimeOut) {
		InterfaceConfig.piSocketTimeOut = piSocketTimeOut;
	}

	public static String getWebPath() {
		return webPath;
	}

	public static void setWebPath(String webPath) {
		InterfaceConfig.webPath = webPath;
	}
}
