package com.cp.epa.check.services;

import java.io.Serializable;
import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.check.entity.CheckBillState;
import com.cp.epa.check.entity.CheckState;
/**
 * 审核对象服务层接口
 * 类名：ICheckService  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 3:58:23 PM 
 * @version Dec 3, 2014
 */
public interface ICheckBillStateService extends IBaseService<CheckBillState> {
	 
	/**
	 * 审核功能
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 7, 2014 12:00:38 PM <br/>
	 */
	<ID extends Serializable> Boolean check(ID PK,String className)throws Exception;
	
	
	/**
	 * 反审核功能
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 7, 2014 12:00:38 PM <br/>
	 */
	<ID extends Serializable> Boolean cancleCheck(ID PK,String className)throws Exception;
	
	/**
	 * 查询审核状况
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 8, 2014 3:49:18 PM <br/>
	 */
	<ID extends Serializable> CheckState selectShow(ID PK,String className,String level)throws Exception;
	
	/**
	 * 功能：获得指定单据的所有已审批对象<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-2-4 下午04:34:32 <br/>
	 */
	public List<CheckState> getCheckedState(CheckBillState cs,String num)throws Exception;
	/**
	 * 功能：获得指定单据的所有未审批对象<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-2-4 下午04:34:32 <br/>
	 */
	public List<CheckState> getUnCheckState(String num)throws Exception;
	
	/**
	 * 判断单据是不是已经审核完成
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-3-3 下午05:30:22 <br/>
	 */
	public <ID extends Serializable> Boolean isCheckOk(ID PK,String className)throws Exception;
	
}
