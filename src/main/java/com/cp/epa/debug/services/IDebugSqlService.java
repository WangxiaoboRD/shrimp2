/**
 * 文件名：@IAccountUnitService.java <br/>
 * 包名：com.zhongpin.pm.server.service.itf <br/>
 * 项目名：pscm <br/>
 * @author dzl <br/>
 */
package com.cp.epa.debug.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.debug.entity.DebugSql;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.utils.Pager;



/**
 * 类名：IDebugSqlService  <br />
 *
 * 功能：sql调试
 *
 * @author dzl <br />
 * 创建时间：2012-7-9 上午11:13:17  <br />
 * @version 2012-7-9
 */
public interface IDebugSqlService extends IBaseService<DebugSql> {
	/**
	 * 检索信息
	 * 功能：<br/>
	 *
	 * @author dzl
	 * @version Jan 27, 2013 11:45:13 AM <br/>
	 */
	public void findFile(DebugSql debugSql,String statu,Pager<DebugSql> page, Users u)throws Exception;
}
