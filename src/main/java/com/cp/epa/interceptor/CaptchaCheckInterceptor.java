package com.cp.epa.interceptor;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.dispatcher.Parameter;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
@SuppressWarnings("serial")
public class CaptchaCheckInterceptor extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
		// TODO Auto-generated method stub
		//System.out.println("--------------------------checkcaptcha-------拦截器执行---------------------------------");
		//Map<String, Object> session = invocation.getInvocationContext().getSession();
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		HttpSession session = request.getSession(true);
		
		invocation.getInvocationContext().getParameters();
		
		Map<String, Parameter> _m = invocation.getInvocationContext().getParameters();
		
		Parameter p = _m.get("patchca");
		
		String patchca = p.getValue();
		//String patchca = _m.get("patchca");
		String sessionPatchca = (String)session.getAttribute("PATCHCA");
		
		//System.out.println("===========gg=======:"+sessionPatchca);
		
		if ((sessionPatchca == null)|| ("".equals(sessionPatchca))||(patchca == null)||("".equals(patchca))||(!sessionPatchca.equalsIgnoreCase(patchca))){	
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=UTF-8"); 
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			PrintWriter out = response.getWriter();
			out.append("CAPTCHAERROR");
			out.flush();
			out.close();
			return null;
		}
		//System.out.println("--------------------------checkcaptcha------拦截器执行完---------------------------------");
		return invocation.invoke();
	}
}
