/**
 * 文件名：@IBussObjAuthPropertyService.java <br/>
 * 包名：com.zhongpin.pap.permission.services <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.permission.entity.BussObjAuthProperty;

/**
 * 类名：IBussObjAuthPropertyService  <br />
 *
 * 功能：业务对象过滤属性业务逻辑层定义
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 下午02:35:54  <br />
 * @version 2013-11-6
 */
public interface IBussObjAuthPropertyService extends IBaseService<BussObjAuthProperty> {
	
	/**
	 * 功能：根据业务对象查询权限过滤属性信息<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-24 下午02:52:57 <br/>
	 */
	public List<BussObjAuthProperty> selectByBussObj(String bussObjCode) throws Exception;
	
	/**
	 * 功能：根据业务对象编码和属性id查询权限过滤属性信息<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-5 上午10:52:30 <br/>
	 */
	public BussObjAuthProperty selectByBussObj(String bussObjCode, int propertyId) throws Exception;
	
	/**
	 * 功能：启用<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-18 下午03:52:47 <br/>
	 */
	public void enable(Integer[] ids) throws Exception;
	
	/**
	 * 功能：禁用、停用<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-18 下午03:52:58 <br/>
	 */
	public void disable(Integer[] ids) throws Exception;

}
