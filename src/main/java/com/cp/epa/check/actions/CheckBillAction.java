package com.cp.epa.check.actions;
import com.cp.epa.base.BaseAction;
import com.cp.epa.check.entity.CheckBill;
import com.cp.epa.check.services.ICheckBillService;
/**
 * 审核单据控制层
 * 类名：CheckBillAction  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 4:12:21 PM 
 * @version Dec 3, 2014
 */
@SuppressWarnings("serial")
public class CheckBillAction extends BaseAction<CheckBill,ICheckBillService>{
	/**
	 * 获取下拉框的值
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 16, 2013 3:10:16 PM <br/>
	 */
	public String loadTypeByCheck()throws Exception{
		elist = service.selectAllByCheck();
		result.put("Rows", elist);
		return JSON;
	}
	/**
	 * 删除
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Dec 4, 2014 7:53:21 PM<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseAction#delete()
	 */
	public String delete()throws Exception{	
		int k =service.deleteByIds(ids.split(","));
		// service.deleteByIds(ids.split(","));		
		message = k+"";		
		text(message);
		return NONE;
	}
}
