package com.cp.epa.bussobj.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.bussobj.entity.BaseTable;
import com.cp.epa.bussobj.services.IBaseTableService;

/**
 * 
 * 类名：BaseTableAction  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:31:46  <br />
 * @version 2013-7-5
 */
@SuppressWarnings("serial")
public class BaseTableAction extends BaseAction<BaseTable, IBaseTableService>{

	public String activeTable()throws Exception{
		
		service.activeTable(ids.split(","));
		text("active");			
		return NONE;
	}
	
	
	public String loadType()throws Exception{
		elist = service.selectAll();
		result.put("Rows", elist);
		return JSON;
	}

	
}
