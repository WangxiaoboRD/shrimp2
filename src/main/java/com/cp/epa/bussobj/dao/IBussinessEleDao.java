package com.cp.epa.bussobj.dao;

import com.cp.epa.base.IBaseDao;
import com.cp.epa.bussobj.entity.BussinessEle;
/**
 * 
 * 类名：IBussinessEleDao  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-1 上午10:29:57  <br />
 * @version 2013-7-1
 */
public interface IBussinessEleDao extends IBaseDao<BussinessEle>{

	public  String getDatabaseType()throws Exception;
	
}
