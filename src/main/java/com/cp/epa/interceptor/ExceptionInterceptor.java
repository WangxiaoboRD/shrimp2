package com.cp.epa.interceptor;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cp.epa.base.BaseAction;
import com.cp.epa.exception.SystemException;
import com.cp.epa.log.entity.ExceptionLog;
import com.cp.epa.log.services.IExceptionLogService;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.sysconfig.utils.SysConfigContext;
import com.cp.epa.utils.PapUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 异常日志拦截器
 * 类名：LogInterceptor  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：May 17, 2013 9:20:59 AM 
 * @version May 17, 2013
 */
@SuppressWarnings("serial")
public class ExceptionInterceptor extends AbstractInterceptor {
	@SuppressWarnings("unused")
	@Resource
	private IExceptionLogService exceptionLogServiceImpl;
	
	public String intercept(ActionInvocation invocation) throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		try {
			return invocation.invoke();
		} catch (Exception e) {
			// 开关
			String status = SysConfigContext.getSwitch("exceptionLog");
			if(status == null || !"N".equals(status)){
				BaseAction b =(BaseAction)invocation.getAction();
				Map<String,Object> m = b.getResult();
				operException(e,invocation,m);
			}else{
				e.printStackTrace();
			}
			/**
			 * 放入异常信息
			 */
			//StringWriter sw = new StringWriter();
			//e.printStackTrace(new PrintWriter(sw));
			
			
			//HttpSession session = request.getSession();
			//session.setAttribute("EXCEPTION_VALUE", _v);
//			HttpServletResponse response = ServletActionContext.getResponse();
//			
//			response.setContentType("text/html;charset=UTF-8"); 
//			response.setHeader("Pragma", "No-cache");
//			response.setHeader("Cache-Control", "no-cache");
//			response.setDateHeader("Expires", 0);
//			PrintWriter out;
//			try {
//				out = response.getWriter();
//				out.append("error");
//				out.flush();
//				out.close();
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			return BaseAction.JSON;
		}
	}
	
	
	private void operException(Exception e,ActionInvocation invocation,Map<String,Object> m){
		HttpServletRequest request = ServletActionContext.getRequest();
		Users u = (Users)invocation.getInvocationContext().getSession().get("CURRENTUSER");
		if(null != u){
			//业务方法
			String methodName = invocation.getProxy().getMethod();			
			String ip = request.getRemoteAddr();
			String operateName = u.getUserCode();
			
			ExceptionLog log = new ExceptionLog();
			if(e instanceof SystemException){
				m.put("EXCEPTION", e.getMessage());
				log.setExceptionInfo(e.getMessage());
			}else{
				//value="系统异常:"+e.getClass().getSimpleName();
				StringBuffer msg = new StringBuffer();  
				int length = e.getStackTrace().length;  
				if (length > 0) {  
	                msg.append(e.toString() + "\n");  
	                for (int i = 0; i < length; i++) {  
	                    msg.append("\t" + e.getStackTrace()[i] + "\n");  
	                }  
	            }
				m.put("EXCEPTION", "系统异常:"+e.getClass().getSimpleName());
				m.put("MSG", msg.toString());
				log.setExceptionInfo(msg.toString());
			}
			log.setOperateUserCode(operateName);
			log.setOperateType(methodName);
			log.setOperateDate(PapUtil.date(new Date()));
			log.setIp(ip);
			
			try {
				exceptionLogServiceImpl.save(log);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
