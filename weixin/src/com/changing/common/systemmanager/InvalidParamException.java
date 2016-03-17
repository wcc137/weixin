package com.changing.common.systemmanager;

public class InvalidParamException extends Exception {
	private static final long serialVersionUID = 1L;
	public InvalidParamException() {
        super();
    }
    
    public InvalidParamException(String msg) {
        super(msg);
    }
    
    public InvalidParamException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
    public InvalidParamException(Throwable cause) {
        super(cause);
    }

}
