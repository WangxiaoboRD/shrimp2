package com.cp.epa.permission.dao;

import com.cp.epa.base.IBaseDao;
import com.cp.epa.permission.entity.Users;


public interface IUsersDao extends IBaseDao<Users> {
	
	/**
	 * 功能：<br/>
	 * 简单用户登录
	 * @author 杜中良
	 * @version Mar 26, 2013 4:36:57 PM <br/>
	 */
	Users selectLoginUser(String name,String password);
}
