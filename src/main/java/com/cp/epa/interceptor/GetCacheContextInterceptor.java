package com.cp.epa.interceptor;

import java.util.Map;

import com.cp.epa.utils.SysContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
@SuppressWarnings("serial")
public class GetCacheContextInterceptor extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("-------------------------------threadlocal--拦截器执行---------------------------------");
		//System.out.println("0000:"+Thread.currentThread().getName());
		 //HttpServletRequest request = (HttpServletRequest) ServletActionContext.getRequest();  
		// HttpSession session = (HttpSession) request.getSession(); 
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		if(session != null)
			SysContext.set(session);
		String ret = invocation.invoke();
		SysContext.romove();
		//System.out.println("--------------------------------threadlocal--拦截器执行完---------------------------------");
		return ret;
	}
}
