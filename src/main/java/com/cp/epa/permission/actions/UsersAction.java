package com.cp.epa.permission.actions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cp.epa.base.BaseAction;
import com.cp.epa.permission.entity.Role;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IUsersService;
//@ParentPackage("json-default")
//@Result(type="dispatcher")
//@Namespace("/user")//通过Namespace可以将页面与action进行分包处理，可以将页面放入到你所定义的包下面，比如/user包
public class UsersAction extends BaseAction<Users,IUsersService> {
	
	private static final long serialVersionUID = 6720153380666167887L;

	private Map<Object, Integer> map = new LinkedHashMap<Object,Integer>();
	

	/** 拼音首字母查询关键字 */
	private String key; 

	/**
	 * 功能：根据拼音首字母查询<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-22 上午10:59:51 <br/>
	 */
	public String loadByPinyinHead()throws Exception{
		elist = service.selectByPinyinHead(key);
		result.put("Rows", elist);
		return JSON;
	}
	
	/**
	 * 获取用户信息及角色信息及用户当前所具备的的角色信息
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 29, 2013 6:07:47 PM <br/>
	 */
	public String loadAllotById()throws Exception{
		
		List<Role> rlist = new ArrayList<Role>();
		e =service.selectById(id,rlist);
		List<Role> roles = e.getRoleSet();
		map.clear();
		if(rlist != null && rlist.size()>0){
			if(roles == null || roles.size()==0){
				for(Role r : rlist)
					map.put(r, 0);
			}else{
				loop:for(Role r : rlist){
					for(Role _r : roles){
						if(r.getRoleCode().equals(_r.getRoleCode())){
							map.put(r, 1);
							continue loop;
						}
					}
					map.put(r, 0);
				}
			}
		}
		return "role";
	}
	
	/**
	 * 刷新界面防止session过期
	 * 功能：<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-11-5 上午10:00:47 <br/>
	 */
	public String getReflus()throws Exception{
		return NONE;
	}
	
	/**
	 * 功能：用户授权<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-14 上午11:57:17 <br/>
	 */
	public String loadGrantById()throws Exception{
		e = service.selectById(id);
		return "grant";
	}
	
	/**
	 * 功能：权限集合<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-14 下午02:13:41 <br/>
	 */
	public String loadRoleSet()throws Exception{
		e = service.selectById(id);
		List<Role> roleSet = e.getRoleSet();
		result.put("Rows", roleSet);
		result.put("Total", roleSet.size());
		return JSON;
	}
	
	/**
	 * 功能：授权角色<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-26 上午11:37:27 <br/>
	 */
	public String grantRole()throws Exception{
		service.grantRole(e);
		message="MODIFYOK";
		text(message);
		return NONE;
	}
	
	/**
	 * 功能：修改密码<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-3-25 上午10:04:01 <br/>
	 */
	public String modifyPwd()throws Exception{
		
		if(service.updatePwd(e)>0)
			message="MODIFYOK";
		text(message);
		return NONE;
	}
	
	/**
	 * 功能：批量修改密码<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-6-20 上午09:11:11 <br/>
	 */
	public String batchModifyPwd()throws Exception{
		String[] idArray = ids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userPassword", e.getUserPassword());
		int updateNum = service.updateByIds(Arrays.asList(idArray), map);
		
		if (idArray.length == updateNum)
			message="MODIFYOK";
		text(message);
		return NONE;
	}
	public Map<Object, Integer> getMap() {
		return map;
	}
	public void setMap(Map<Object,Integer> map) {
		this.map = map;
	}

	/**
	 * 获得 key值
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 将key设置为参数key
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
}
