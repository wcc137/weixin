package com.changing.framework.config;

import com.changing.framework.helper.StringHelper;
import com.changing.framework.helper.TypeChange;
import com.changing.framework.helper.XmlHelper;

/**
 * 存储系统配置的相关信息
 */
public class SysConfig {
	// 没有权限时显示的页面
	private static String PAGE_NOT_POWER;
	// 出错时显示的页面
	private static String PAGE_ERROR;
	// session失效时显示的页面
	private static String PAGE_NOT_SESSION;
	//用户登录成功之后跳转的页面
	private static String PAGE_REDIRECT;
	// 应用服务器
	private static String SERVER;
	// 登陆session中的info名称
	public static final String LOGON_INFO_NAME = "LogInfo";
	// 返回出错信息的名称
	public static final String ERROR_MEG = "ErrorMeg";
	// 返回查询条件的Info名称
	public static final String RETURN_SELECT_CONDITION = "selectConditionInfo";
	//数据库报错信息
	public static final String DB_ERROR_00001 = "ORA-00001"; //主键重复
	//数据库常用的操作类型
	public static final String DB_CONTROL_INSERT = "INSERT";
	public static final String DB_CONTROL_UPDATE = "UPDATE";
	public static final String DB_CONTROL_DELETE = "DELETE";

	//计划任务执行程序类型
	public static final int EXEC_TYPE_JSP = 1; // URL
	public static final int EXEC_TYPE_APP = 2; // 应用程序
	public static final int EXEC_TYPE_SQL = 3; // SQL
	public static final int EXEC_TYPE_PROC = 4; // 存储过程
	public static final int EXEC_TYPE_JAVA = 5; // Java接口实现类

	// 系统名称
	private static String sysName;
	// 客户名称
	private static String client;
	// 当前系统日志文件存储路径
	private static String logPath;
	// 当前系统文件上传路径
	private static String uploadPath;
	// 当前系统文件上传路径(可用于WEB访问)
	private static String uploadPathWeb;
	// 当前系统运行绝对路径
	private static String webPath;
	// 当前系统的超级密码
	private static String superPwd;
	// 当前系统刷新时间
	private static int refreshTime;
	// 是否打印错误日志
	private static boolean outError;
	// 是否打印DEBUGGER日志
	private static boolean outDebug;
	// 登录是否启用验证码验证
	private static boolean isYzm;
	//是否启用域账户登录
	private static boolean isDomain;

	/**
	 * 初始化相关属性
	 */
	public static void init() throws Exception {
		try {
			XmlHelper xmlHelper = new XmlHelper(webPath + "/WEB-INF/resource/system.xml");
			sysName = StringHelper.ConvertStrNull(xmlHelper.getValue("name"));
			SERVER = StringHelper.ConvertStrNull(xmlHelper.getValue("server"));
			client = StringHelper.ConvertStrNull(xmlHelper.getValue("client"));
			refreshTime = TypeChange.strToInt(StringHelper.ConvertStrNull(xmlHelper.getValue("refresh_time")), 300);
			PAGE_ERROR = StringHelper.ConvertStrNull(xmlHelper.getValue("pageError"));
			PAGE_NOT_SESSION = StringHelper.ConvertStrNull(xmlHelper.getValue("pageNotSession"));
			PAGE_NOT_POWER = StringHelper.ConvertStrNull(xmlHelper.getValue("pageNotPower"));
			PAGE_REDIRECT = StringHelper.ConvertStrNull(xmlHelper.getValue("pageRedirect"));
			logPath = StringHelper.ConvertStrNull(xmlHelper.getValue("log_path"));
			uploadPath = StringHelper.ConvertStrNull(xmlHelper.getValue("upload_path"));
			uploadPathWeb = StringHelper.ConvertStrNull(xmlHelper.getValue("upload_path_web"));
			superPwd = StringHelper.ConvertStrNull(xmlHelper.getValue("super_pwd"));
			outError = StringHelper.ConvertStrNull(xmlHelper.getValue("outError")).toUpperCase().equals("TRUE");
			outDebug = StringHelper.ConvertStrNull(xmlHelper.getValue("outDebug")).toUpperCase().equals("TRUE");
			isYzm = StringHelper.ConvertStrNull(xmlHelper.getValue("isYzm")).toUpperCase().equals("TRUE");
			isDomain = StringHelper.ConvertStrNull(xmlHelper.getValue("domain")).toUpperCase().equals("TRUE");

		} catch (Exception e) {
			throw new Exception("系统配置文件system.xml初始化失败!" + e.getMessage());
		}
	}

	public static boolean isOutError() {
		return outError;
	}

	public static void setOutError(boolean outError) {
		SysConfig.outError = outError;
	}

	public static boolean isOutDebug() {
		return outDebug;
	}

	public static void setOutDebug(boolean outDebug) {
		SysConfig.outDebug = outDebug;
	}

	public static String getPAGE_NOT_POWER() {
		return PAGE_NOT_POWER;
	}

	public static void setRefreshTime(int refreshTime) {
		SysConfig.refreshTime = refreshTime;
	}

	/**
	 * @return 当前系统刷新时间
	 */
	public static int getRefreshTime() {
		return refreshTime;
	}

	public static String getSuperPwd() {
		return superPwd;
	}

	public static void setSuperPwd(String superPwd) {
		SysConfig.superPwd = superPwd;
	}

	public static void setPAGE_NOT_POWER(String page_not_power) {
		PAGE_NOT_POWER = page_not_power;
	}

	public static String getPAGE_ERROR() {
		return PAGE_ERROR;
	}

	public static void setPAGE_ERROR(String page_error) {
		PAGE_ERROR = page_error;
	}

	public static String getPAGE_NOT_SESSION() {
		return PAGE_NOT_SESSION;
	}

	public static void setPAGE_NOT_SESSION(String page_not_session) {
		PAGE_NOT_SESSION = page_not_session;
	}

	public static String getSERVER() {
		return SERVER;
	}

	public static void setSERVER(String server) {
		SERVER = server;
	}

	public static String getSysName() {
		return sysName;
	}

	public static void setSysName(String sysName) {
		SysConfig.sysName = sysName;
	}

	public static String getLogPath() {
		return logPath;
	}

	public static void setLogPath(String logPath) {
		SysConfig.logPath = logPath;
	}

	public static String getUploadPath() {
		return uploadPath;
	}

	public static void setUploadPath(String uploadPath) {
		SysConfig.uploadPath = uploadPath;
	}

	public static String getUploadPathWeb() {
		return uploadPathWeb;
	}

	public static void setUploadPathWeb(String uploadPathWeb) {
		SysConfig.uploadPathWeb = uploadPathWeb;
	}

	public static String getLOGON_INFO_NAME() {
		return LOGON_INFO_NAME;
	}

	public static String getERROR_MEG() {
		return ERROR_MEG;
	}

	public static String getRETURN_SELECT_CONDITION() {
		return RETURN_SELECT_CONDITION;
	}

	public static String getClient() {
		return client;
	}

	public static void setClient(String client) {
		SysConfig.client = client;
	}

	public static String getWebPath() {
		return webPath;
	}

	public static void setWebPath(String webPath) {
		SysConfig.webPath = webPath;
	}

	public static boolean isYzm() {
		return isYzm;
	}

	public static void setYzm(boolean isYzm) {
		SysConfig.isYzm = isYzm;
	}

	public static boolean isDomain() {
		return isDomain;
	}

	public static void setDomain(boolean isDomain) {
		SysConfig.isDomain = isDomain;
	}

	public static String getPAGE_REDIRECT() {
		return PAGE_REDIRECT;
	}

	public static void setPAGE_REDIRECT(String page_redirect) {
		PAGE_REDIRECT = page_redirect;
	}
	public static String getUploadPath(String isWeb){
		return isWeb.equals("1")?getUploadPathWeb():getUploadPath();
	}
}