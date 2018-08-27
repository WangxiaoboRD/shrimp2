package com.cp.epa.bussobj.dao.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import com.cp.epa.base.BaseDaoImpl;
import com.cp.epa.bussobj.dao.IBaseTableDao;
import com.cp.epa.bussobj.entity.BaseTable;
/**
 * 
 * 类名：BaseTableDaoImpl  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:28:53  <br />
 * @version 2013-7-5
 */
public class BaseTableDaoImpl extends BaseDaoImpl<BaseTable> implements IBaseTableDao{

	
	public  String getDatabaseType() throws Exception{
		String type="";
		Connection connection=jdbcTemplate.getDataSource().getConnection();
		try {
			DatabaseMetaData metaData=connection.getMetaData();
			String name=metaData.getDriverName().trim();
			name=name.toUpperCase();
			if(name.indexOf("ORACLE")!=-1){
				type="Oracle";
			}else if(name.indexOf("MYSQL")!=-1){
				type="Mysql";
			}else if(name.indexOf("SQL SERVER")!=-1){
				type="Sqlserver";
			}	
		} catch (SQLException e) {
			throw new Exception("获取数据库类型失败！",e);
		}finally{
			try {
				if(null!=connection){
				  connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
		return type;
	}

	public Connection getConnection() throws Exception {
		
		return jdbcTemplate.getDataSource().getConnection();
	}

}
