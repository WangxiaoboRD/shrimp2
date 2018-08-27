/**
 * 文件名：@UserAuthValueDaoImpl.java <br/>
 * 包名：com.zhongpin.pap.permission.dao.impl <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cp.epa.base.BaseDaoImpl;
import com.cp.epa.permission.dao.IUserAuthValueDao;
import com.cp.epa.permission.entity.UserAuthValue;

/**
 * 类名：UserAuthValueDaoImpl  <br />
 *
 * 功能：用户权限规则持久层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:56:13  <br />
 * @version 2013-11-6
 */
public class UserAuthValueDaoImpl extends BaseDaoImpl<UserAuthValue> implements IUserAuthValueDao {

	/**
	 * 通过条件查询结果集，权限专用
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 27, 2013 9:25:25 AM <br/>
	 */
	@SuppressWarnings("unchecked")
	public List<UserAuthValue> selectAllByAuth(Map<String,String> map)throws Exception{
		String hql = "from "+super.entityClass.getName()+" where 1=1";
		if(map == null)
			return null;
		//遍历map
		Set<Map.Entry<String, String>> set = map.entrySet();
        for (Iterator<Map.Entry<String, String>> it = set.iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            hql += " and "+entry.getKey()+"='"+entry.getValue()+"'";
        }
        //执行HQL
        return (List<UserAuthValue>) hibernateTemplate.find(hql);
	}
}
