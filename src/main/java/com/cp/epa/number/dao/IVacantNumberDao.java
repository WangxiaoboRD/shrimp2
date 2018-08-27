/**
 * 文件名：@IVacantNumberDao.java <br/>
 * 包名：com.zhongpin.pap.number.dao <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.dao;

import java.util.List;

import com.cp.epa.base.IBaseDao;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.number.entity.VacantNumber;

/**
 * 类名：IVacantNumberDao  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午02:52:23  <br />
 * @version 2015-10-7
 */
public interface IVacantNumberDao extends IBaseDao<VacantNumber> {

	/**
	 * 功能：查询空号中的所有业务对象<br/>
	 *
	 * @author 孟雪勤
	 * @version 2016-1-4 下午01:23:34 <br/>
	 */
	public List<BussinessObject> selectBussObj() throws Exception;
}
