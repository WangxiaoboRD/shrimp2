package com.cp.epa.bussobj.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseTypeUtil {

	private static Map<String, String> mysqlMap=new HashMap<String, String>();
	private static Map<String, String> oralceMap=new HashMap<String, String>();
	@Resource
	protected JdbcTemplate jdbcTemplate;

	
	static{
				
		mysqlMap.put("int", "int");
		mysqlMap.put("float", "float");
		mysqlMap.put("decimal", "decimal");
		mysqlMap.put("varText", "varchar");
		mysqlMap.put("decMoney", "float");
		mysqlMap.put("varDate", "varchar");
	    mysqlMap.put("varTime", "varchar");
	    
	    oralceMap.put("int", "number");
	    oralceMap.put("float", "number");
	    oralceMap.put("decimal", "number");
	    oralceMap.put("varText", "varchar2");
	    oralceMap.put("decMoney", "number");
	    oralceMap.put("varDate", "varchar2");
	    oralceMap.put("varTime", "varchar2");
	}
	
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
	
	
	/**
	 * 
	 * 功能：<br/>
	 * 根据元素元素数据类型获得对应数据库类型
	 * @author zp
	 * @version 2013-11-16 上午09:32:42 <br/>
	 */
	public String getDataType(String key){
		
		String type="";
		try {
		    type=getDatabaseType();			
		} catch (Exception e) {
			
		}
		if(type.equals("Oracle")){
			return oralceMap.get(key);			
		}else{
			return mysqlMap.get(key);
		}

	}

	
	public static void main(String [] sw){
		
		for(String key:mysqlMap.keySet()){
			System.out.println(key);
		}
		
		
	}
	
	
}
