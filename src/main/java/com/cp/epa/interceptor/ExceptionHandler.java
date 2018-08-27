package com.cp.epa.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * 
 * 类名：ExceptionHandler  
 *
 * 功能：
 * @deprecated
 * @author dzl 
 * 创建时间：Jun 29, 2013 6:03:00 PM 
 * @version Jun 29, 2013
 */
public class ExceptionHandler implements PreResultListener {

	private ActionInvocation invocation;
	private boolean beforeResult=true;
	private String result = Action.ERROR;
	
	public ExceptionHandler(ActionInvocation invocation){
		this.invocation = invocation;
		invocation.addPreResultListener(this);
	}
	
	String invok(){
		try {
			result = invocation.invoke();
		} catch (Exception e) {
			if(beforeResult){
				return Action.ERROR;
			}else{
				return result;
			}
		}
		return result;
	}
	
	public void beforeResult(ActionInvocation invocation, String resultCode) {
		// TODO Auto-generated method stub
		beforeResult = false;
		result = resultCode;
	}

}
