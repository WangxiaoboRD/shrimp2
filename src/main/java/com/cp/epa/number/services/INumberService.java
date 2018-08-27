package com.cp.epa.number.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.utils.Pager;
import com.cp.epa.number.entity.Number;

public interface INumberService extends IBaseService<Number> {
	
	/**
	 * 单据模式的更新
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 16, 2013 5:23:21 PM <br/>
	 */
//	public int updateByDetail(Number number)throws Exception;
	
	/**
	 * 获得号码对象一个号码
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 2, 2013 11:20:40 AM <br/>
	 */
	public String get(String number,String numberScope,String year,String subobject)throws Exception;
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	void selectAll(String id,Number entity,Pager<Number> page)throws Exception;
	
	/**
	 * 检验业务对象的编码是否符合号码对象生成规则
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 30, 2013 11:02:04 AM <br/>
	 */
	boolean check(String id,String number,String numberScope,String year,String subobject)throws Exception;
	
	/**
	 * 功能：根据业务对象编码获得对应下一个号码<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-14 下午05:31:12 <br/>
	 */
	public String getNext(String objCode) throws Exception;
}
