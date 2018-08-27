package com.cp.epa.bussobj.actions;

import java.util.List;

import com.cp.epa.base.BaseAction;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.services.IBaseTableColumnService;


/**
 * 
 * 类名：BaseTableColumnAction  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:36:29  <br />
 * @version 2013-7-5
 */
public class BaseTableColumnAction extends BaseAction<BaseTableColumn, IBaseTableColumnService>{

	
	/**
	 * 
	 * 功能：<br/>
	 * 加载数据库数据类型
	 * @author zp
	 * @version 2013-7-8 上午09:13:45 <br/>
	 */
	public String loadDataBase()throws Exception{
		List<BaseTableColumn> list=service.loadColumns();
		result.put("Rows", list);
		return JSON;
	}

	
	/**
	 * 
	 * 功能：<br/>
	 * 明细查看
	 * @author zp
	 * @version 2013-7-11 下午03:10:56 <br/>
	 */
	public String loadDetail() throws Exception{

		e = service.loadDetail(e);
		return SHOW;
	}

	
	/**
	 * 
	 * 功能：<br/>
	 * 根据表名加载该表下所有字段
	 * @author zp
	 * @version 2013-11-21 下午04:10:00 <br/>
	 */
	public String loadField()throws Exception{
		elist = service.selectAll(e);
		result.put("Rows", elist);
		return JSON;
	}


	
}
