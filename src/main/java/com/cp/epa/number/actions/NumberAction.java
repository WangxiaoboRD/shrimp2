package com.cp.epa.number.actions;
import com.cp.epa.base.BaseAction;
import com.cp.epa.exception.SystemException;
import com.cp.epa.number.services.INumberService;
import com.cp.epa.number.entity.Number;

public class NumberAction extends BaseAction<Number,INumberService> {
	
	private static final long serialVersionUID = -5985036165237878841L;

	/**
	 * 单据模式的更新
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:17:59 AM <br/>
	 */
	/*
	public String modifyByDetail()throws Exception{
		//service.update(e);
		if(service.updateByDetail(e)>0)
			message="MODIFYOK";
		text(message);
		return NONE;
	}
	*/
	
	public String selectDetail()throws Exception{
		Number number = service.selectById(id);
		message = "";
		if(number.getNumberDetails() == null || number.getNumberDetails().size()==0)
			message="OK";
		text(message);
		return NONE;
	}
	
	/**
	 * 保存
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:17:59 AM <br/>
	 */
	public String save()throws Exception{
		//service.update(e);
		if(e.getMarkSub().getEcode()==null || "".equals(e.getMarkSub().getEcode()))
			e.setMarkSub(null);
		message = (service.save(e)).toString();
		text(message);
		return NONE;
	}
	
	/**
	 * 简单对象拆分更新
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:17:59 AM <br/>
	 */
	public String modify()throws Exception{
		//service.update(e);
		//if(e.getMarkSub().getEcode()==null || "".equals(e.getMarkSub().getEcode()))
		//	e.getMap().remove("e.markSub.ecode");
		//if(service.updateHql(e)>0)
		service.update(e);
		message="MODIFYOK";
		text(message);
		return NONE;
	}
	
	/**
	 * 简单对象分页查询
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:13:54 AM <br/>
	 * @throws SystemException 
	 */
	public String loadByPageUse()throws Exception{
					
		service.selectAll(id,e,pageBean);
		result.put("Rows", pageBean.getResult());
		result.put("Total", pageBean.getTotalCount());
		pageBean.setResult(null);
		result.put("pageBean", pageBean);
		return JSON;
	}
	

}
