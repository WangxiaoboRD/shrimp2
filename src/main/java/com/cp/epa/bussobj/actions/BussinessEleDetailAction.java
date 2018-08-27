package com.cp.epa.bussobj.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.bussobj.entity.BussinessEleDetail;
import com.cp.epa.bussobj.services.IBussinessEleDetailService;
/**
 * 
 * 类名：BussinessEleDetailAction  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-2 上午09:47:38  <br />
 * @version 2013-7-2
 */
public class BussinessEleDetailAction extends BaseAction<BussinessEleDetail, IBussinessEleDetailService>{

	
	/**
	 * 获取指定业务元素的业务元素明细信息
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Sep 3, 2013 3:57:33 PM<br/>
	 * 
	 * @return <br/>
	 * @see com.zhongpin.pap.base.BaseAction#loadType()
	 */
	public String loadType()throws Exception{
		elist = service.getBussEleDetail(id);
		result.put("Rows", elist);
		return JSON;
	}
	
	
	/**
	 * 
	 * 功能:配种单弹出框生猪状态选择
	 * @author:wxb
	 * @data:2016-12-1下午09:57:07
	 * @file:BussinessEleDetailAction.java
	 * @return
	 * @throws Exception
	 */
	public String loadByEntityBreed()throws Exception{
				
		elist = service.selectBreed(e);
		result.put("Rows", elist);
		//result.put("Total", elist.size());
		return JSON;
	}
	
	
	
	
	
	
	
	
	
	
}
