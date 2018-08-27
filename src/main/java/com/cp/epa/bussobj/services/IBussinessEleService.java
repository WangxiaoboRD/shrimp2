package com.cp.epa.bussobj.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.bussobj.entity.BussinessEle;
/**
 * 
 * 类名：IBussinessEleService  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-1 上午10:32:17  <br />
 * @version 2013-7-1
 */
public interface IBussinessEleService extends IBaseService<BussinessEle>{

	/**
	 * 通过条件获取业务元素
	 * 条件是 查询 有固定值与参考表的元素
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 31, 2013 10:25:54 AM <br/>
	 */
	List<BussinessEle> getBussEle()throws Exception;

	
}
