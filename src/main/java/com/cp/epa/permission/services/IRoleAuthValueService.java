/**
 * 文件名：@IRoleAuthValueService.java <br/>
 * 包名：com.zhongpin.pap.permission.services <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.permission.entity.RoleAuthValue;

/**
 * 类名：IRoleAuthValueService  <br />
 *
 * 功能：角色权限规则业务逻辑层定义
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:42:40  <br />
 * @version 2013-11-6
 */
public interface IRoleAuthValueService extends IBaseService<RoleAuthValue> {
	
	/**
	 * 功能：根据角色、权限对象查询角色权限规则信息<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-11 下午02:26:47 <br/>
	 */
	public List<RoleAuthValue> selectByRoleAuthObj(RoleAuthValue entity) throws Exception;
	
	/**
	 * 功能：分配权限<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-16 下午04:02:00 <br/>
	 */
	public void allotAuth(RoleAuthValue entity) throws Exception;
	
	/**
	 * 功能：清空已分配的权限<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-16 下午04:02:35 <br/>
	 */
	public void cancelAuth(RoleAuthValue entity) throws Exception;
	
}
