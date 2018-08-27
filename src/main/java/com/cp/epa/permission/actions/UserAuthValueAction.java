/**
 * 文件名：@UserAuthValueAction.java <br/>
 * 包名：com.zhongpin.pap.permission.actions <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.actions;

import java.util.List;

import com.cp.epa.base.BaseAction;
import com.cp.epa.permission.entity.Item;
import com.cp.epa.permission.entity.UserAuthValue;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.permission.services.IUserAuthValueService;

/**
 * 类名：UserAuthValueAction  <br />
 *
 * 功能：用户权限规则控制层处理
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午03:00:23  <br />
 * @version 2013-11-6
 */
public class UserAuthValueAction extends BaseAction<UserAuthValue, IUserAuthValueService> {

	private static final long serialVersionUID = -7205014259669098536L;
	
	private String url;
	
	/**
	 * 通过用户ID获得用户所具备的的权限
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 3, 2013 11:19:15 AM <br/>
	 */
	public String indexMenu()throws Exception{
		Users u = (Users)session.get(CURRENTUSER);
		List<Item> its =  service.getAllItemByMenu(u);
		result.put("Rows", its );
		result.put("Total", its.size());
		return JSON;
	}
	
	/**
	 * 
	 * 功能：<br/>
	 * 验证用户权限，针对于有弹出框与页签的按钮功能
	 * @author 杜中良
	 * @version Nov 20, 2013 4:34:12 PM <br/>
	 */
	public String check()throws Exception{
		boolean b = service.validate(url,(Users)session.get(CURRENTUSER));
		text(b+"");
		return NONE;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
