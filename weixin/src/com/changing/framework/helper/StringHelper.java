/**
 * @公司： 前景科技
 * @系统名称：changing_framework
 * @文件名称：StringHelper.java
 * @功能描述: String工具类
 * @创建人 ：zn
 * @创建时间: 2010-10-27 下午06:38:59
 * @完成时间：2010-10-27 下午06:38:59
 * @修该人： lxp
 * @修改内容：增加方法
 * @修改日期：2010-10-30
 */
package com.changing.framework.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author watermelon
 *         <p>
 *         功能描述:
 *         <p>
 *         使用示例：
 *         <p>
 */
public class StringHelper {
	private static final String EMPTY = "";
	private static final int PAD_LIMIT = 8192;
	private static final int INDEX_NOT_FOUND = -1;

	/**
	 * 遇到为null的字符串对象则返回一个""空字符串对象
	 * 
	 * @param strNull 待检查的String对象
	 * @return 当检查的String对象为null时，返回一个""空字符串对象；否则返回原字符串
	 */
	public static String ConvertStrNull(String strNull) {
		return (strNull == null ? "" : strNull.trim());
	}

	/**
	 * <p>
	 * 判断字符串是否为空 null,""," " 返回true,否则返回false
	 * </p>
	 * 
	 * <pre>
	 * StringHelper.isBlank(null) = true,
	 * StringHelper.isBlank("") = true,
	 * StringHelper.isBlank(" ") = true,
	 * StringHelper.isBlank("bob") = false,
	 * StringHelper.isBlank(" bob ") = false
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param cs 字符序列
	 * @return
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是否为空，什么都没有，一个字符都没有，长度为0或者null 
	 *  <pre>
	 * StringHelper.isEmpty(null) = true,
	 *  StringHelper.isEmpty("") = true , 
	 *  StringHelper.isEmpty(" ") =	 false, 
	 *  StringHelper.isEmpty("abc") = false ,
	 *  StringHelper.isEmpty(" abc ") = false
	 *  </pre>
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>
	 * 遇到为null的字符串对象则返回一个""空字符串对象
	 * </p>
	 * 
	 * @param strNull 待检查的String对象
	 * @return :当检查的String对象strNull = null 时，返回一个""空字符串对象；否则返回原字符串
	 * 
	 * <pre>
	 * StringUtils.trimToEmpty(null)          = ""
	 * StringUtils.trimToEmpty("")            = ""
	 * StringUtils.trimToEmpty("     ")       = ""
	 * StringUtils.trimToEmpty("abc")         = "abc"
	 * StringUtils.trimToEmpty("    abc    ") = "abc"
	 * </pre>
	 * 
	 * @param str 待检查的String对象
	 * @return :当检查的String对象str = null 时，返回一个""空字符串对象;否则返回原字符串
	 */
	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	/**
	 * <p>
	 * 移除字符串左右两边的空字符(char &lt;= 32)
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.trim(null) = null
	 * StringUtils.trim("") = "" 
	 * StringUtils.trim(" ") = ""
	 * StringUtils.trim("abc") = "abc" 
	 * StringUtils.trim(" abc ") = "abc"
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	/**
	 * <p>
	 * 删除给定字符串的所有空格 {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.deleteWhitespace(null)         = null
	 * StringUtils.deleteWhitespace("")           = ""
	 * StringUtils.deleteWhitespace("abc")        = "abc"
	 * StringUtils.deleteWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 * 
	 * @param str 源字符串
	 * @return the 返回删除空格后的字符串
	 */
	public static String deleteWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}
		return new String(chs, 0, count);
	}

	/**
	 * <p>
	 * 检查一个String对象是否为空或者为空串
	 * </p>
	 * 
	 * @param strChk 待检查的String对象
	 * @return 当被检查String对象为空或者为空串时，返回true；否则返回false
	 */
	public static boolean isValuedString(String strChk) {
		return ((strChk != null) && (!strChk.equals("")));
	}

	/**
	 * <p>
	 * 比较两个字符串内容是否相等(区分大小写)
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equals(null, null)   = true
	 * StringUtils.equals(null, "abc")  = false
	 * StringUtils.equals("abc", null)  = false
	 * StringUtils.equals("abc", "abc") = true
	 * StringUtils.equals("abc", "ABC") = false
	 * </pre>
	 * 
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return
	 */
	public static boolean equals(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equals(str2);
	}

	/**
	 * <p>
	 * 比较两个字符串内容是否相等(忽略大小写)
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 * 
	 * @param str1 字符串1
	 * @param str2 字符串2
	 * @return
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
	}

	/**
	 * <p>
	 * 从源字符串起始位置搜索目标字符串在源字符串中第一次出现的索引 如果没有找到 则返回-1
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.indexOf(null, *)          = -1
	 * StringUtils.indexOf(*, null)          = -1
	 * StringUtils.indexOf("", "")           = 0
	 * StringUtils.indexOf("", *)            = -1 (except when * = "")
	 * StringUtils.indexOf("aabaabaa", "a")  = 0
	 * StringUtils.indexOf("aabaabaa", "b")  = 2
	 * StringUtils.indexOf("aabaabaa", "ab") = 1
	 * StringUtils.indexOf("aabaabaa", "")   = 0
	 * </pre>
	 * 
	 * @param str 源字符串
	 * @param searchStr 搜索目标字符串
	 * @return 返回目标字符串第一次出现的索引
	 */
	public static int indexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		return str.indexOf(searchStr);
	}

	/**
	 * 从源字符串指定起始位置搜索目标字符串在源字符串中第一次出现的索引
	 * 
	 * <pre>
	 * StringUtils.indexOf(null, *, *)          = -1
	 * StringUtils.indexOf(*, null, *)          = -1
	 * StringUtils.indexOf("", "", 0)           = 0
	 * StringUtils.indexOf("", *, 0)            = -1 (except when * = "")
	 * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
	 * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
	 * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
	 * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
	 * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
	 * StringUtils.indexOf("aabaabaa", "b", -1) = 2
	 * StringUtils.indexOf("aabaabaa", "", 2)   = 2
	 * StringUtils.indexOf("abc", "", 9)        = 3
	 * </pre>
	 * 
	 * @param str 源字符串
	 * @param searchStr 搜索目标字符串
	 * @param startPos 搜索起始位置
	 * @return 返回目标字符串子搜索位置及之后第一次出现的索引
	 */
	public static int indexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		return str.indexOf(searchStr, startPos);
	}

	/**
	 * 从源字符串起始位置搜索目标字符在源字符串中最后一次出现的索引
	 * 
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)          = -1
	 * StringUtils.lastIndexOf(*, null)          = -1
	 * StringUtils.lastIndexOf("", "")           = 0
	 * StringUtils.lastIndexOf("aabaabaa", "a")  = 7
	 * StringUtils.lastIndexOf("aabaabaa", "b")  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "ab") = 4
	 * StringUtils.lastIndexOf("aabaabaa", "")   = 8
	 * </pre>
	 * 
	 * @param str 源字符串
	 * @param searchStr 搜索目标字符串
	 * @return 返回最后一次出现的索引
	 */
	public static int lastIndexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		return str.lastIndexOf(searchStr);
	}

	/**
	 * 将字符串strString从aa处截取，并在后面加上......
	 * 
	 * @param strString
	 * @param aa
	 * @return
	 */
	public static String subStrings(String strString, int aa) {
		if (strString == null || strString.equals("") || strString.length() <= 0) {
			return "";
		} else if (strString.length() <= aa) {
			return strString;
		} else {
			strString = strString.substring(0, aa) + "...";
			return strString;
		}
	}

	/**
	 * 从源字符串中指定起始位置截取一直到字符串的结尾，并返回截取的子字符串 避免抛出异常 如果start为正数,则从左向右截取 如果start为负数,则从右向左截取
	 * 
	 * <pre>
	 * StringUtils.substring(null, *)   = null
	 * StringUtils.substring("", *)     = ""
	 * StringUtils.substring("abc", 0)  = "abc"
	 * StringUtils.substring("abc", 2)  = "c"
	 * StringUtils.substring("abc", 4)  = ""
	 * StringUtils.substring("abc", -2) = "bc"
	 * StringUtils.substring("abc", -4) = "abc"
	 * &#064;param str   源字符串
	 * &#064;param start 截取源字符串的起始位置
	 * &#064;return	    返回截取的子字符串     
	 * </pre>
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	/**
	 * <p>
	 * 将object类型的数组对象连接成单个字符串，不添加任何连接符号
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.join(null)            = null
	 * StringUtils.join([])              = ""
	 * StringUtils.join([null])          = ""
	 * StringUtils.join(["a", "b", "c"]) = "abc"
	 * StringUtils.join([null, "", "a"]) = "a"
	 * </pre>
	 * 
	 * @param array
	 * @return
	 */
	public static String join(Object[] array) {
		return join(array, null);
	}

	/**
	 * <p>
	 * 将object类型的数组对象连接成单个字符串,在各个连接的元素之间添加指定的连接符号
	 * 
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 * 
	 * @param array the array of values to join together, may be null
	 * @param separator the separator character to use, null treated as ""
	 * @return the joined String, <code>null</code> if null array input
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, separator, 0, array.length);
	}

	/**
	 * <p>
	 * 将object类型的数组对象连接成单个字符串,在各个连接的元素之间添加指定的连接符号 指定object类型的数组对象连接开始索引,和连接结束索引
	 * 
	 * <pre>
	 * StringUtils.join(null, *)                = null
	 * StringUtils.join([], *)                  = ""
	 * StringUtils.join([null], *)              = ""
	 * StringUtils.join(["a", "b", "c"], "--")  = "a--b--c"
	 * StringUtils.join(["a", "b", "c"], null)  = "abc"
	 * StringUtils.join(["a", "b", "c"], "")    = "abc"
	 * StringUtils.join([null, "", "a"], ',')   = ",,a"
	 * </pre>
	 * 
	 * @param array
	 * @param separator
	 * @param startIndex
	 * @param endIndex
	 * @return the joined String, <code>null</code> if null array input
	 */
	public static String join(Object[] array, String separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}
		int bufSize = (endIndex - startIndex);
		if (bufSize <= 0) {
			return EMPTY;
		}

		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());

		StringBuilder buf = new StringBuilder(bufSize);

		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * 从源字符串中指定起始位置截取指定结束位置,不包括结束位置 并返回截取的子字符串 避免抛出异常
	 * 
	 * <pre>
	 * StringUtils.substring(null, *, *)    = null
	 * StringUtils.substring("", * ,  *)    = "";
	 * StringUtils.substring("abc", 0, 2)   = "ab"
	 * StringUtils.substring("abc", 2, 0)   = ""
	 * StringUtils.substring("abc", 2, 4)   = "c"
	 * StringUtils.substring("abc", 4, 6)   = ""
	 * StringUtils.substring("abc", 2, 2)   = ""
	 * StringUtils.substring("abc", -2, -1) = "b"
	 * StringUtils.substring("abc", -4, 2)  = "ab"
	 * </pre>
	 * 
	 * @param str 源字符串, 可能为 null
	 * @param start 截取源字符串的起始位置
	 * @param end 截取源字符串的结束位置
	 * @return 返回截取的子字符串
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		if (end < 0) {
			end = str.length() + end;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 从源字符串左边开始截取指定长度的子字符串
	 * 
	 * <pre>
	 * StringUtils.left(null, *)    = null
	 * StringUtils.left(*, -ve)     = ""
	 * StringUtils.left("", *)      = ""
	 * StringUtils.left("abc", 0)   = ""
	 * StringUtils.left("abc", 2)   = "ab"
	 * StringUtils.left("abc", 4)   = "abc"
	 * </pre>
	 * 
	 * @param str 源字符串
	 * @param len 截取的长度
	 * @return 返回截取的子字符串
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(0, len);
	}

	/**
	 * 从源字符串右边开始截取指定长度的子字符串
	 * 
	 * <pre>
	 * StringUtils.right(null, *)    = null
	 * StringUtils.right(*, -ve)     = ""
	 * StringUtils.right("", *)      = ""
	 * StringUtils.right("abc", 0)   = ""
	 * StringUtils.right("abc", 2)   = "bc"
	 * StringUtils.right("abc", 4)   = "abc"
	 * </pre>
	 * 
	 * @param str 源字符串
	 * @param len 截取的长度
	 * @return 返回截取的子字符串
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		}
		return str.substring(str.length() - len);
	}

	/**
	 * 从源字符串指定起始位置开始截取指定长度的子字符串
	 * 
	 * <pre>
	 * StringUtils.mid(null, *, *)    = null
	 * StringUtils.mid(*, *, -ve)     = ""
	 * StringUtils.mid("", 0, *)      = ""
	 * StringUtils.mid("abc", 0, 2)   = "ab"
	 * StringUtils.mid("abc", 0, 4)   = "abc"
	 * StringUtils.mid("abc", 2, 4)   = "c"
	 * StringUtils.mid("abc", 4, 2)   = ""
	 * StringUtils.mid("abc", -2, 2)  = "ab"
	 * </pre>
	 * 
	 * @param str 源字符串
	 * @param pos 指定截取的起始位置
	 * @param len 截取的长度
	 * @return the 返回截取的子字符串
	 */
	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0 || pos > str.length()) {
			return EMPTY;
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	/**
	 * 用新字符串替换源字符串中指定的字符串
	 * 
	 * <pre>
	 * StringUtils.replace(null, *, *)        = null
	 * StringUtils.replace("", *, *)          = ""
	 * StringUtils.replace("any", null, *)    = "any"
	 * StringUtils.replace("any", *, null)    = "any"
	 * StringUtils.replace("any", "", *)      = "any"
	 * StringUtils.replace("aba", "a", null)  = "aba"
	 * StringUtils.replace("aba", "a", "")    = "b"
	 * StringUtils.replace("aba", "a", "z")   = "zbz"
	 * </pre>
	 * 
	 * @param text 源字符串
	 * @param searchString 搜索字符串
	 * @param replacement 要替换的字符串
	 * @return 替换后的字符串
	 */
	public static String replace(String text, String searchString, String replacement) {
		return replace(text, searchString, replacement, -1);
	}

	/**
	 * 用新字符串替换源字符串中指定的字符串 替换的最大次数为max StringUtils.replace(null, *, *, *) = null StringUtils.replace("", *, *, *) = ""
	 * StringUtils.replace("any", null, *, *) = "any" StringUtils.replace("any", *, null, *) = "any" StringUtils.replace("any",
	 * "", *, *) = "any" StringUtils.replace("any", *, *, 0) = "any" StringUtils.replace("abaa", "a", null, -1) = "abaa"
	 * StringUtils.replace("abaa", "a", "", -1) = "b" StringUtils.replace("abaa", "a", "z", 0) = "abaa"
	 * StringUtils.replace("abaa", "a", "z", 1) = "zbaa" StringUtils.replace("abaa", "a", "z", 2) = "zbza"
	 * StringUtils.replace("abaa", "a", "z", -1) = "zbzz"
	 * 
	 * </pre>
	 * 
	 * @param text text 源字符串
	 * @param searchString 搜索被替换的字符串
	 * @param replacement 要替换的字符串
	 * @param max 替换的字符串最大次数
	 * @return 返回替换后的字符串 <code>null</code> if null String input
	 */
	public static String replace(String text, String searchString, String replacement, int max) {
		if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(searchString, start);
		if (end == INDEX_NOT_FOUND) {
			return text;
		}
		int replLength = searchString.length();
		int increase = replacement.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuilder buf = new StringBuilder(text.length() + increase);
		while (end != INDEX_NOT_FOUND) {
			buf.append(text.substring(start, end)).append(replacement);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(searchString, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	/**
	 * 比对字符串是否以prefix为开始符,null也可以比较 startsWith("asd","a",true) = true startsWith("asd","A",true) = true
	 * startsWith("asd","a",false) = true startsWith("asd","A",false) = false
	 * 
	 * @param str
	 * @param prefix
	 * @param ignoreCase 是否忽略大小写
	 * @return
	 */
	public static boolean startsWith(String str, String prefix, boolean ignoreCase) {
		if (str == null || prefix == null) {
			return (str == null && prefix == null);
		}
		if (prefix.length() > str.length()) {
			return false;
		}
		return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
	}

	/**
	 * <p>
	 * 判断是否是suffix为结束符
	 * </p>
	 * 
	 * <pre>
	 *  
	 * endsWith("asd","d",true) = true 
	 * endsWith("asd","D",true) =
	 * true endsWith("asd","d",false) = true 
	 * endsWith("asd","d",false) = false
	 * </pre>
	 * 
	 * @param str
	 * @param suffix
	 * @param ignoreCase 是否忽略大小写,true为忽略大小写
	 * @return
	 */
	private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
		if (str == null || suffix == null) {
			return str == null && suffix == null;
		}
		if (suffix.length() > str.length()) {
			return false;
		}
		int strOffset = str.length() - suffix.length();
		return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
	}

	/**
	 * <p>
	 * 返回字符串出现的次数
	 * </p>
	 * 
	 * <pre>
	 * StringHelper.indexOfCont("1.1222.", "22") = 2
	 * </pre>
	 * 
	 * @param str
	 * @param searchStr
	 * @return
	 */
	public static int indexOfCount(String str, String searchStr) {
		if (str == null || str.length() == 0 || searchStr == null || searchStr.equals("")) {
			return 0;
		} else {
			int countStr = 0;
			while (str.indexOf(searchStr) > -1) {
				countStr++;
				str = str.substring(0, str.indexOf(searchStr))
						+ str.substring(str.indexOf(searchStr) + searchStr.length());
			}
			return countStr;
		}

	}

	/**
	 * <p>
	 * 是否可以转化为数字
	 * </p>
	 * 
	 * <pre>
	 * StringHelper.isNum("  ") = false
	 * StringHelper.isNum(null) = false;
	 * StringHelper.isNum("")   = false;
	 * StringHelper.isNum("  1.2") = false;
	 * StringHelper.isNum("1.2  ") = false;
	 * StringHelper.isNum(" 1.2 ") = false;
	 * StringHelper.isNum("1.2") = true;
	 * StringHelper.isNum("0.123") = true;
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		if (isBlank(str)) {
			return false;
		}
		if (str.startsWith(".") || str.endsWith(".") || indexOfCount(str, ".") > 1) {
			return false;
		} else {
			str = replace(str, ".", "");
			int sz = str.length();
			for (int i = 0; i < sz; i++) {
				if (Character.isDigit(str.charAt(i)) == false) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 是否为java类型的数字，如123L,
	 * </p>
	 * 
	 * <pre>
	 * StringHelper.isNumber("  ") = false
	 * StringHelper.isNumber(null) = false;
	 * StringHelper.isNumber("")   = false;
	 * StringHelper.isNumber("  1.2") = false;
	 * StringHelper.isNumber("1.2  ") = false;
	 * StringHelper.isNumber(" 1.2 ") = false;
	 * StringHelper.isNumber("1.2") = true;
	 * StringHelper.isNumber("0.123") = true;
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		if (isBlank(str)) {
			return false;
		}
		char[] chars = str.toCharArray();
		int sz = chars.length;
		boolean hasExp = false;
		boolean hasDecPoint = false;
		boolean allowSigns = false;
		boolean foundDigit = false;
		int start = (chars[0] == '-') ? 1 : 0;
		if (sz > start + 1) {
			if (chars[start] == '0' && chars[start + 1] == 'x') {
				int i = start + 2;
				if (i == sz) {
					return false;
				}
				for (; i < chars.length; i++) {
					if ((chars[i] < '0' || chars[i] > '9') && (chars[i] < 'a' || chars[i] > 'f')
							&& (chars[i] < 'A' || chars[i] > 'F')) {
						return false;
					}
				}
				return true;
			}
		}
		sz--;
		int i = start;

		while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
			if (chars[i] >= '0' && chars[i] <= '9') {
				foundDigit = true;
				allowSigns = false;

			} else if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {

					return false;
				}
				hasDecPoint = true;
			} else if (chars[i] == 'e' || chars[i] == 'E') {

				if (hasExp) {

					return false;
				}
				if (!foundDigit) {
					return false;
				}
				hasExp = true;
				allowSigns = true;
			} else if (chars[i] == '+' || chars[i] == '-') {
				if (!allowSigns) {
					return false;
				}
				allowSigns = false;
				foundDigit = false;
			} else {
				return false;
			}
			i++;
		}
		if (i < chars.length) {
			if (chars[i] >= '0' && chars[i] <= '9') {

				return true;
			}
			if (chars[i] == 'e' || chars[i] == 'E') {

				return false;
			}
			if (chars[i] == '.') {
				if (hasDecPoint || hasExp) {

					return false;
				}

				return foundDigit;
			}
			if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F')) {
				return foundDigit;
			}
			if (chars[i] == 'l' || chars[i] == 'L') {

				return foundDigit && !hasExp;
			}

			return false;
		}
		return !allowSigns && foundDigit;
	}

	/**
	 * <p>
	 * 是否可以转化为整数
	 * </p>
	 * 
	 * <pre>
	 * StringHelper.isNumber("  ") = false
	 * StringHelper.isNumber(null) = false;
	 * StringHelper.isNumber("")   = false;
	 * StringHelper.isNumber("  1.2") = false;
	 * StringHelper.isNumber("1.2  ") = false;
	 * StringHelper.isNumber(" 1.2 ") = false;
	 * StringHelper.isNumber("1.2") = true;
	 * StringHelper.isNumber("0.123") = true;
	 * </pre>
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (isBlank(str)) {
			return false;
		}
		int sz = str.length();
		if (str.startsWith("'") && str.endsWith("'")) {
			str = str.substring(0, str.length());
			str = str.substring(1);
		}
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 四舍五入，可设置小数位，和四舍五入失败返回的默认字符串
	 * 
	 * @param numberStr 需要处理的对象
	 * @param fraction 需要保留的小数位
	 * @param defaultValue 默认字符串，转换失败返回的字符串
	 * @return
	 */
	public static String object45(Object numberStr, int fraction, String defaultValue) {

		if (!StringHelper.isNum(numberStr.toString()) || fraction < 0) {
			return defaultValue;
		} else {
			java.math.BigDecimal bd = new java.math.BigDecimal(numberStr.toString());
			bd = bd.setScale(fraction, java.math.BigDecimal.ROUND_HALF_UP);
			String returnNumber = bd.doubleValue() + "";
			if (fraction == 0) {
				returnNumber = returnNumber.replaceAll(".0", "");
			}
			return returnNumber;

		}
	}

	/**
	 * 重复字符多次
	 * 
	 * @param repeat 重复次数
	 * @param padChar 需要重复的字符
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
		if (repeat < 0) {
			throw new IndexOutOfBoundsException("重复次数必须大于0,给定重复次数为：" + repeat);
		}
		final char[] buf = new char[repeat];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = padChar;
		}
		return new String(buf);
	}

	/**
	 * 右补齐字符串，用字符填补
	 * 
	 * @param str 需要补齐的字符，如果为空就返回空，字符长度大于需要的补齐的字符长度返回原字符串
	 * @param size 所需字符长度
	 * @param padStr 补齐字符
	 * @return
	 */
	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str;
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	/**
	 * 右补齐字符串，字符串为null返回null rightPad("12",5,"ab")=12aba
	 * 
	 * @param str 操作字符串
	 * @param size 所需字符长度
	 * @param padStr 补齐字符
	 * @return
	 */
	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	/**
	 * 去除开始字符，如starer字符串去除开始字符串st 返回arer,如果字符串味空则返回NULL
	 * 
	 * @param str 操作字符串
	 * @param wipeString 消除字符串
	 * @param ignoreCase 是否忽略大小写
	 * @return
	 */
	public static String rightWipe(String str, String wipeString, boolean ignoreCase) {
		if (str == null) {
			return null;
		}
		int pads = wipeString.length();
		return endsWith(str, wipeString, ignoreCase) ? str.substring(0, str.length() - pads) : str;
	}

	/**
	 * 左补齐字符串
	 * 
	 * @param str 操作字符串
	 * @param size 所需字符长度
	 * @param padChar 补齐字符
	 * @return
	 */
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	/**
	 * <p>
	 * 左补齐字符串
	 * </p>
	 * 
	 * @param str 操作字符串
	 * @param size 所需字符长度
	 * @param padStr 补齐字符串
	 * @return
	 */
	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	/**
	 * 去除开始字符，如starer字符串去除开始字符串st 返回arer,如果字符串味空则返回NULL
	 * 
	 * @param str 操作字符串
	 * @param wipeString 消除字符串
	 * @param ignoreCase 是否忽略大小写
	 * @return
	 */
	public static String leftWipe(String str, String wipeString, boolean ignoreCase) {
		if (str == null) {
			return null;
		}
		int pads = wipeString.length();
		return startsWith(str, wipeString, ignoreCase) ? str.substring(pads) : str;
	}

	/**
	 * <p>
	 * 字符串str从aa处截取，并在后面加上指定的字符
	 * </p>
	 * 
	 * @param padStr 需添加的字符串
	 * @param str 源字符串
	 * @param len 截取的长度
	 * @param defaultStr str 为null or "" or " "时返回的默认值
	 * @return
	 */
	public static String subStringRightPad(String str, int len, String padStr, String defaultStr) {
		if (isEmpty(str)) {
			return defaultStr;
		} else if (str.length() <= len) {
			return str;
		} else {
			if (padStr == null) {
				padStr = "";
			}
			str = left(str, len) + padStr;
			return str;
		}
	}

	/**
	 * 字符串字符顺序颠倒如 "abc" 颠倒为 "cba"
	 * 
	 * @param str
	 * @return
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuilder(str).reverse().toString();
	}

	/**
	 * 是否全部为大写字符
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isAllUpperCase(String str) {
		if (isEmpty(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isUpperCase(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否全部为小写字符
	 * 
	 * @param cs
	 * @return
	 */
	public static boolean isAllLowerCase(String str) {
		if (isEmpty(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLowerCase(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 返回年-月-日 时:分:秒字符串
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.dateTimeSubString("2010-10-10 12:12:12.0") = "2010-10-10 12:12:12"
	 * StringUtils.dateTimeSubString(null) = defaultStr
	 * </pre>
	 * 
	 * @param str 需处理的日期字符
	 * @parame defaultStr 返回默认的字符串
	 */
	public static String dateTimeSubString(String str, String defaultStr) {
		if (str == null) {
			return defaultStr;
		} else {
			return left(str, 19);
		}

	}

	/**
	 * <p>
	 * 返回年-月-日 时:分字符串
	 * </p>
	 * 
	 * <pre>
	 * StringUtils.dateMinuteSubString("2010-10-10 12:12:12.0") = "2010-10-10 12:12"
	 * StringUtils.dateMinuteSubString(null) = defaultStr
	 * </pre>
	 * 
	 * @param str 需处理的日期字符
	 * @parame defaultStr 返回默认的字符串
	 * @return
	 */
	public static String dateMinuteSubString(String str, String defaultStr) {
		if (str == null) {
			return defaultStr;
		} else {
			return left(str, 16);
		}
	}

	/**
	 * 返回年-月-日 小时字符串
	 * 
	 * <pre>
	 * StringUtils.dateHourSubString("2010-10-10 12:12:12.0") = "2010-10-10 12"
	 * StringUtils.dateHourSubString(null) = defaultStr
	 * </pre>
	 * 
	 * @param str 需处理的日期字符
	 * @parame defaultStr 返回默认的字符串
	 * @return
	 */
	public static String dateHourSubString(String str, String defaultStr) {
		if (str == null) {
			return defaultStr;
		} else {
			return left(str, 13);
		}
	}

	/**
	 * 返回年-月-日字符串
	 * 
	 * <pre>
	 * StringUtils.dateSubString("2010-10-10 12:12:12.0") = "2010-10-10"
	 * StringUtils.dateSubString(null) = defaultStr
	 * </pre>
	 * 
	 * @param str 需处理的日期字符
	 * @parame defaultStr 返回默认的字符串
	 * @return
	 */
	public static String dateSubString(String str, String defaultStr) {
		if (str == null) {
			return defaultStr;
		} else {
			return left(str, 10);
		}

	}

	/**
	 * 返回年-月字符串
	 * 
	 * <pre>
	 * StringUtils.dateMonthSubString("2010-10-10 12:12:12.0") = "2010-10"
	 * StringUtils.dateMonthSubString(null) = defaultStr
	 * </pre>
	 * 
	 * @param str 需处理的日期字符
	 * @parame defaultStr 返回默认的字符串
	 * @return
	 */
	public static String dateMonthSubString(String str, String defaultStr) {
		if (str == null) {
			return defaultStr;
		} else {
			return left(str, 7);
		}

	}

	/**
	 * 返回年字符串
	 * 
	 * <pre>
	 * StringUtils.dateYearSubString("2010-10-10 12:12:12.0") = "2010"
	 * StringUtils.dateYearSubString(null) = defaultStr
	 * </pre>
	 * 
	 * @param str 需处理的日期字符
	 * @parame defaultStr 返回默认的字符串
	 * @return
	 */
	public static String dateYearSubString(String str, String defaultStr) {
		if (str == null) {
			return defaultStr;
		} else {
			return left(str, 4);
		}

	}

	/**
	 * <p>
	 * filterHTML: 显示页面时,须将如下字符过滤: &quot; => " , > => &gt; , < => &lt; '\n' => <br> , '\r' => <br>
	 * 
	 * @param strHTML input html string.
	 * @return String 返回格式化后的字符串。
	 */
	public static String filterHTML(String strHTML) {
		if (strHTML == null) {
			return "";
		}
		StringBuffer sbResult = new StringBuffer();
		int nLen = strHTML.length();
		char chCur;
		for (int i = 0; i < nLen; i++) {
			chCur = strHTML.charAt(i);
			switch (chCur) {
			case '\"':
				sbResult.append("&quot;");
				break;
			case '>':
				sbResult.append("&gt;");
				break;
			case '<':
				sbResult.append("&lt;");
				break;
			case '\r':
			case '\n':
				sbResult.append("<br>");
				break;
			case ' ':
				sbResult.append("&nbsp;");
				break;
			default:
				sbResult.append(chCur);
				break;
			}
		}
		return sbResult.toString();
	}

	/**
	 * url 特殊字符处理
	 * 
	 * @param strString
	 * @return
	 */
	public static String filterToUrl(String strUrl) {
		strUrl = replace(strUrl, "%", "%25");
		strUrl = replace(strUrl, "#", "%23");
		strUrl = replace(strUrl, "?", "%3F");
		strUrl = replace(strUrl, "/", "%2F");
		strUrl = replace(strUrl, "=", "%3D");
		strUrl = replace(strUrl, ",", "%2C");
		strUrl = replace(strUrl, ";", "%3B");
		strUrl = replace(strUrl, "&", "%26");
		strUrl = replace(strUrl, " ", "%20");
		strUrl = replace(strUrl, "<", "%3C");
		strUrl = replace(strUrl, ">", "%3E");
		strUrl = replace(strUrl, "'", "%27");
		return strUrl;
	}

	/**
	 * url 特殊字符处理
	 * 
	 * @param strString
	 * @return
	 */
	public static String filterToUrlUtf8(String strUrl) {
		String returnValue = "";
		try {
			returnValue = URLEncoder.encode(filterToUrl(strUrl), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	public static String filterForJs(String strString) {
		strString = replaceString(strString, "\"", "\"\"");
		strString = replaceString(strString, "\\", "\\\\");
		strString = replaceString(strString, "'", "\\'");
		strString = replaceString(strString, "\n", "  ");
		return strString;
	}

	/**
	 * url 特殊字符处理
	 * 
	 * @param strString
	 * @return
	 */
	public static String filterToUtf8(String strUrl) {
		String returnValue = "";
		try {
			returnValue = URLEncoder.encode(strUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * <p>
	 * 将一个字符串strRep中所有的子串strText用另一个子串strKey来代替。
	 * </p>
	 * <p>
	 * 用法示例 ： 将字符串"aababbabaa"中所有的"aa"用"CC"来代替，那么使用 replaceString("aababbabaa", "aa", "CC")即可得到结果。
	 * 注意，从JDK1.4开始就提供了类似的方法，所以该方法适合在JDK1.3及其以前的JDK版本中使用。
	 * </p>
	 * 
	 * @param strRep 原始字符串对象
	 * @param strKey 用来代替的字符串
	 * @param strText 将被代替的字符串
	 * @return 返回被替换后的结果字符串对象
	 */
	public static String replaceString(String strRep, String strKey, String strText) {
		StringBuffer objBuffer = new StringBuffer(strRep);
		int index = (objBuffer.toString()).indexOf(strKey);

		while (index > -1) {
			objBuffer.replace(index, index + strKey.length(), strText);
			index = (objBuffer.toString()).indexOf(strKey, index + strText.length());
		}
		return objBuffer.toString();
	}

	/**
	 * 判断是否可以转换为整数 1.0 true '1.0' true 1.1 false
	 * 
	 * @param str
	 * @return
	 */
	public static boolean canParseInt(String str) {
		if (str.startsWith("'") && str.endsWith("'")) {
			str = str.substring(0, str.length() - 1);
			str = str.substring(1);
		}
		if (isNumber(str) && Integer.parseInt(object45(str, 0, "erro")) == Float.parseFloat(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否可以转换为整数 1.0 true '1.0' true 1.1 false
	 * 
	 * @param str
	 * @return
	 */
	public static int parseToInt(String str) {
		if (str.startsWith("'") && str.endsWith("'")) {
			str = str.substring(0, str.length() - 1);
			str = str.substring(1);
		}
		if (isNumber(str) && Integer.parseInt(object45(str, 0, "erro")) == Float.parseFloat(str)) {
			return Integer.parseInt(object45(str, 0, "-99999"));
		} else {
			return -9999;
		}
	}

	public static void main(String[] args) {
		System.out.println(Float.parseFloat("1.6f"));
		System.out.println(object45("1.355", 2, "0"));
		System.out.println(leftWipe("sdfs", "Sd", false));

		System.out.println(dateSubString("2012-03-09 21:18:17.0", ""));
		System.out.println(replaceString("sdf*dfd", "*", "%"));
		System.out.println(filterToUtf8("系"));
	}
}
