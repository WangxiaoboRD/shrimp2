package com.cp.epa.check.services.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.check.dao.ICheckBillDao;
import com.cp.epa.check.entity.Check;
import com.cp.epa.check.entity.CheckBill;
import com.cp.epa.check.services.ICheckBillService;
import com.cp.epa.check.services.ICheckService;
import com.cp.epa.exception.SystemException;
/**
 * 审核单据服务实现
 * 类名：CheckBillServiceImpl  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 4:03:04 PM 
 * @version Dec 3, 2014
 */
public class CheckBillServiceImpl extends BaseServiceImpl<CheckBill,ICheckBillDao> implements ICheckBillService{

	@Resource
	private ICheckService checkService;
	
	/**
	 * 查询并过滤已经配置过的
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
	public List<CheckBill> selectAllByCheck()throws Exception{
		//获得目前所有的单据
		List<CheckBill> cblist = super.selectAll();
		//已生成配置的单据
		List<Check> clist = checkService.selectAll();
		//过滤
		if(clist == null || clist.size()==0)
			return cblist;
		List<CheckBill> _cbList = new ArrayList<CheckBill>();
		for(CheckBill cb : cblist){
			int t = 0;
			for(Check c : clist){
				if(cb.getClassName().equals(c.getObjName()))
					t++;
			}
			if(t==0)
				_cbList.add(cb);
		}
		return _cbList;
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID className : PK){
			//验证该单据是不是已经配置
			Check c = checkService.selectBySinglet("objName", className);
			if(c != null)
				throw new SystemException("单据已经配置，不能删除");
			super.deleteBySingletAll("className", className);
		}
		return PK.length;
	}
}
