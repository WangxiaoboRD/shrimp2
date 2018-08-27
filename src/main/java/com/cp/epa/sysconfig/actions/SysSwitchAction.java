/**
 * 文件名：@SysSwitchAction.java <br/>
 * 包名：com.zhongpin.pap.sysconfig.actions <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.sysconfig.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.sysconfig.entity.SysSwitch;
import com.cp.epa.sysconfig.services.ISysSwitchService;

/**
 * 类名：SysSwitchAction  <br />
 *
 * 功能：系统开关控制层处理
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-20 上午10:28:11  <br />
 * @version 2013-12-20
 */
public class SysSwitchAction extends BaseAction<SysSwitch, ISysSwitchService> {

	private static final long serialVersionUID = 5679779163670304065L;

	/**
	 * 功能：启用<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-20 上午11:36:51 <br/>
	 */
	public String enable()throws Exception{
		service.enable(ids.split(","));
		message = "MODIFYOK";
		text(message);
		return NONE;
	}
	
	/**
	 * 功能：禁用<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-20 上午11:36:54 <br/>
	 */
	public String disable()throws Exception{
		service.disable(ids.split(","));
		message = "MODIFYOK";
		text(message);
		return NONE;
	}
}
