package com.cp.epa.bussobj.actions;

import java.util.List;
import com.cp.epa.base.BaseAction;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.bussobj.entity.ImportantObj;
import com.cp.epa.bussobj.services.IBussinessObjectService;

/**
 * 
 * 类名：BussinessObjectAction <br />
 * 
 * 功能：
 * 
 * @author zp <br />
 *         创建时间：2013-7-10 上午10:07:08 <br />
 * @version 2013-7-10
 */
public class BussinessObjectAction extends BaseAction<BussinessObject, IBussinessObjectService> {

	private static final long serialVersionUID = -6461544876851282825L;

	public String refreshProperty() throws Exception {
		service.refreshProperty();
		text("refresh");
		return NONE;
	}

	/**
	 * 
	 * 功能：<br/>
	 * 刷新单个model
	 * 
	 * @author zp
	 * @version 2013-8-15 下午05:13:48 <br/>
	 */
	public String refreashSingleModel() throws Exception {

		service.refreshSingleModel(e);

		text("refresh");

		return NONE;
	}

	/**
	 * 获得对象关键属性列表 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Sep 9, 2013 6:00:00 PM<br/>
	 * 
	 * @return <br/>
	 * @throws Exception
	 * @see com.zhongpin.pap.base.BaseAction#loadType()
	 */
	public String loadType() throws Exception {
		List<BaseTableColumn> elist = service.selecTableColumns(id);
		result.put("Rows", elist);
		return JSON;
	}

	/**
	 * 获得对象关键属性值列表 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Sep 9, 2013 6:00:00 PM<br/>
	 * 
	 * @return <br/>
	 * @throws Exception
	 * @see com.zhongpin.pap.base.BaseAction#loadType()
	 */
	public String loadTypeValue() throws Exception {
		List<ImportantObj> elistValue = service.selectImportantObjs(id);
		result.put("Rows", elistValue);
		return JSON;
	}

	/**
	 * 功能：业务对象属性查询页面<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2013-11-15 上午10:04:48 <br/>
	 */
	public String loadPropertySelectById() throws Exception {
		e = service.selectById(id);
		return "propertySelect";
	}

	/**
	 * 功能：权限过滤属性设置<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2013-11-15 下午01:56:13 <br/>
	 */
	public String authPropertySet() throws Exception {
		service.addAuthPropertySet(e);
		message = "MODIFYOK";
		text(message);
		return NONE;
	}

	/**
	 * 功能：权限对象绑定<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2013-11-18 下午04:06:12 <br/>
	 */
	public String bindAuthObj() throws Exception {
		service.operateBindAuthObj(e);
		message = "MODIFYOK";
		text(message);
		return NONE;
	}
	
	/**
	 * 功能：查询参考表信息<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-21 下午04:31:18 <br/>
	 */
	public String loadRefTableInfo() throws Exception {
		result = service.selectRefTableInfo(e);
		return JSON;
	}
	
	/**
	 * 查询业务对象信息，结果集不包含变更日志中的对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 16, 2013 9:54:11 AM <br/>
	 */
	public String loadByCont()throws Exception{
		
		service.selectByCont(e,pageBean);
		result.put("Rows", pageBean.getResult());
		result.put("Total", pageBean.getTotalCount());
		pageBean.setResult(null);
		result.put("pageBean", pageBean);
		return JSON;
	}

}
