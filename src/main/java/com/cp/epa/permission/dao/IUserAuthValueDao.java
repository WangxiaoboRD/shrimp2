/**
 * 文件名：@IUserAuthValueDao.java <br/>
 * 包名：com.zhongpin.pap.permission.dao <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.dao;

import java.util.List;
import java.util.Map;

import com.cp.epa.base.IBaseDao;
import com.cp.epa.permission.entity.UserAuthValue;

/**
 * 类名：IUserAuthValueDao  <br />
 *
 * 功能：用户权限规则持久层定义
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:54:18  <br />
 * @version 2013-11-6
 */
public interface IUserAuthValueDao extends IBaseDao<UserAuthValue> {
	
	/**
	 * 通过条件查询结果集，权限专用
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 27, 2013 9:25:25 AM <br/>
	 */
	List<UserAuthValue> selectAllByAuth(Map<String,String> map)throws Exception;
}
