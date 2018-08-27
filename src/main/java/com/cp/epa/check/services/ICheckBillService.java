package com.cp.epa.check.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.check.entity.CheckBill;
/**
 * 审核单据服务层接口
 * 类名：ICheckBillService  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 3:58:23 PM 
 * @version Dec 3, 2014
 */
public interface ICheckBillService extends IBaseService<CheckBill> {
	/**
	 * 查询并过滤已经配置过的
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
	public List<CheckBill> selectAllByCheck()throws Exception;
}
