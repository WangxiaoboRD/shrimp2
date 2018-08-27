package com.cp.epa.bussobj.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.bussobj.entity.BaseTableColumn;
import com.cp.epa.bussobj.entity.BussObjProperty;
import com.cp.epa.utils.Pager;
/**
 * 
 * 类名：IBussObjPropertyService  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-8-8 上午10:43:44  <br />
 * @version 2013-8-8
 */
public interface IBussObjPropertyService extends IBaseService<BussObjProperty>{

	public void loadBussObjProperty(BussObjProperty e,Pager<BussObjProperty> page, String objName)throws Exception;
	
	
	public List<BaseTableColumn> selectBaseTableColumns(String fullClassName,boolean filter)throws Exception;
	
	/**
	 * 功能：根据条件查询业务对象属性<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-21 下午04:02:46 <br/>
	 */
	public String selectPropertyName(String fullClassName, String tabFieldCode) throws Exception;
}
