package com.cp.epa.bussobj.services;

import java.util.List;

import com.cp.epa.base.IBaseService;
import com.cp.epa.bussobj.entity.BaseTable;
import com.cp.epa.bussobj.entity.BaseTableColumn;
/**
 * 
 * 类名：IBaseTableService  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:29:45  <br />
 * @version 2013-7-5
 */
public interface IBaseTableService extends IBaseService<BaseTable>{

	  /**
	   * 
	   * 功能：<br/>
	   * 激活创建表
	   * @author zp
	   * @version 2013-7-5 下午03:34:51 <br/>
	   */
	  public String activeTable(String [] ids)throws Exception;
	  
	  /**
	   * 
	   * 功能：<br/>
	   * 校验记录明细
	   * @author zp
	   * @version 2013-7-8 下午08:55:51 <br/>
	   */
	  public String checkRecord(BaseTableColumn column)throws Exception;
	  
	  
	  /**
	   * 
	   * 功能：<br/>
	   *
	   * @author zp
	   * @version 2013-7-8 下午09:15:34 <br/>
	   */
	  public String  createTable(List<BaseTableColumn> columns ,String tableName)throws Exception;
	  
	  /**
	   * 
	   * 功能：<br/>
	   *
	   * @author zp
	   * @version 2013-7-8 下午09:16:14 <br/>
	   */
	  public int operatePhysicalTable(String sql)throws Exception;
	  
	  /**
	   * 
	   * 功能：<br/>
	   *
	   * @author zp
	   * @version 2013-7-8 下午09:16:57 <br/>
	   */
	  public void checkRefKey(List<BaseTableColumn> column ,String refTab)throws Exception;
	  
	  /**
	   * 
	   * 功能：<br/>
	   *
	   * @author zp
	   * @version 2013-7-8 下午09:17:31 <br/>
	   */
	  public String createMysqlSql(List<BaseTableColumn> columns ,String tableName)throws Exception;
	  
	  

	  
	
}
