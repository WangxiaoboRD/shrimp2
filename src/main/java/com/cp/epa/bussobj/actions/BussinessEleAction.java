package com.cp.epa.bussobj.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.bussobj.entity.BussinessEle;
import com.cp.epa.bussobj.services.IBussinessEleService;
/**
 * 
 * 类名：BussinessEleAction  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-1 上午10:34:23  <br />
 * @version 2013-7-1
 */
public class BussinessEleAction extends BaseAction<BussinessEle, IBussinessEleService>{

	/**
	 * 查询
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Aug 31, 2013 11:16:17 AM<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseAction#loadType()
	 */
	public String loadType()throws Exception{
		elist = service.getBussEle();
		result.put("Rows", elist);
		return JSON;
	}
	
	
	public String loadDataType()throws Exception{
		
		elist=service.selectAll();
		result.put("Rows", elist);
		return JSON;
	}
	
	
	
	public String loadJsonType()throws Exception{
		elist = service.selectAll(e);
		result.put("Rows", elist);
		return JSON;
	}
	
	
	
	
	
	
	
	
	
	
	
}
