package com.cp.epa.permission.dao.impl;

import com.cp.epa.base.BaseDaoImpl;
import com.cp.epa.permission.dao.IUsersDao;
import com.cp.epa.permission.entity.Users;

public class UsersDaoImpl extends BaseDaoImpl<Users> implements IUsersDao {
	
	/**
	 * 功能：<br/>
	 * 简单用户登录
	 * @author 杜中良
	 * @version Mar 26, 2013 4:36:57 PM <br/>
	 */
	public Users selectLoginUser(String name,String password){
		String hql = "from Users u where u.userCode=? and u.userPassword=?";
		return (Users) getSession().createQuery(hql).setParameter(0, name).setParameter(1, password).uniqueResult();
//		String hql = "select count(userCode),max(userCode) ,userCode from User u where u.userCode='admin'";
//		
//		List<Object> list = hibernateTemplate.find(hql);
//		
//		return null;
	}
}
