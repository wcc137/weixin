/**
 *@公司：          前景科技
 *@系统名称：changing
 *@文件名称：LogHelper.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2011-8-5 下午03:06:12
 *@完成时间：2011-8-5 下午03:06:12
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.helper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import com.changing.framework.config.SysConfig;
/**
 * @author Administrator <p> 功能描述: <p> 使用示例：<p>
 */
public class LogHelper {

	/**
	 * 将日志信息写入到指定路径文件
	 * 
	 * @param str
	 *        日志信息
	 * @param path
	 *        日志文件路径
	 */
	public static void log(String str, String path) {
		PrintWriter printWriter = null;
		try {
			if (FileHelper.fileIsExists(path)) {//文件存在
				printWriter = new PrintWriter(new FileOutputStream(path, true));
				printWriter.println(new java.sql.Timestamp(System.currentTimeMillis()) + "：  " + str);
			} else {//文件不存在
				String fileDirectoryStr = path.substring(0, path.lastIndexOf(File.separator) + 1);
				if (FileHelper.creatDirectory(fileDirectoryStr) && FileHelper.createFile(path)) {
					printWriter = new PrintWriter(new FileOutputStream(path, true));
					printWriter.println(new java.sql.Timestamp(System.currentTimeMillis()) + "：  " + str);
				} else {
					LogHelper.logError(LogHelper.class,"创建日志文件失败");
				}
			}
		} catch (FileNotFoundException e) {
			LogHelper.logError(LogHelper.class,"文件写入失败,找不到文件!"+path+e.getStackTrace());
		} catch (IOException e) {
			LogHelper.logError(LogHelper.class,"创建日志文件失败!"+e.getStackTrace());
		} catch (Exception e) {
			LogHelper.logError(LogHelper.class,"其它错误!"+e.getStackTrace());
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}
	}
	/**
	 * 打印错误日子到error.log
	 * @param outErro
	 * @param str
	 */
	public static void logError(boolean outErro, String str) {
		if (outErro) {
			logError(str);
		}
	}
	/**
	 * 写出错日志
	 * 
	 * @param str
	 *        日志信息
	 */
	public static void logError(String str) {
		log(str,   SysConfig.getLogPath() + "error.log");
	}
	/**
	 * 写出错日志
	 * @param obj	当前操作的类文件对象
	 * @param str	日志信息
	 */
	public static void logError(Class obj, String str) {
		getLogger(obj).error(str);
	}
	/**
	 * 写出错日志
	 * @param e	例外对象
	 */
	public static void logError(Exception e) {
		logError(null, e);
	}

	/**
	 * 写出错日志，并追踪相应的调用的程序
	 * @param obj	当前操作的类文件对象
	 * @param e	例外对象
	 */
	public static void logError(Class obj, Exception e) {
		logError(obj, e, null);
	}
	/**
	 * 写出错日志，并追踪相应的调用的程序
	 * @param obj	当前操作的类文件对象
	 * @param e	例外对象
	 * @param str	附加日志信息
	 */
	public static void logError(Class obj, Exception e, String str) {
		Logger logger = getLogger(obj);
		StringBuffer sb = new StringBuffer();
		if (str != null && !(str = str.trim()).equals(""))
			sb.append("\r\n\t").append(str);
		sb.append("\r\n\t").append(e.getMessage());
		StackTraceElement[] aStack = e.getStackTrace();
		int len = aStack.length;
		for (int i = 0; i < len; i++) {
			StackTraceElement ste = aStack[i];
			if ((str = ste.getClassName()) != null) {
				sb.append("\r\n\t").append(ste.toString());
			}
		}
		sb.append("\r\n");
		logger.error(sb);
	}
	
	/**
	 * 打印警告日志文件
	 * 
	 */
	public static void logWarn(Class obj, String str) {
		Logger logger = getLogger(obj);
		logger.warn(str);
		
	}
	/**
	 * 打印debug日志
	 * 
	 * @param outDuebug
	 * @param str
	 */
	public static void logDebug(boolean outDuebug, String str) {
		if (outDuebug) {
			logDebug(str);
		}
	}
	/**
	 * 写调试日志
	 * 
	 * @param str
	 *        日志信息
	 */
	public static void logDebug(String str) {
		log(str,  SysConfig.getLogPath() + "debug.log");
	}
	
	/**
	 * 写调试日志
	 * @param obj	当前操作的类文件对象
	 * @param str	日志信息
	 */
	public static void logDebug(Class obj, String str) {
		Logger logger = getLogger(obj);
		if(logger.isDebugEnabled()) {
			logger.debug(str);
		}
	}

	/**
	 * 写提供给管理人员查看的信息，可以是成功信息或其他信息
	 * 
	 * @param str
	 *        日志信息
	 */
	public static void logMsg(String str) {
		logFile(str, "msg.log");
	}

	/**
	 * 写提供给管理人员查看的信息，可以是成功信息或其他信息
	 * @param str	日志信息
	 */
	public static void logInfo(String str) {
		logInfo(null, str);
	}
	/**
	 * 写提供给管理人员查看的信息，可以是成功信息或其他信息
	 * @param obj	当前操作的类文件对象
	 * @param str	日志信息
	 */
	public static void logInfo(Class obj, String str) {
		Logger logger = getLogger(obj);
		if(logger.isInfoEnabled()) {
			logger.info(str);
		}
	}
		
	/**
	 * 
	 * @param obj
	 * @return
	 */
	private static Logger getLogger(Class obj) {
		Logger logger = null;
		if (obj == null) {
			logger = Logger.getRootLogger();
		} else {
			logger = Logger.getLogger(obj);
		}
		return logger;
	}
	/**
	 * 报表出错日志
	 * 
	 * @param str
	 *        日志信息
	 */
	public static void logReport(String str) {
		logFile(str, "report.log");
	}

	/**
	 * 调试出错日志
	 * 
	 * @param str
	 *        日志信息
	 * @param file
	 *        日志路径下的文件名称
	 */
	public static void logFile(String file,String str) {
		log(str,  SysConfig.getLogPath() + file);
	}
}
