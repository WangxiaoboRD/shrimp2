package com.cp.epa.permission.actions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cp.epa.base.BaseAction;
import com.cp.epa.permission.entity.AuthObj;
import com.cp.epa.permission.entity.Role;
import com.cp.epa.permission.services.IRoleService;

public class RoleAction extends BaseAction<Role,IRoleService>{

	private static final long serialVersionUID = 1L;
	
	private Map<Object, Integer> map = new LinkedHashMap<Object,Integer>();
	
	/**
	 * 功能：加载权限对象集合<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-9 下午05:59:49 <br/>
	 */
	public String loadAuthObjSet()throws Exception{
		e = service.selectById(id);
		List<AuthObj> objs = e.getAuthObjSet();
		result.put("Rows", objs);
		result.put("Total", objs.size());
		return JSON;
	}
	
	/**
	 * 功能：授权页面<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-11 上午10:10:52 <br/>
	 */
	public String loadGrantRoleById()throws Exception{
		e = service.selectById(id);
		return "grant";
	}
	
	/**
	 * 功能：分配权限页面<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-11 下午03:25:04 <br/>
	 */
	public String loadAllotRoleById()throws Exception{
		e = service.selectById(id);
		return "allotauth";
	}
	
	public Map<Object, Integer> getMap() {
		return map;
	}

	public void setMap(Map<Object, Integer> map) {
		this.map = map;
	}
	
}
