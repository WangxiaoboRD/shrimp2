/**
 * 文件名：@IUserAuthValueService.java <br/>
 * 包名：com.zhongpin.pap.permission.services <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.services;

import java.util.List;
import java.util.Map;

import com.cp.epa.base.IBaseService;
import com.cp.epa.permission.entity.Item;
import com.cp.epa.permission.entity.UserAuthValue;
import com.cp.epa.permission.entity.Users;

/**
 * 类名：IUserAuthValueService  <br />
 *
 * 功能：用户权限规则业务逻辑层定义
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:57:23  <br />
 * @version 2013-11-6
 */
public interface IUserAuthValueService extends IBaseService<UserAuthValue> {
	
	/**
	 * 
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 17, 2013 10:14:35 AM <br/>
	 */
	public List<Item> getAllItemByMenu(Users u)throws Exception;
	
	/**
	 * 
	 * 功能：<br/>
	 * 验证按钮的url是否合法
	 * @author 杜中良
	 * @version Nov 17, 2013 10:14:35 AM <br/>
	 */
	public boolean validate(String url,Users u)throws Exception; 
	
	/**
	 * 根据条件查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 24, 2013 3:44:17 PM <br/>
	 */
	public List<UserAuthValue> selectAllByCod(String authObj,String authField,Users u)throws Exception;
	
	/**
	 * 通过条件查询结果集，权限专用
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 27, 2013 9:25:25 AM <br/>
	 */
	List<UserAuthValue> selectAllByAuth(Map<String,String> map)throws Exception;
	
	/**
	 * 接口权限的验证
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Nov 29, 2013 9:18:50 AM <br/>
	 */
	boolean checkInterfaceAuthority(String methodName,String userId)throws Exception;
}
