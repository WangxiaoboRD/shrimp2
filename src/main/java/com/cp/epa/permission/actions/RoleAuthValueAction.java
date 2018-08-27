/**
 * 文件名：@RoleAuthValueAction.java <br/>
 * 包名：com.zhongpin.pap.permission.actions <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.actions;

import java.util.List;

import com.cp.epa.base.BaseAction;
import com.cp.epa.permission.entity.AuthField;
import com.cp.epa.permission.entity.RoleAuthValue;
import com.cp.epa.permission.entity.RoleAuthValueDetail;
import com.cp.epa.permission.services.IRoleAuthValueService;
import com.cp.epa.utils.TypeUtil;

/**
 * 类名：RoleAuthValueAction <br />
 * 
 * 功能：角色权限规则值控制层处理
 * 
 * @author 孟雪勤 <br />
 *         创建时间：2013-11-6 下午02:52:48 <br />
 * @version 2013-11-6
 */
public class RoleAuthValueAction extends BaseAction<RoleAuthValue, IRoleAuthValueService> {

	private static final long serialVersionUID = -4361845705100240447L;

	/**
	 * 功能：根据条件[角色、权限对象]加载角色权限规则<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2013-11-11 下午03:08:02 <br/>
	 */
	public String loadByRoleAuthObj() throws Exception {

		elist = service.selectByRoleAuthObj(e);
		result.put("Rows", elist);
		result.put("Total", elist.size());
		return JSON;
	}

	/**
	 * 功能：根据id加载完整信息,调转带分配权限页面<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2013-11-12 下午04:13:29 <br/>
	 */
	public String loadEntityById() throws Exception {
		Integer _id = TypeUtil.getIdType(id,e.getClass());
		e = service.selectById(_id);
		AuthField authField = e.getAuthField();
		String valueType = authField.getBussinessEle().getValueType();
		int authTree = authField.getTree();
		// 若业务元素的值类型为固定值或参考表
		if ("2".equals(valueType) || "3".equals(valueType)) {
			List<RoleAuthValueDetail> details = e.getDetails();
			if (null != details && details.size() == 1) {
				e.setValue(details.get(0).getValue());
			}
		}else {
			e.setValue("");
		}
		
		// 若为参考表的树状权限分配，则返回树状权限分配页面
		if ("2".equals(valueType) && authTree == 1)
			return "allottree";
		
		return "allot";
	}
	
	/**
	 * 功能：分配权限<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-16 下午04:14:56 <br/>
	 */
	public String allotAuth()throws Exception{
		service.allotAuth(e);
		message="MODIFYOK";
		text(message);
		return NONE;
	}
	
	/**
	 * 功能：取消权限分配<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-16 下午04:15:19 <br/>
	 */
	public String cancelAuth()throws Exception{
		service.cancelAuth(e);
		message="MODIFYOK";
		text(message);
		return NONE;
	}
}
