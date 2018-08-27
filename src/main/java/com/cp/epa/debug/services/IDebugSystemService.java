/**
 * 文件名：@IAccountUnitService.java <br/>
 * 包名：com.zhongpin.pm.server.service.itf <br/>
 * 项目名：pscm <br/>
 * @author dzl <br/>
 */
package com.cp.epa.debug.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.debug.entity.DebugSystem;


/**
 * 类名：IDebugSqlService  <br />
 *
 * 功能：sql调试
 *
 * @author dzl <br />
 * 创建时间：2012-7-9 上午11:13:17  <br />
 * @version 2012-7-9
 */
public interface IDebugSystemService extends IBaseService<DebugSystem> {

	/**
	 * 保存对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 10, 2014 5:30:02 PM <br/>
	 */
	public void saveEntityList(List<String> slist)throws Exception;
	
//	public void create(List<DebugSystem> _list);
//	
//	public ResultSetModel<DebugSystem> findSystem(DebugSystem sample, String statu,int... pageArgs) throws Exception;

}
