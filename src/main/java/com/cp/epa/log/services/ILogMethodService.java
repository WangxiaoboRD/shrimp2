/**
 * 文件名：@ILogMethodService.java <br/>
 * 包名：com.zhongpin.ps.baseinfo.services <br/>
 * 项目名：ps <br/>
 * @author 席金红 <br/>
 */
package com.cp.epa.log.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.log.entity.LogMethod;

/**
 * 类名：ILogMethodService  <br />
 *
 * 功能：日志方法
 *
 * @author 席金红 <br />
 * 创建时间：2016-1-25 上午10:15:23  <br />
 * @version 2016-1-25
 */
public interface ILogMethodService extends IBaseService<LogMethod> {

	/**
	 * 功能：根据Class和名称查询方法<br/>
	 *
	 * @author 孟雪勤
	 * @version 2016-1-30 下午02:58:52 <br/>
	 */
	public LogMethod selectByClass(String className, String method) throws Exception;
	
}
