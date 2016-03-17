/**
 *@公司：    前景科技
 *@系统名称：changing
 *@文件名称：Password.java
 *@功能描述:
 *@创建人  ：
 *@创建时间: 
 *@完成时间：
 *@修该人：
 *@修改内容：
 *@修改日期：
 */
package com.changing.common.systemmanager;
public class Password {
	/**
	 * 判断给定的两个密码值是否一致
	 * @param pwd 要校验的没有处理过的原始密码
	 * @param pwd2 要核对的密码,可能已经经过了加密处理
	 * @return 密码一致则返回true,否则返回false
	 */
	public static boolean check(String pwd, String pwd2) {
		if (encrypt(pwd).equals(pwd2) || pwd.equals(pwd2)) // 非加密密码为代理用
			return true;
		return false;
	}

	/**
	 * 对密码采用md5方式进行加密处理
	 * @param pwd 要进行加密处理的密码
	 * @return 加密处理后的密码
	 */
	public static String getMd5(String pwd) {
		return new MD5().getMD5ofStr(pwd);
	}

	/**
	 * 对密码进行加密处理，可能是用的md5方式，或者其他方式
	 * @param pwd 要进行加密处理的密码
	 * @return 加密处理后的密码
	 */
	public static String encrypt(String pwd) {
		return new MD5().getMD5ofStr(pwd);
	}
	/**
	 * 检验给定的密码是否符合给定的条件
	 * @param pwd 要校验的密码
	 * @param zgdm 用户代码
	 * @return 如果符合条件，返回空字符串，否则，返回错误信息
	 */
	public static String checkout(String pwd, String zgdm) {
		int len = pwd.length();
		if (len < 6)
			return "您的密码长度不能少于6位长度，请重新输入!";
		if (len > 16)
			return "您的密码长度不能大于16位长度，请重新输入!";
		if (pwd.equals(zgdm))
			return "您的密码和你的登录账户相同，这是不允许的，请重新输入!";

		int count = 0;
		if (pwd.matches("[^\\p{Upper}]*[\\p{Upper}]+.*"))
			count++; // 检验大写字符是否存在一次或一次以上
		if (pwd.matches("[^\\p{Lower}]*[\\p{Lower}]+.*"))
			count++; // 检验小写字符是否存在一次或一次以上
		if (pwd.matches("\\D*\\d+.*"))
			count++; // 检验数字字符是否存在一次或一次以上
		if (pwd.matches("\\w*[\\W|_]+.*"))
			count++; // 检验非单词字符(如!@#$%等)是否存在一次或一次以上
		if (count < 3) {
			return "您的密码输入格式不正确，\\n至少应包含英文大写字母、英文小写字母、10个基本数字以及非字母字符(如“$#”等)四种字符中的至少三种。";
		}

		return "";
	}
}
