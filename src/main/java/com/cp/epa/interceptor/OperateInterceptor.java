package com.cp.epa.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cp.epa.log.entity.OperateLog;
import com.cp.epa.log.services.IOperateLogService;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.sysconfig.utils.SysConfigContext;
import com.cp.epa.utils.PapUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登录日志拦截器
 * 类名：OperateInterceptor  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：May 17, 2013 9:20:59 AM 
 * @version May 17, 2013
 */
public class OperateInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	@Resource
	private IOperateLogService operateLogServiceImpl;
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		/*
		 * 执行拦截器
		 */
		String result = invocation.invoke();
		
		// 开关
		String status = SysConfigContext.getSwitch("operateLog");
		if(status != null && "N".equals(status))
			return result;
		
		Users u = (Users)invocation.getInvocationContext().getSession().get("CURRENTUSER");
		if(null != u){

			//业务对象
			String actionName = invocation.getAction().getClass().getSimpleName();
			if(actionName.contains("Action")){
				actionName = actionName.substring(0,actionName.indexOf("Action"));	
			}
			//业务方法
			String methodName = invocation.getProxy().getMethod();
//			String _methodName="";
//			if(methodName.length()>4)
//				_methodName = methodName.substring(0,4);
			
			//System.out.println("---methodName---:"+methodName);
			
			String ip = request.getRemoteAddr();
			String loginName = u.getUserCode();
			if(!"load".equals(methodName.substring(0,4))){
				OperateLog log = new OperateLog();
				
				log.setIp(ip);
				log.setOperateDate(PapUtil.date(new Date()));
				log.setOperateModel(actionName);
				log.setOperateType(methodName);
				log.setOperateUserCode(loginName);
				
				operateLogServiceImpl.save(log);
			}
			
		}
		return result;
	}
}
