package com.changing.framework.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
	 * 类型转换处理程序
	 */
	public class TypeChange
	{
		private static Log logger = LogFactory.getLog(TypeChange.class);
		/**
		 * 将对象转为字符串
		 * @param obj 对象
		 * @return 对象字符串
		 */
		public static String objToStr(Object obj) {
			return obj == null ? "" : obj.toString();
		}

		/**
		 * 将字符串转化为整型，如果出错，则返回默认值
		 * @param str 要进行整型转化的字符串
		 * @param defaultValue 默认替代值
		 * @return 转化结果值
		 */
		public static int strToInt(String str, int defaultValue) {
			if (str == null || (str = str.trim()).equals(""))
				return defaultValue;
			try {
				return new Integer(str).intValue();
			} catch (Exception e) {
				logger.error(e); // 写错误日志
				try {
					return new Double(str).intValue();
				} catch (Exception e2) {
					logger.error(e);// 写错误日志
					return defaultValue;
				}
			}
		}

		/**
		 * 将字符串转化为long类型，如果出错，则返回默认值
		 * @param str 要进行long类型转化的字符串
		 * @param defaultValue 默认替代值
		 * @return 转化结果值
		 */
		public static long strToLong(String str, long defaultValue) {
			if (str == null || (str = str.trim()).equals(""))
				return defaultValue;
			try {
				return new Long(str).longValue();
			} catch (Exception e) {
				logger.error(e); // 写错误日志
				try {
					return new Double(str).longValue();
				} catch (Exception e2) {
					logger.error(e); // 写错误日志
					return defaultValue;
				}
			}
		}

		/**
		 * 将字符串转化为double类型，如果出错，则返回默认值
		 * @param str 要进行double类型转化的字符串
		 * @param defaultValue 默认替代值
		 * @return 转化结果值
		 */
		public static double strToDouble(String str, double defaultValue) {
			if (str == null || (str = str.trim()).equals(""))
				return defaultValue;
			try {
				return new Double(str).doubleValue();
			} catch (Exception e) {
				logger.error(e);// 写错误日志
				return defaultValue;
			}
		}

		/**
		 * 将字符串对象使用指定的分隔符分割，然后保存在字符串数组中
		 * @param	str  待分割的字符串对象
		 * @param	strDelim  指定的分隔符，如果是“$”，请用“\\$”
		 * @return	保存分割后各个字符串的数组
		 **/
		public static String[] strToArray(String str, String delim) {
			if (str == null || str.equals(""))
				return new String[0];
			return str.split(delim);
		}

		/**
		 * 将字符串转化为列表集合
		 * @param str 要进行类型转化的字符串
		 * @return 转化结果值
		 */
		public static ArrayList strToList(String str, String delim) {
			if (str == null || str.equals(""))
				return new ArrayList();
			String[] objArray = (str + delim + "1").split(delim);
			int len = objArray.length - 1;
			ArrayList list = new ArrayList(len);
			for (int i = 0; i < len; i++)
				list.add(objArray[i]);
			return list;
		}

		/**
		 * 将字符串转化为列表集合
		 * @param str 要进行类型转化的字符串
		 * @return 转化结果值
		 */
		public static Vector strToVector(String str, String delim) {
			if (str == null || str.equals(""))
				return new Vector();
			String[] objArray = (str + delim + "1").split(delim);
			int len = objArray.length - 1;
			Vector vec = new Vector(len);
			for (int i = 0; i < len; i++)
				vec.add(objArray[i]);
			return vec;
		}

		/**
		 * 将数值转为指定分隔符连接的字符串
		 * @param obj
		 * @param delim
		 * @return
		 */
		public static String arrayToStr(Object[] obj, String delim) {
			if (obj == null || obj.length == 0)
				return "";
			String str = "";
			String rtn = "";
			for (int i = 0; i < obj.length; i++) {
				str = obj[i].toString();
				if (str != null && !(str = str.trim()).equals(""))
					rtn += delim + str;
			}
			if (!rtn.equals("") && !delim.equals(""))
				rtn = rtn.substring(delim.length());
			return rtn;
		}

		/**
		 * 将数值转为指定分隔符连接的字符串
		 * @param obj
		 * @param delim
		 * @return
		 */
		public static ArrayList arrayToList(Object[] obj) {
			if (obj == null || obj.length == 0)
				return new ArrayList();
			ArrayList list = new ArrayList();
			for (int i = 0; i < obj.length; i++) {
				list.add(obj[i]);
			}
			return list;
		}

		/**
		 * 将列表集合转化为字符串类型，如果出错，则返回默认值
		 * @param str 要进行字符串类型转化的列表集合
		 * @param delim 分隔符
		 * @return 转化结果值
		 */
		public static String listToStr(List list, String delim) {
			if (list == null || list.isEmpty())
				return "";
			String str = "";
			String rtn = "";
			for (int i = 0; i < list.size(); i++) {
				str = list.get(i).toString();
				if (str != null && !(str = str.trim()).equals(""))
					rtn += delim + str;
			}
			if (!rtn.equals(""))
				rtn = rtn.substring(delim.length());
			return rtn;
		}

		/**
		 * 将列表集合转化为字符串类型，如果出错，则返回默认值
		 * @param str 要进行字符串类型转化的列表集合
		 * @param delim 分隔符
		 * @return 转化结果值
		 */
		public static String setToStr(Set set, String delim) {
			if (set == null || set.isEmpty())
				return "";
			String str = "";
			String rtn = "";
			Iterator it = set.iterator();
			while (it.hasNext()) {
				str = it.next().toString();
				if (str != null && !(str = str.trim()).equals(""))
					rtn += delim + str;
			}
			if (!rtn.equals(""))
				rtn = rtn.substring(delim.length());
			return rtn;
		}

		/**
		 * Vector转换成字符串，用','号分开
		 * @param vec
		 * @return
		 */
		public static String vectorToStr(Vector vec) {
			String ret = "";
			if (vec != null && vec.size() > 0) {
				for (int i = 0; i < vec.size(); i++) {
					ret += vec.get(i).toString().trim();
					if (i < i - 1) {
						ret += ",";
					}
				}
			}
			return ret;
		}
	}