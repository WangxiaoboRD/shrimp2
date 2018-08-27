/**
 * 文件名：@IVacantManageService.java <br/>
 * 包名：com.zhongpin.pap.number.services <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.number.entity.VacantManage;

/**
 * 类名：IVacantManageService  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午02:48:19  <br />
 * @version 2015-10-7
 */
public interface IVacantManageService extends IBaseService<VacantManage> {

	/**
	 * 功能：启动空号作业<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-14 下午04:34:17 <br/>
	 */
	public void operateStart(VacantManage entity) throws Exception;
	
	/**
	 * 功能：停止空号作业<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-14 下午04:35:08 <br/>
	 */
	public void operateStop(VacantManage entity) throws Exception;
}
