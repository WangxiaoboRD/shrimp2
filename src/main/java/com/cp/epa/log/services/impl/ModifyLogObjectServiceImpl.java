package com.cp.epa.log.services.impl;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.bussobj.entity.BussinessObject;
import com.cp.epa.log.dao.IModifyLogObjectDao;
import com.cp.epa.log.entity.ModifyLogObject;
import com.cp.epa.log.services.IModifyLogObjectService;

public class ModifyLogObjectServiceImpl extends BaseServiceImpl<ModifyLogObject,IModifyLogObjectDao> implements IModifyLogObjectService{

	/**
	 * 保存对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 16, 2013 10:42:51 AM <br/>
	 */
	public String saveAll(String ids)throws Exception{
		if(ids == null || "".equals(ids))
			return "";
		String[] arrayIds = ids.split(",");
		
		for(String id : arrayIds){
			ModifyLogObject mbo = new ModifyLogObject();
			BussinessObject bo = new BussinessObject();
			bo.setBussCode(id);
			mbo.setBussObj(bo);
			dao.insert(mbo);
		}
		return arrayIds.length+"";
	}
}
