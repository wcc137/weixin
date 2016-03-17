/**
 *@公司：          前景科技
 *@系统名称：changing_framework
 *@文件名称：NumberHelper.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2010-10-29 上午10:04:04
 *@完成时间：2010-10-29 上午10:04:04
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.helper;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author watermelon <p> 功能描述: <p> 使用示例： <p>
 */
public class NumberHelper {
	/**
	 * 四舍五入
	 * 
	 * @param value
	 *        需要操作的对象
	 * @param fraction
	 *        小数位
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public static double roundValue(Object value, int fraction, double defaultValue) {
		if (StringHelper.isNum(value.toString())) {
			double valueTemp = toDouble(value.toString(), defaultValue);
			DecimalFormat df = new DecimalFormat("##.00");
			df.setMaximumFractionDigits(fraction);
			df.setMinimumFractionDigits(fraction);
			return Double.parseDouble(df.format(valueTemp));
		} else {
			return defaultValue;
		}

	}

	/**
	 * 四舍五入0.455四舍五入到5位小数，则返回值为0.455，区别于 <p> roundValue(String value, int fraction, String defaultValue)
	 * 
	 * @param value
	 *        需要操作的对象
	 * @param fraction
	 *        小数位
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public static float roundValue(Object value, int fraction, float defaultValue) {
		if (StringHelper.isNum(value.toString())) {
			float valueTemp = toFloat(value.toString(), defaultValue);
			DecimalFormat df = new DecimalFormat("##.00");
			df.setMaximumFractionDigits(fraction);
			df.setMinimumFractionDigits(fraction);
			return Float.parseFloat(df.format(valueTemp));
		} else {
			return defaultValue;
		}

	}

	/**
	 * 四舍五入,0.455四舍五入到5位小数，则返回值为0.45500，自动在返回值后补齐
	 * 
	 * @param value
	 *        需要操作的对象
	 * @param fraction
	 *        小数位
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public static String roundValue(String value, int fraction, String defaultValue) {
		if (StringHelper.isNum(value)) {
			BigDecimal bigDecimal = new BigDecimal(value).setScale(fraction, BigDecimal.ROUND_HALF_UP);
			return bigDecimal.toString();
		} else {
			return defaultValue;
		}

	}

	/**
	 * 获取数组中最小值
	 * 
	 * @param array
	 * @return
	 */
	public static long min(long[] array) {
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}
		long min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] < min) {
				min = array[i];
			}
		}

		return min;
	}

	/**
	 * 获取数组中最小值
	 * 
	 * @param array
	 * @return
	 */
	public static int min(int[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns min
		int min = array[0];
		for (int j = 1; j < array.length; j++) {
			if (array[j] < min) {
				min = array[j];
			}
		}

		return min;
	}

	/**
	 * 获取数组中最小值
	 * 
	 * @param array
	 * @return
	 */
	public static short min(short[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns min
		short min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] < min) {
				min = array[i];
			}
		}
		return min;
	}

	/**
	 * 获取数组中最小值
	 * 
	 * @param array
	 * @return
	 */
	public static byte min(byte[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns min
		byte min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] < min) {
				min = array[i];
			}
		}

		return min;
	}

	/**
	 * 获取数组中最小值
	 * 
	 * @param array
	 * @return
	 */
	public static double min(double[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns min
		double min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (Double.isNaN(array[i])) {
				return Double.NaN;
			}
			if (array[i] < min) {
				min = array[i];
			}
		}

		return min;
	}

	/**
	 * 获取数组中最小值
	 * 
	 * @param array
	 * @return
	 */
	public static float min(float[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns min
		float min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (Float.isNaN(array[i])) {
				return Float.NaN;
			}
			if (array[i] < min) {
				min = array[i];
			}
		}

		return min;
	}

	/**
	 * 获取数组中最大值
	 * 
	 * @param array
	 * @return
	 */
	public static long max(long[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns max
		long max = array[0];
		for (int j = 1; j < array.length; j++) {
			if (array[j] > max) {
				max = array[j];
			}
		}

		return max;
	}

	/**
	 * 获取数组中最大值
	 * 
	 * @param array
	 * @return
	 */
	public static int max(int[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns max
		int max = array[0];
		for (int j = 1; j < array.length; j++) {
			if (array[j] > max) {
				max = array[j];
			}
		}

		return max;
	}

	/**
	 * 获取数组中最大值
	 * 
	 * @param array
	 * @return
	 */
	public static short max(short[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns max
		short max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}

		return max;
	}

	/**
	 * 获取数组中最大值
	 * 
	 * @param array
	 * @return
	 */
	public static byte max(byte[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns max
		byte max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
		}

		return max;
	}

	/**
	 * 获取数组中最大值
	 * 
	 * @param array
	 * @return
	 */
	public static double max(double[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns max
		double max = array[0];
		for (int j = 1; j < array.length; j++) {
			if (Double.isNaN(array[j])) {
				return Double.NaN;
			}
			if (array[j] > max) {
				max = array[j];
			}
		}

		return max;
	}

	/**
	 * 获取数组中最大值
	 * 
	 * @param array
	 * @return
	 */
	public static float max(float[] array) {
		// Validates input
		if (array == null) {
			throw new IllegalArgumentException(" Array 不能为 null");
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Array 长度不能为0");
		}

		// Finds and returns max
		float max = array[0];
		for (int j = 1; j < array.length; j++) {
			if (Float.isNaN(array[j])) {
				return Float.NaN;
			}
			if (array[j] > max) {
				max = array[j];
			}
		}

		return max;
	}

	/**
	 * 字符串转换为整形
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(String str, int defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 对象转换为double,如果不能转化则返回 默认字段defaultValue
	 * 
	 * @param obj
	 *        需转换的对象
	 * @param defaultValue
	 *        转换失败返回值
	 * @return
	 */
	public static double toDouble(Object obj, double defaultValue) {
		try {
			return new Double(obj.toString()).doubleValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 对象转换为double,如果不能转化则返回 默认字段defaultValue
	 * 
	 * @param obj
	 *        需转换的对象
	 * @param defaultValue
	 *        转换失败返回值
	 * @return
	 */
	public static long toLong(String str, long defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Long.parseLong(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 对象转换为float,如果不能转化则返回 默认字段defaultValue
	 * 
	 * @param obj
	 *        需转换的对象
	 * @param defaultValue
	 *        转换失败返回值
	 * @return
	 */
	public static float toFloat(String str, float defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 对象转换为byte,如果不能转化则返回 默认字段defaultValue
	 * 
	 * @param obj
	 *        需转换的对象
	 * @param defaultValue
	 *        转换失败返回值
	 * @return
	 */
	public static byte toByte(String str, byte defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Byte.parseByte(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 对象转换为short,如果不能转化则返回 默认字段defaultValue
	 * 
	 * @param obj
	 *        需转换的对象
	 * @param defaultValue
	 *        转换失败返回值
	 * @return
	 */
	public static short toShort(String str, short defaultValue) {
		if (str == null) {
			return defaultValue;
		}
		try {
			return Short.parseShort(str);
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	/**
	 * 对象格式化，按照给定的格式 "##.00%" numberFormat("0.1", "##0.00%","1") = 10.00% numberFormat("0.1", "##.00","1") = .10
	 * numberFormat("0.1", "##0.00","1") = 0.1
	 * 
	 * @param numberTyple
	 */
	public static String numberFormat(Object object, String numberTyple, String defaultValue) {
		if (StringHelper.isNum(object.toString())) {
			DecimalFormat df1 = new DecimalFormat(numberTyple);
			return df1.format(Double.parseDouble(object.toString()));
		} else {
			return defaultValue;
		}

	}

	/**
	 * 两个数相加,可选择是否精确小数位，小数位小于0则返回默认值
	 * 
	 * @param v1
	 * @param v2
	 * @param isRound
	 *        是否精确小数位
	 * @param fraction
	 *        需要精确到的小数位
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public static double add(double v1, double v2, boolean isRound, int fraction, double defaultValue) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		if (isRound) {
			if (fraction > -1) {
				return roundValue(b1.add(b2), fraction, defaultValue);
			} else {
				return defaultValue;
			}
		} else {
			return b1.add(b2).doubleValue();
		}
	}

	/**
	 * 减法运算,可选择是否精确小数位，小数位小于0则返回默认值
	 * 
	 * @param v1
	 * @param v2
	 * @param isRound
	 *        是否精确小数位
	 * @param fraction
	 *        需要精确到的小数位
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public static double sub(double v1, double v2, boolean isRound, int fraction, double defaultValue) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		if (isRound) {
			if (fraction > -1) {
				return roundValue(b1.subtract(b2), fraction, defaultValue);
			} else {
				return defaultValue;
			}
		} else {
			return b1.subtract(b2).doubleValue();
		}
	}

	/**
	 * 乘法运算，可选择是否精确小数位，小数位小于0则返回默认值
	 * 
	 * @param v1
	 * @param v2
	 * @param isRound
	 *        是否精确小数位
	 * @param fraction
	 *        需要精确到的小数位
	 * @param defaultValue
	 *        默认值
	 * @return
	 */
	public static double mul(double v1, double v2, boolean isRound, int fraction, double defaultValue) {

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		if (isRound) {
			if (fraction > -1) {
				return roundValue(b1.multiply(b2), fraction, defaultValue);
			} else {
				return defaultValue;
			}
		} else {
			return b1.multiply(b2).doubleValue();
		}
	}

	/**
	 * 两个数相除，可选择是否精确小数位，小数位小于0则返回默认值
	 * 
	 * @param v1
	 * @param v2
	 * @param fraction
	 * @return
	 */
	public static double div(double v1, double v2, int fraction, double defaultValue) {

		if (fraction < 0) {

			return defaultValue;

		} else {
			BigDecimal b1 = new BigDecimal(Double.toString(v1));

			BigDecimal b2 = new BigDecimal(Double.toString(v2));

			return b1.divide(b2, fraction, BigDecimal.ROUND_HALF_UP).doubleValue();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println(NumberHelper.roundValue(0, 2, 1));
/*
		System.out.println(numberFormat("0.1", "##0.00%", "1"));
		System.out.println(numberFormat("011.1", "##.00", "1"));
		System.out.println(numberFormat("0.10", "##.00", "1"));
		System.out.println(roundValue("0.155", 2, 1));

		DecimalFormat df = new DecimalFormat("##.00");
		df.setMaximumFractionDigits(4);
		df.setMinimumFractionDigits(2);
		System.out.println(div(0.155, 3, 1, -1));
		System.out.println(roundValue("0.12", 5, "12"));*/
		
		System.out.println(numberFormat("123456789.2563", "#,##0.00","0"));
	}
}
