/**
 * 文件名：@BussObjAuthPropertyAction.java <br/>
 * 包名：com.zhongpin.pap.permission.actions <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.permission.entity.BussObjAuthProperty;
import com.cp.epa.permission.services.IBussObjAuthPropertyService;
import com.cp.epa.utils.TypeUtil;

/**
 * 类名：BussObjAuthPropertyAction  <br />
 *
 * 功能：业务对象权限过滤属性控制层处理
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:38:37  <br />
 * @version 2013-11-6
 */
public class BussObjAuthPropertyAction extends BaseAction<BussObjAuthProperty, IBussObjAuthPropertyService> {

	private static final long serialVersionUID = -5370500083280591885L;
	
	/**
	 * 功能：启用<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-18 下午03:59:51 <br/>
	 */
	public String enable()throws Exception{
		Integer[] _ids =TypeUtil.getIdsType(ids,e.getClass());
		service.enable(_ids);
		message = "MODIFYOK";
		text(message);
		return NONE;
	}
	
	/**
	 * 功能：停用、禁用<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-18 下午04:02:20 <br/>
	 */
	public String disable()throws Exception{
		Integer[] _ids = TypeUtil.getIdsType(ids,e.getClass());
		service.disable(_ids);
		message="MODIFYOK";
		text(message);
		return NONE;
	}
}
