/**
 * 文件名：@AuthObjAction.java <br/>
 * 包名：com.zhongpin.pap.permission.actions <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cp.epa.base.BaseAction;
import com.cp.epa.bussobj.utils.DatabaseTypeUtil;
import com.cp.epa.permission.entity.AuthField;
import com.cp.epa.permission.entity.AuthObj;
import com.cp.epa.permission.services.IAuthObjService;

/**
 * 类名：AuthObjAction <br />
 * 
 * 功能：权限对象控制层处理
 * 
 * @author 孟雪勤 <br />
 *         创建时间：2013-11-6 下午02:29:55 <br />
 * @version 2013-11-6
 */
public class AuthObjAction extends BaseAction<AuthObj, IAuthObjService> {

	private static final long serialVersionUID = -6750687839985044300L;

	/**
	 * 功能：加载权限字段明细<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2013-11-9 上午11:33:44 <br/>
	 */
	public String loadFieldSet() throws Exception {
		e = service.selectById(id);
		List<AuthField> fields = e.getFieldSet();
		if (null != fields && fields.size() > 0) {
			for (AuthField field : fields) {
				Map<String, String> tempStack = field.getTempStack();
				if (null == tempStack)
					field.setTempStack(tempStack = new HashMap<String, String>());
				String dataType = field.getBussinessEle().getDataType();
				if (null != dataType) {
					tempStack.put("databaseDataType", new DatabaseTypeUtil().getDataType(dataType));
				}
			}
		}
		result.put("Rows", fields);
		result.put("Total", fields.size());
		return JSON;
	}
	
	/**
	 * 功能：权限字段选择页面<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-18 上午10:58:45 <br/>
	 */
	public String loadSelectFieldById()throws Exception{
		e = service.selectById(id);
		return "selectField";
	}
}
