package com.cp.epa.permission.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.permission.entity.Role;
import com.cp.epa.permission.entity.Users;

public interface IUsersService extends IBaseService<Users> {
	
	/**
	 * 功能：授权角色<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-19 上午08:35:35 <br/>
	 */
	public void grantRole(Users entity)throws Exception;
	
	/**
	 * 功能：<br/>
	 * 简单用户登录
	 * @author 杜中良
	 * @version Mar 26, 2013 4:36:57 PM <br/>
	 */
	public Users selectLoginUser(String name,String password);
	
	
	/**
	 * 获取user对象以及所有角色，以及user对象目前所包含的角色
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 30, 2013 9:01:19 AM <br/>
	 * @throws Exception 
	 */
	public Users selectById(String PK,List<Role> rlist) throws Exception;
	
	/**
	 * 功能：修改密码<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-7-2 上午08:52:10 <br/>
	 */
	public int updatePwd(Users entity) throws Exception;
	
	/**
	 * 功能：根据拼音首字母模糊查询<br/>
	 *
	 * @author 孟雪勤
	 * @version 2015-12-31 下午05:43:25 <br/>
	 */
	public List<Users> selectByPinyinHead(String key) throws Exception;
}
