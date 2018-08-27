package com.cp.epa.check.actions;
import com.cp.epa.base.BaseAction;
import com.cp.epa.check.entity.CheckBillState;
import com.cp.epa.check.entity.CheckState;
import com.cp.epa.check.services.ICheckBillStateService;
/**
 * 审核控制层
 * 类名：CheckAction  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 4:12:21 PM 
 * @version Dec 3, 2014
 */
@SuppressWarnings("serial")
public class CheckBillStateAction extends BaseAction<CheckBillState,ICheckBillStateService>{
	
	//获得className
	private String className;
	
	private CheckState checkState;
	
	private String level;
	
	/**
	 * 审核
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 7, 2014 10:17:24 AM <br/>
	 */
	public String check()throws Exception{
		boolean b=service.check(id,className);
	  	if(b){
	  		text("OK");
	  	}
		return NONE;
	}
	
	/**
	 * 反审核
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 7, 2014 10:17:24 AM <br/>
	 */
	public String cancleCheck()throws Exception{
		boolean b=service.cancleCheck(id,className);
	  	if(b){
	  		text("OK");
	  	}
		return NONE;
	}
	
	/**
	 * 判断是不是审核已经完成
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-3-3 下午06:18:44 <br/>
	 */
	public String isCheckOk()throws Exception{
		boolean b=service.isCheckOk(id,className);
	  	if(b){
	  		text("OK");
	  	}
		return NONE;
	}
	
	/**
	 * 审核查看
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 7, 2014 10:17:24 AM <br/>
	 */
	public String checkShow()throws Exception{
		checkState=service.selectShow(id,className,level);
		return SHOW;
	}
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public CheckState getCheckState() {
		return checkState;
	}

	public void setCheckState(CheckState checkState) {
		this.checkState = checkState;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
