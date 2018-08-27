
package com.cp.epa.debug.actions;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.cp.epa.base.BaseAction;
import com.cp.epa.debug.entity.DebugSql;
import com.cp.epa.debug.services.IDebugSqlService;
import com.cp.epa.debug.utils.P6spyUtil;
import com.cp.epa.permission.entity.Users;
import com.opensymphony.xwork2.ActionContext;
import com.p6spy.engine.common.P6OutDataInteface;



/**
 * 
 * 类名：DebugSqlAction  <br />
 *
 * 功能：
 *
 * @author dzl <br />
 * 创建时间：Jan 9, 2013 9:17:58 AM  <br />
 * @version Jan 9, 2013
 */
public class DebugSqlAction extends BaseAction<DebugSql, IDebugSqlService>  {

	private static final long serialVersionUID = 1L;
	private String systemStatus;
	private String sqlStatus;
	
	
	
	/**
	 * 
	 * 功能：分页条件查询<br/>
	 *
	 * @author xtwin
	 * @version 2011-12-22 下午04:01:01 <br/>
	 */
	public String loadByPage() throws Exception {
		
		//获得当前用户
		Users currentUser = (Users)session.get(CURRENTUSER);
		
		String statu = (String)session.get(currentUser.getUserCode()+P6spyUtil.sql);
		service.findFile(e,statu,pageBean,currentUser);
		result.put("Rows", pageBean.getResult());
		result.put("Total", pageBean.getTotalCount());
		pageBean.setResult(null);
		result.put("pageBean", pageBean);
		return JSON;
	}

	/**
	 * 调试初始状态
	 * 功能：<br/>
	 *
	 * @author dzl
	 * @version Jan 9, 2013 3:43:12 PM <br/>
	 */
	public String initDebugStatus(){
		//获得当前用户
		Users currentUser = (Users)session.get(CURRENTUSER);
		String systemStutas = (String)session.get(currentUser.getUserCode()+P6spyUtil.system);
		String sqlStutas = (String)session.get(currentUser.getUserCode()+P6spyUtil.sql);
		if(systemStutas == null || "".equals(systemStutas))
			systemStutas = "OFF";
		if(sqlStutas == null || "".equals(sqlStutas))
			sqlStutas = "OFF";
		
		this.systemStatus = systemStutas;
		this.sqlStatus = sqlStutas;
		
		return "switch";
	}
	
	
	/**
	 * 设置调试状态
	 * 功能：<br/>
	 *
	 * @author dzl
	 * @version Jan 9, 2013 3:44:49 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	public String openOnOff(){
	
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		ServletContext context  = request.getSession().getServletContext(); 
		
		//获得当前用户
		Users currentUser = (Users)session.get(CURRENTUSER);
		if(systemStatus != null && !"".equals(systemStatus)){
			if("ON".equals(systemStatus)){
				//session开关
				session.put(currentUser.getUserCode()+P6spyUtil.system, systemStatus);
				//将开关放入容器中
				context.setAttribute(currentUser.getUserCode()+P6spyUtil.system, systemStatus);
				
				result.put("status", "ON");
			}else{
				session.remove(currentUser.getUserCode()+P6spyUtil.system);
				context.removeAttribute(currentUser.getUserCode()+P6spyUtil.system);
				result.put("status", "OFF");
			}
			
		}
		if(sqlStatus != null && !"".equals(sqlStatus)){
			if("ON".equals(sqlStatus)){
				session.put(currentUser.getUserCode()+P6spyUtil.sql, sqlStatus);
				session.put(currentUser.getUserCode()+P6spyUtil.FILENAME,currentUser.getUserCode()+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+System.currentTimeMillis()+".log");
				context.setAttribute(currentUser.getUserCode()+P6spyUtil.sql, sqlStatus);
				P6OutDataInteface.openMap.put(currentUser.getUserCode()+P6spyUtil.sql, sqlStatus);
				result.put("status", "ON");
			}else{
				session.remove(currentUser.getUserCode()+P6spyUtil.sql);
				session.remove(currentUser.getUserCode()+P6spyUtil.FILENAME);
				context.removeAttribute(currentUser.getUserCode()+P6spyUtil.sql);
				P6OutDataInteface.openMap.remove(currentUser.getUserCode()+P6spyUtil.sql);
				result.put("status", "OFF");
			}
		}
		
		return JSON;
		
	}

	public String getSystemStatus() {
		return systemStatus;
	}
	public void setSystemStatus(String systemStatus) {
		this.systemStatus = systemStatus;
	}
	public String getSqlStatus() {
		return sqlStatus;
	}
	public void setSqlStatus(String sqlStatus) {
		this.sqlStatus = sqlStatus;
	}
}
