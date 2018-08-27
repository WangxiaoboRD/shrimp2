/**
 * 文件名：@ISysSwitchService.java <br/>
 * 包名：com.zhongpin.pap.sysconfig.services <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.sysconfig.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.sysconfig.entity.SysSwitch;

/**
 * 类名：ISysSwitchService  <br />
 *
 * 功能：系统开关业务逻辑层定义
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-20 上午10:26:14  <br />
 * @version 2013-12-20
 */
public interface ISysSwitchService extends IBaseService<SysSwitch> {

	/**
	 * 功能：启用<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-20 上午11:33:18 <br/>
	 */
	public void enable(String[] ids) throws Exception;
	
	/**
	 * 功能：禁用<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-20 上午11:33:33 <br/>
	 */
	public void disable(String[] ids) throws Exception;
}
