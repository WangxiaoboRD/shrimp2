package com.cp.epa.check.actions;
import com.cp.epa.base.BaseAction;
import com.cp.epa.check.entity.CheckRole;
import com.cp.epa.check.services.ICheckRoleService;
/**
 * 审核权限控制层
 * 类名：CheckRoleAction  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 4:12:21 PM 
 * @version Dec 3, 2014
 */
@SuppressWarnings("serial")
public class CheckRoleAction extends BaseAction<CheckRole,ICheckRoleService>{
	/**
	 * 保存权限分配
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:17:59 AM <br/>
	 */
	public String allotAuth()throws Exception{
		//service.update(e);
		if(service.allotAuth(e))
			message="MODIFYOK";
		text(message);
		return NONE;
	}
}
