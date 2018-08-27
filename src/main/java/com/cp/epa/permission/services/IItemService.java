package com.cp.epa.permission.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.permission.entity.Item;
import com.cp.epa.utils.Pager;
/**
 * 
 * 类名：IMenuService  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：May 28, 2013 10:14:15 AM 
 * @version May 28, 2013
 */
public interface IItemService extends IBaseService<Item> {
	
	/**
	 * 按照某个属性排序查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 5, 2013 9:37:38 AM <br/>
	 */
	void selectSortItem(Pager<Item> page)throws Exception;
	
	/**
	 * 查询菜单列表
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 14, 2013 4:32:45 PM <br/>
	 */
	List<Item> selectAllByMenu()throws Exception;
	
	/**
	 * 查询所有资源并以树形展示
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 15, 2013 3:51:37 PM <br/>
	 */
	List<Item> selectAllItem(Item item)throws Exception;
	
}
