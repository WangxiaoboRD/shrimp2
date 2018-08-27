package com.cp.epa.number.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.number.entity.NumberConfig;


public interface INumberConfigService extends IBaseService<NumberConfig> {
	
	/**
	 * 验证业务对象是否已经进行绑定号码对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 10, 2013 4:23:17 PM <br/>
	 */
	boolean selectBussObject(String objId)throws Exception;
	
	/**
	 * 验证当前选定的号码对象是否已经绑定了业务对象，
	 * 如果已经绑定，那么以后只能绑定该业务对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 10, 2013 5:13:11 PM <br/>
	 */
	int selectNumber(NumberConfig nc)throws Exception;
	
	/**
	 * 验证业务对象关键属性与号码对象子对象是否一致
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 11, 2013 5:42:05 PM <br/>
	 */
	boolean selectChecking(NumberConfig nc)throws Exception;
	
	/**
	 * 功能：空号设置<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-10-7 上午11:00:06 <br/>
	 */
	void operateVacantSet(Integer[] ids) throws Exception;
}
