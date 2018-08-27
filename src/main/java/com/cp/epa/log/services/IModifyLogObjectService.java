package com.cp.epa.log.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.log.entity.ModifyLogObject;

/**
 * 
 * 类名：IModifyLogObjectService  
 *
 * 功能：需要记录变更日志的对象
 *
 * @author dzl 
 * 创建时间：Dec 14, 2013 3:36:25 PM 
 * @version Dec 14, 2013
 */
public interface IModifyLogObjectService extends IBaseService<ModifyLogObject> {
	
	/**
	 * 保存对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 16, 2013 10:42:51 AM <br/>
	 */
	public String saveAll(String ids)throws Exception;
}
