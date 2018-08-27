package com.cp.epa.bussobj.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.bussobj.entity.BaseTableColumn;
/**
 * 
 * 类名：IBaseTableColumnService  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:34:41  <br />
 * @version 2013-7-5
 */
public interface IBaseTableColumnService extends IBaseService<BaseTableColumn>{

	public List<BaseTableColumn> loadColumns()throws Exception;
	public BaseTableColumn loadDetail(BaseTableColumn baseTableColumn)throws Exception;
	
	/**
	 * 功能：根据字段编码、表编码查询字段描述<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-11-21 下午03:41:27 <br/>
	 */
	public String selectDesc(String fdcode, String tabCode) throws Exception;
	
}
