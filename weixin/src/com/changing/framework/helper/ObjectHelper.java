/**
 *@公司：          前景科技
 *@系统名称：changing_framework
 *@文件名称：ObjectHelper.java
 *@功能描述:
 *@创建人  ：zn
 *@创建时间: 2010-10-29 下午07:44:23
 *@完成时间：2010-10-29 下午07:44:23
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.framework.helper;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author watermelon
 *         <p>
 *         功能描述:
 *         <p>
 *         使用示例：
 *         <p>
 */
public class ObjectHelper {

	/**
	 * 对HashMap按照key值大小从小到大排序,如果key为数字
	 * 
	 * @param map
	 * @return
	 */
	public static HashMap orderMapByKey(HashMap map) {
		Object list[] = map.keySet().toArray();
		Arrays.sort(list);
		HashMap returnMap = new HashMap();
		for (int i = 0; i < list.length; i++) {
			returnMap.put(list[i], map.get(list[i]));
		}
		return returnMap;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
