package com.cp.epa.check.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.check.entity.CheckRole;
/**
 * 审核权限服务层接口
 * 类名：ICheckRoleService  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 3:58:23 PM 
 * @version Dec 3, 2014
 */
public interface ICheckRoleService extends IBaseService<CheckRole> {
	/**
	 * 分配权限
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 6, 2014 4:19:40 PM <br/>
	 */
	boolean allotAuth(CheckRole e)throws Exception;
}
