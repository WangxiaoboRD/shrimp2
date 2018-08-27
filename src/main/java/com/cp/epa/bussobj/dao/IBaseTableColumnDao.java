package com.cp.epa.bussobj.dao;

import com.cp.epa.base.IBaseDao;
import com.cp.epa.bussobj.entity.BaseTableColumn;
/**
 * 
 * 类名：IBaseTableColumnDao  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:33:05  <br />
 * @version 2013-7-5
 */
public interface IBaseTableColumnDao extends IBaseDao<BaseTableColumn>{

	public  String getDatabaseType()throws Exception;
}
