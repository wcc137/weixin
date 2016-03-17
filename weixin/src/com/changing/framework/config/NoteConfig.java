package com.changing.framework.config;

import java.util.HashMap;

import com.changing.framework.helper.LogHelper;
import com.changing.framework.helper.XmlHelper;

/**
 * 存储系统定义的常用变量
 */
public class NoteConfig
{
	/** ******************系统登陆时相关信息******************* */
	public final static String ERROR_YH = "ERROR_YH"; // 输入用户登录代码出错
	public final static String ERROR_RE = "ERROR_RE"; // 输入用户重名
	public final static String ERROR_DOMAIN_KEY = "ERROR_DOMAIN_KEY"; // 域用户登录加密key不匹配
	public final static String ERROR_FORBIDDEN = "ERROR_FORBIDDEN"; // 用户已经被禁用了
	public final static String ERROR_KL = "ERROR_KL"; // 输入用户口令出错
	public final static String ERROR_SESSION = "ERROR_SESSION"; // session失效
	public final static String ERROR_PRIVIEW = "ERROR_PRIVIEW"; // 用户无当前页面的操作权限
	public final static String ERROR_DB = "ERROR_DB"; // 数据库访问错
	public final static String ERROR_NULL = "ERROR_NULL"; // 用户输入的代码或口令为空
	/** ******************系统登陆时相关信息******************* */

	/** ******************系统状态代码******************* */
	public final static int STATUS_SUCCESS = 1; // 系统正常允许
	public final static int STATUS_DBERROR = 2; // 数据库错误
	public final static int STATUS_NOTINIT = 3; // 系统没有初始化
	public final static int STATUS_INITERROR = 4; // 系统初始化运行环境失败
	/** ******************系统状态代码******************* */

	/** ******************Word报表代码******************* */
	//大文件类型
	public static final String DWJLX_WORD = "91"; //WORD文档
	public static final String DWJLX_EXECL = "92"; //EXECL表格
	public static final String DWJLX_PPOIN = "93"; //PPT幻灯片
	public static final String DWJLX_EXECLT = "94"; //Excel图表
	public static final String DWJLX_VISIO = "95"; //Visio画图
	public static final String DWJLX_PROJECT = "96"; //Project项目
	public static final String DWJLX_CID = "97"; //CAD文件
	public static final String DWJLX_PICTURE = "98"; //图片文件
	public static final String DWJLX_PDF = "99"; //PDF
	public static final String DWJLX_TIFF = "90"; //TIFF

	/** ******************信息相关的代码******************* */
	//信息类型
	public static final String MES_TYPE_01 = "01"; //系统提示信息
	public static final String MES_TYPE_02 = "02"; //审批提示信息
	public static final String MES_TYPE_03 = "03"; //邮件提示信息
	public static final String MES_TYPE_04 = "04"; //个人提示信息

	//信息级别
	public static final String MES_LEVEL_01 = "11"; //低级
	public static final String MES_LEVEL_02 = "12"; //中级
	public static final String MES_LEVEL_03 = "13"; //高级

	//信息状态
	public static final String MES_STATUS_YES = "1"; //已经查阅
	public static final String MES_STATUS_NO = "0"; //未查看

	//信息是否超连接
	public static final int MES_URL_YES = 1; //超连接
	public static final int MES_URL_NO = 0; //非超连接
    //是否代理
    public static final String SFDL_YES = "1"; //代理
    public static final String SFDL_NO = "0"; //不代理
	/** ******************系统状态代码******************* */

	private static NoteConfig noteInfo = null;
	private HashMap mapNote = null;

	public static synchronized NoteConfig getSelf() {
		if (noteInfo == null)
			noteInfo = new NoteConfig();
		return noteInfo;
	}

	/**
	 * 构造函数
	 */
	private NoteConfig() {
		init();
	}

	/**
	 * 初始化相关属性
	 */
	public synchronized void init() {
		try {
		XmlHelper xmlHelper = new XmlHelper(SysConfig.getWebPath() + "/WEB-INF/resource/note.xml");
		mapNote = xmlHelper.getMap();
		} catch (Exception e) {
			LogHelper.logError(NoteConfig.class, e, "note.xml配置文件初始化失败!");
		}
	}
	/**
	 * @param key 属性主键
	 * @return 根据主键获取属性值
	 */
	public String getNote(String key) {
		Object obj = mapNote.get(key);
		return obj == null ? "" : obj.toString();
	}
}
