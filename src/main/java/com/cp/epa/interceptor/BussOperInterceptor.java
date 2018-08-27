package com.cp.epa.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cp.epa.log.entity.BussOperateLog;
import com.cp.epa.log.entity.LogMethod;
import com.cp.epa.log.entity.LogType;
import com.cp.epa.log.services.IBussOperateLogService;
import com.cp.epa.log.services.ILogMethodService;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.sysconfig.utils.SysConfigContext;
import com.cp.epa.utils.PapUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 类名：OperLogInterceptor  <br />
 *
 * 功能：业务操作日志拦截器，区别于OperateInterceptor，
 * 该拦截器是只拦截业务方法，不拦截框架方法，并且拦截的方法都以实际业务名称显示而非英文代码
 *
 * @author 孟雪勤 <br />
 * 创建时间：2016-1-25 上午11:35:55  <br />
 * @version 2016-1-25
 */
public class BussOperInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 2348868396960555586L;
	/** 操作日志业务注册 */
	@Resource
	private IBussOperateLogService logService;
	/** 方法业务注册 */
	@Resource
	private ILogMethodService methodService;
	
	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2016-1-25 上午11:40:59<br/>
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception <br/>
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String result = invocation.invoke();
		
		// 开关
		String status = SysConfigContext.getSwitch("BussOperLog");
		if(status != null && "N".equals(status))
			return result;
		
		Users u = (Users)invocation.getInvocationContext().getSession().get("CURRENTUSER");
		if(null != u){
			//业务对象
			String actionName = invocation.getAction().getClass().getSimpleName();
			if(actionName.contains("Action")){
				String actionFullName = invocation.getAction().getClass().getName(); 
				//业务方法
				String methodName = invocation.getProxy().getMethod();
				LogMethod method = methodService.selectByClass(actionFullName, methodName);
				if (null != method) {
					String ip = request.getRemoteAddr();
					if(!"load".equals(methodName.substring(0,4))){
						BussOperateLog log = new BussOperateLog();
						log.setIp(ip);
						log.setOperDate(PapUtil.date(new Date()));
						log.setOperUserName(u.getUserRealName());
						log.setClassPath(actionFullName);
						log.setClassName(method.getLogClass().getName());
						log.setMethodName(method.getName());
						LogType module = method.getLogType();
						if (null != module) {
							log.setModuleId(module.getId());
							log.setModuleName(module.getName());
						}
						logService.save(log);
					}
				}
			}
		}
		return result;
	}
}
