/**
 * 文件名：@VacantNumberAction.java <br/>
 * 包名：com.zhongpin.pap.number.actions <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.actions;

import java.util.List;

import com.cp.epa.base.BaseAction;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.number.entity.VacantNumber;
import com.cp.epa.number.services.IVacantNumberService;

/**
 * 类名：VacantNumberAction  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午02:56:37  <br />
 * @version 2015-10-7
 */
public class VacantNumberAction extends BaseAction<VacantNumber, IVacantNumberService> {

	private static final long serialVersionUID = -3180469520506344387L;

	/**
	 * 功能：查询空号中的业务对象<br/>
	 *
	 * @author 孟雪勤
	 * @version 2016-1-4 下午01:31:07 <br/>
	 */
	public String loadBussObj()throws Exception{
		List<BussinessObject> list = service.selectBussObj();
		result.put("Rows", list);
		result.put("Total", list.size());
		
		return JSON;
	}
}
