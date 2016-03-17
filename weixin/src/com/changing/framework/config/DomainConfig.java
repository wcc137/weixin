package com.changing.framework.config;

import com.changing.framework.helper.StringHelper;
import com.changing.framework.helper.XmlHelper;

public class DomainConfig {
	//域用户登录时间差（服务器时间差），单位为分钟，必须为整数，即当前时间向前/后推算多少分钟，默认为1 #########
	private static int difMinute;
	//域用户登录加密字符串前缀 必须和验证服务器中web.config中的 prefix 等同
	private static String prefix;
	//##域用户验证IIS服务器默认验证地址
	private static String iisvalidate;
	//域用户修改密码地址
	private static String iischgpwd;
	//域用户登录页面
	private static String domainUrl;
	// 当前系统运行绝对路径
	private static String webPath;
	
	/**
	 * 初始化相关属性
	 */
	public static void init() throws Exception {
		try {
		XmlHelper xmlHelper = new XmlHelper(webPath + "/WEB-INF/resource/domain.xml");
		difMinute = Integer.parseInt(StringHelper.ConvertStrNull(xmlHelper.getValue("difMinute")).equals("")?"1":StringHelper.ConvertStrNull(xmlHelper.getValue("difMinute"))); 
		prefix = StringHelper.ConvertStrNull(xmlHelper.getValue("prefix"));
		iisvalidate = StringHelper.ConvertStrNull(xmlHelper.getValue("iisvalidate"));
		iischgpwd = StringHelper.ConvertStrNull(xmlHelper.getValue("iischgpwd"));
		domainUrl = StringHelper.ConvertStrNull(xmlHelper.getValue("domainUrl"));
		} catch (Exception e) {
			throw new Exception("系统域登录配置文件domain.xml初始化失败!"+e.getMessage());
		}
	}
	public static String getWebPath() {
		return webPath;
	}
	public static void setWebPath(String webPath) {
		DomainConfig.webPath = webPath;
	}
	
	public static int getDifMinute() {
		return difMinute;
	}
	public static void setDifMinute(int difMinute) {
		DomainConfig.difMinute = difMinute;
	}
	public static String getPrefix() {
		return prefix;
	}
	public static void setPrefix(String prefix) {
		DomainConfig.prefix = prefix;
	}
	public static String getIisvalidate() {
		return iisvalidate;
	}
	public static void setIisvalidate(String iisvalidate) {
		DomainConfig.iisvalidate = iisvalidate;
	}
	public static String getIischgpwd() {
		return iischgpwd;
	}
	public static void setIischgpwd(String iischgpwd) {
		DomainConfig.iischgpwd = iischgpwd;
	}
	public static String getDomainUrl() {
		return domainUrl;
	}
	public static void setDomainUrl(String domainUrl) {
		DomainConfig.domainUrl = domainUrl;
	}
	
	
}
