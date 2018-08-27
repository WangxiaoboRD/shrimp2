package com.cp.epa.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * 
 * 类名：ExceptionPreResultInterceptor  
 *
 * 功能：
 * @deprecated
 * @author dzl 
 * 创建时间：Jun 29, 2013 6:03:22 PM 
 * @version Jun 29, 2013
 */
public class ExceptionPreResultInterceptor implements Interceptor{

	public void destroy() {
		// TODO Auto-generated method stub
	}
	public void init() {
		// TODO Auto-generated method stub
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		ExceptionHandler handler = new ExceptionHandler(invocation);
		return handler.invok();
	}

}
