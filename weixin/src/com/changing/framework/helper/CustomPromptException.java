
package com.changing.framework.helper;
/**
 * 自定义提示信息异常
 */
public class CustomPromptException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public CustomPromptException(String message) {
		super(message);
	}
}
