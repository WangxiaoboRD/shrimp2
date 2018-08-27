package com.cp.epa.bussobj.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.bussobj.entity.BussObjProperty;
import com.cp.epa.bussobj.services.IBussObjPropertyService;
/**
 * 
 * 类名：BussObjPropertyAction  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-8-8 上午10:45:43  <br />
 * @version 2013-8-8
 */
public class BussObjPropertyAction extends BaseAction<BussObjProperty, IBussObjPropertyService>{
	
	
	private static final long serialVersionUID = -7764865394545855475L;

	/**
	 * 功能：查询业务对象属性<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-9-23 下午04:52:18 <br/>
	 */
	public String loadBussObjProperty()throws Exception{
		
		service.loadBussObjProperty(e, pageBean, null);
		result.put("Rows", pageBean.getResult());
		result.put("Total", pageBean.getTotalCount());		
		pageBean.setResult(null);
		result.put("pageBean", pageBean);
		return JSON;
	}


}
