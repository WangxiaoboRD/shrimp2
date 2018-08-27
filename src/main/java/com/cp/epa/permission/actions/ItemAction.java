package com.cp.epa.permission.actions;


import com.cp.epa.base.BaseAction;
import com.cp.epa.permission.entity.Item;
import com.cp.epa.permission.services.IItemService;


public class ItemAction extends BaseAction<Item,IItemService>{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 获取最大序号
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 5, 2013 9:32:46 AM <br/>
	 */
	public String loadMaxOrder()throws Exception{
		
		service.selectSortItem(pageBean);
		elist = pageBean.getResult();
		e = elist.get(0);
		text((e.getRank()+1)+"");
		return NONE;
	}
	
	/**
	 * 查询功能列表中，只属于菜单的功能
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 14, 2013 4:30:03 PM <br/>
	 */
	public String loadByConEntity()throws Exception{
		elist = service.selectAllByMenu();
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}
	
	/**
	 * 查询所有资源并以树形展示
	 * 功能：<br/>
	 * @deprecated
	 * @author 杜中良
	 * @version Nov 15, 2013 3:51:37 PM <br/>
	 */
	public String loadAllItemByTree()throws Exception{
		elist = service.selectAllItem(e);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}

}
