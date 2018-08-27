package com.cp.epa.interceptor;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cp.epa.log.entity.LoginLog;
import com.cp.epa.log.services.ILoginLogService;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.sysconfig.utils.SysConfigContext;
import com.cp.epa.utils.PapUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 登录日志拦截器
 * 类名：LogInterceptor  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：May 17, 2013 9:20:59 AM 
 * @version May 17, 2013
 */
public class LogInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	@Resource
	private ILoginLogService loginLogServiceImpl;
	public String intercept(ActionInvocation invocation) throws Exception {
		
		
		//Class<?> clazz = invocation.getAction().getClass();
		//System.out.println("---:"+clazz.getName());
		//获取当前action对象所持有的泛型类型
		//Type[] _type = TypeUtil.getActualTypes(clazz);
		//获取第一个泛型类型及实体类
		//System.out.println( "------entity:"+_type[0].getClass().getName());
		//TypeUtil.getClassIdType(_type[0].getClass());
		//1.获取action名称
		//2.获取action所持有的实体类泛型
		//3.获取实体类的Id的类型
		//4.获取action的ID 与ids属性的值
		//5.判断id与ids不为空的情况下，将其类型转换为实体类相对应的类型
		
		HttpServletRequest request = ServletActionContext.getRequest();
		/*
		 * 执行拦截器
		 */
		String result = invocation.invoke();
		
		// 开关
		String status = SysConfigContext.getSwitch("loginLog");
		if(status != null && "N".equals(status))
			return result;
		
		Users u = (Users)invocation.getInvocationContext().getSession().get("CURRENTUSER");
		if(null != u){

			String ip = request.getRemoteAddr();
			String loginName = u.getUserCode();
			String loginRalName = u.getUserRealName();
			String date = PapUtil.date(new Date());
			
			LoginLog log = new LoginLog();

			log.setIp(ip);
			log.setLoginDate(date);
			log.setUserName(loginRalName);
			log.setUserCode(loginName);
			
			loginLogServiceImpl.save(log);
		}
		return result;
	}
	
	


}
