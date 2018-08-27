package com.cp.epa.permission.actions;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.cp.epa.base.BaseAction;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IUsersService;


@ParentPackage("login")
public class LoginAction extends BaseAction<Users,IUsersService>{
	
	private static final long serialVersionUID = 1L;

	/**
	 *  简单登录测试
	 */
	public String login(){
		e = service.selectLoginUser(e.getUserCode(), e.getUserPassword());
		if(e != null){
			if(e.getUserStatus() == 1){
				message ="LOGINOK";
				//将当前登录对象保存到session中
				session.remove(CURRENTUSER);   
				session.put(CURRENTUSER, e);
				//SysContainer.set(e);
			}else{
				message = "LOGINSTOP";
			}
		}else{
			message="LOGINERROR";
		}
		text(message);
		//result.put("message", message);
		//return JSON;
		return NONE;
	}
//	/**
//	 * 简单登录测试
//	 */
//	public String login(){
//		e = service.selectLoginUser(e.getUserName(), e.getPassword());
//		if(e != null){
//			session.put(CURRENTUSER, e);
//			return INDEX;
//		}
//		return SUCCESS;
//	}
}
