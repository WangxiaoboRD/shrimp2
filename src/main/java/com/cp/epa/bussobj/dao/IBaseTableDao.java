package com.cp.epa.bussobj.dao;

import java.sql.Connection;

import com.cp.epa.base.IBaseDao;
import com.cp.epa.bussobj.entity.BaseTable;
/**
 * 类名：IBaseTableDao  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:27:54  <br />
 * @version 2013-7-5
 */
public interface IBaseTableDao extends IBaseDao<BaseTable>{
	
	public  String getDatabaseType()throws Exception;
	
	public Connection getConnection()throws Exception;

}
