package com.cp.epa.exception;

import java.io.PrintStream;

/**
 * 系统异常
 * 类名：SystemException  
 * 功能：
 * @author dzl 
 * 创建时间：May 23, 2013 8:49:53 AM 
 * @version May 23, 2013
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = -1378076813308977834L;
	
	private Throwable cause;

	public SystemException(String msg) {
		super(msg);
	}

	public SystemException(String msg, Throwable ex) {
		super(msg);
		this.cause = ex;
	}

	public Throwable getCause() {
		return (this.cause == null ? this : this.cause);
	}

	public String getMessage(){   
		String message = super.getMessage();   
		return message;   
	}
	
	public void printStackTrace(PrintStream ps) {
		if (getCause() == null) {
			super.printStackTrace(ps);

		} else {
			ps.println(this);
			getCause().printStackTrace(ps);
		}
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}
}