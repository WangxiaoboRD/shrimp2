/**
 * 文件名：@VacantManageAction.java <br/>
 * 包名：com.zhongpin.pap.number.actions <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.number.entity.VacantManage;
import com.cp.epa.number.services.IVacantManageService;

/**
 * 类名：VacantManageAction  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午02:49:54  <br />
 * @version 2015-10-7
 */
public class VacantManageAction extends BaseAction<VacantManage, IVacantManageService> {

	private static final long serialVersionUID = -3336648685398318866L;

	/**
	 * 功能：启动<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-15 上午10:42:31 <br/>
	 */
	public String start() throws Exception{
		service.operateStart(e);
		text("STARTOK");
		return NONE;
	}
	
	/**
	 * 功能：停止<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-15 上午10:42:58 <br/>
	 */
	public String stop() throws Exception{
		service.operateStop(e);
		text("STARTOK");
		return NONE;
	}
}
