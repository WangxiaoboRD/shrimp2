package com.cp.epa.check.services.impl;

import java.util.List;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.check.dao.ICheckRoleDao;
import com.cp.epa.check.entity.CheckRole;
import com.cp.epa.check.services.ICheckRoleService;
import com.cp.epa.exception.SystemException;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;
/**
 * 审核权限服务实现
 * 类名：CheckRoleServiceImpl  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 4:09:34 PM 
 * @version Dec 3, 2014
 */
public class CheckRoleServiceImpl extends BaseServiceImpl<CheckRole,ICheckRoleDao> implements ICheckRoleService{

	/**
	 * 分配权限
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 6, 2014 4:19:40 PM <br/>
	 */
	public boolean allotAuth(CheckRole e)throws Exception{
		if(e == null)
			throw new SystemException("权限对象不存在");
		//查询数据库中 对于同一个角色同一个对象的权限设置是不是存在
		SqlMap<String,String,String> sqlmap = new SqlMap<String,String,String>();
		sqlmap.put("className", "=", e.getClassName());
		sqlmap.put("role.roleCode", "=", e.getRole().getRoleCode());
		List<CheckRole> crList = super.selectHQL(sqlmap);
		sqlmap.clear();
		sqlmap=null;
		if(crList == null || crList.size()==0){
			super.save(e);
			return true;
		}else{
			if(crList.size()>1)
				throw new SystemException("审核权限设置重复");
			CheckRole _checkRole = crList.get(0);
			_checkRole.setCheckLevels(e.getCheckLevels());
			return true;
		}
	}
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public void selectAll(CheckRole entity,Pager<CheckRole> page)throws Exception{
		super.selectAll(entity,page);
		List<CheckRole> clist = page.getResult();
		if(clist != null && clist.size()>0){
			for(CheckRole cr : clist){
				String levels = cr.getCheckLevels();
				if(!"".equals(levels)){
					String[] arrLevels = levels.split(",");
					String levelsName = "";
					for(String lv : arrLevels){
						levelsName +=PapUtil.transform(lv)+"级审核,";
					}
					levelsName = levelsName.substring(0,levelsName.length()-1);
					cr.setCheckLevelName(levelsName);
				}
			}
		}
	}
}
