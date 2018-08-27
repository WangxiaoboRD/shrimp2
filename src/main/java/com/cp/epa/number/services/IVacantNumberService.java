/**
 * 文件名：@IVacantNumberService.java <br/>
 * 包名：com.zhongpin.pap.number.services <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.number.entity.VacantNumber;

/**
 * 类名：IVacantNumberService  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午02:54:16  <br />
 * @version 2015-10-7
 */
public interface IVacantNumberService extends IBaseService<VacantNumber> {

	/**
	 * 功能：创建空号<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-15 上午10:02:58 <br/>
	 */
	public void createNumber(String objCode) throws Exception;
	
	/**
	 * 功能：查询业务对象<br/>
	 *
	 * @author 孟雪勤
	 * @version 2016-1-4 下午01:29:17 <br/>
	 */
	public List<BussinessObject> selectBussObj() throws Exception;
}
