package com.cp.epa.check.services.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.check.dao.ICheckDao;
import com.cp.epa.check.entity.Check;
import com.cp.epa.check.entity.CheckBill;
import com.cp.epa.check.entity.CheckRole;
import com.cp.epa.check.services.ICheckBillService;
import com.cp.epa.check.services.ICheckRoleService;
import com.cp.epa.check.services.ICheckService;
import com.cp.epa.exception.SystemException;
/**
 * 审核对象服务实现
 * 类名：CheckServiceImpl  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 4:03:04 PM 
 * @version Dec 3, 2014
 */
public class CheckServiceImpl extends BaseServiceImpl<Check,ICheckDao> implements ICheckService{
	
	@Resource
	private ICheckBillService checkBillService;
	@Resource
	private ICheckRoleService checkRoleService;
	/**
	 * 保存实体对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @param entity 实体对象
	 * @version Mar 15, 2013 5:05:08 PM <br/>
	 * @return ID 返回值为ID
	 */
	public Object save(Check entity) throws Exception{
		if(entity == null)
			throw new SystemException("对象不存在");
		String objName = entity.getObjName();
		if(objName != null && !"".equals(objName)){
			CheckBill cb = checkBillService.selectBySinglet("className", objName);
			entity.setBussName(cb.getBussName());
		}
		return super.save(entity);
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		for(ID id : PK){
			//验证该单据是不是已经配置
			Check c = super.selectById(id);
			List<CheckRole> crlist = checkRoleService.selectBySingletAll("className", c.getObjName());
			if(crlist != null && crlist.size()>0)
				throw new SystemException("单据配置已经使用，不能删除除");
			super.deleteById(id);
		}
		return PK.length;
	}
	
	/**
	 * 通过拆分对象为HQL语句更新对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
	public int updateHql(Check entity)throws Exception{
		if(entity != null){
			List<CheckRole> crlist = checkRoleService.selectBySingletAll("className", entity.getObjName());
			if(crlist == null || crlist.size()==0)
				return super.updateHql(entity);
			else
				throw new SystemException("单据【"+entity.getBussName()+"】已经设置权限，不允许修改");
		}
		return 0;
	}
}
