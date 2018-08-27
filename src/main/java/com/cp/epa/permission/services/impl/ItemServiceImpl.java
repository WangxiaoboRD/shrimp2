package com.cp.epa.permission.services.impl;
import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.permission.dao.IItemDao;
import com.cp.epa.permission.entity.Item;
import com.cp.epa.permission.entity.RoleAuthValue;
import com.cp.epa.permission.services.IItemService;
import com.cp.epa.permission.services.IRoleAuthValueService;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.SqlMap;

/**
 * 
 * 类名：MenuServiceImpl  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：May 28, 2013 10:14:07 AM 
 * @version May 28, 2013
 */
public class ItemServiceImpl extends BaseServiceImpl<Item,IItemDao> implements IItemService{
	
	/** 角色权限值业务注册 */
	@Resource
	private IRoleAuthValueService roleAuthValueService;
	/**
	 * 按照某个属性排序查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 5, 2013 9:37:38 AM <br/>
	 */
	public void selectSortItem(Pager<Item> page)throws Exception{
		SqlMap<String,String,Object> sqlMap = new SqlMap<String,String,Object>(); 
		sqlMap.put("rank", "order by", "desc");
		dao.selectByConditionHQL(sqlMap, page);
	}
	
	/**
	 * 查询菜单列表
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 14, 2013 4:32:45 PM <br/>
	 */
	public List<Item> selectAllByMenu()throws Exception{
		List<Item> items = (List<Item>) selectBySingletAll("itemType",0);
		return items;
	}
	
	/**
	 * 添加
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 29, 2013 8:57:47 AM <br/>
	 */
	public Integer save(Item item)throws Exception{
		if(item == null){
			return null;
		}
		int flag = item.getItemType();
		if(flag == 0 || flag ==1){
			item.setFunctionName(null);
			//Item _m = dao.selectById(item.getParentId().getId());
			if(item.getParentId()==null){
				item.setParentId(0);
			}
			//Item _m = dao.selectById(item.getParentId());
			//int i = _m.getChildrens();
			//_m.setChildrens(0);
			
		}else if(flag == 2){
			item.setUrl(null);
			item.setUrlParam(null);
			//item.setParentId(0);
			
			//Item it = new Item();
			//it.setId(0);
			//item.setParentId(it);
			item.setParentId(0);
		}
		item.setChildrens(0);
		return (Integer) dao.insert(item);
	}
	
	/**
	 * 查询所有资源并以树形展示
	 * 功能：<br/>
	 * @deprecated
	 * @author 杜中良
	 * @version Nov 15, 2013 3:51:37 PM <br/>
	 */
	public List<Item> selectAllItem(Item e)throws Exception{
		
//		
//		SqlMap<String,String,String> sqlmap = new SqlMap<String,String,String>();
//		sqlmap.put("parentId.id", "IS NULL", "");
//		dao.s
		
		List<Item> items = dao.selectAll(e);
		if(items == null || items.size()==0)
			return null;		
//		Iterator<Item> iter=items.iterator();
//	    while(iter.hasNext()){
//	    	Item item = iter.next(); 
//	    	if(item.getParentId()!= null)
//	    		iter.remove();
//	    }
		return items;
	}
	
	
	/**
	 * 更新对象
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Nov 16, 2013 2:46:32 PM<br/>
	 * 
	 * @param item
	 * @return <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#updateHql(com.zhongpin.pap.base.BaseEntity)
	 */
	public int updateHql(Item item)throws Exception{
		
		if(item == null){
			return 0;
		}
		int flag = item.getItemType();
		//原始对象
		Item _it = dao.selectById(item.getId());
		if(flag == 0 || flag ==1){
			//item.setFunctionName(null);
			//原始对象父对象的值
			Integer _oldParentId = _it.getParentId();
			//要更新的对象的父对象值
			Integer _newParentId = item.getParentId();
			if(_oldParentId != _newParentId){
				//原始对象的父对象
				Item _oldPit = dao.selectById(_it.getParentId());
				if(_oldPit != null){
					int i = _oldPit.getChildrens();
					if(i>0)
						_oldPit.setChildrens(i-1);
					dao.update(_oldPit);
				}
				//新对象处理
				//Item _newPit = dao.selectById(item.getParentId().getId());
				Item _newPit = dao.selectById(item.getParentId());
				int t = _newPit.getChildrens();
				_newPit.setChildrens(t+1);
				dao.update(_newPit);
			}
		}else if(flag == 2){
			item.setUrl(null);
			item.setUrlParam(null);
			//item.setParentId(0);
			//Item it = new Item();
			//it.setId(0);
			//item.setParentId(0);
		}
		item.setCreateUser(_it.getCreateUser());
		item.setCreateDate(_it.getCreateDate());
		dao.merge(item);
		return 1;
	}
	
	/**
	 * 删除
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 29, 2013 8:57:47 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception {
		
		List<RoleAuthValue> ravs = (List<RoleAuthValue>) roleAuthValueService.selectBySingletAll("e.authObj.type", "FUNCTION");
		if (null != ravs && ravs.size() > 0) {
			for (RoleAuthValue rav : ravs) {
				String av = rav.getValue();
				if (null != av) {
					for (ID id : PK) {
						if (av.contains(String.valueOf(id))) {
							throw new SystemException("所删除的功能菜单已给角色【" + rav.getRole().getRoleCode() + "】分配权限，不允许删除！");
						}
					}
				}
			}
		}
		
		for(ID in : PK){
			Item it = dao.selectById(in);
			if(it.getChildrens() != null && it.getChildrens() >0)
				throw new SystemException("包含有子菜单");
			dao.delete(it);
			Item _pit = dao.selectById(it.getParentId());
			if(_pit != null){
				_pit.setChildrens(_pit.getChildrens()-1);
				dao.update(_pit);
			}
		}
		
		return PK.length;
	}

}
