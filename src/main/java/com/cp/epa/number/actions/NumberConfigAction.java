package com.cp.epa.number.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.number.entity.NumberConfig;
import com.cp.epa.number.services.INumberConfigService;
import com.cp.epa.utils.TypeUtil;

public class NumberConfigAction extends BaseAction<NumberConfig,INumberConfigService> {
	
	private static final long serialVersionUID = -1306430247824233294L;

	/**
	 * 验证业务对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 11, 2013 5:38:03 PM <br/>
	 */
	public String selectBussObject() throws Exception{
		boolean b = service.selectBussObject(id);
		text(""+b);
		return NONE;
	}
	
	/**
	 * 验证号码对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 11, 2013 5:37:36 PM <br/>
	 */
	public String selectNumber() throws Exception{
		int b = service.selectNumber(e);
		text(""+b);
		return NONE;
	}
	
	/**
	 * 验证业务对象关键属性与号码对象子对象是否一致
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 16, 2013 3:10:16 PM <br/>
	 */
	public String loadType()throws Exception{
		boolean b = service.selectChecking(e);
		text(""+b);
		return NONE;
	}
	
	/**
	 * 功能: 空号设置<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-10-7 上午11:15:19<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseAction#modifyAll()
	 */
	public String vacantSet()throws Exception{
		Integer[] _ids =TypeUtil.getIdsType(ids,e.getClass());
		service.operateVacantSet(_ids);
		message="SETOK";
		text(message);
		return NONE;
	}
}
