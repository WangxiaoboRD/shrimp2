package com.cp.epa.utils;

import java.util.Map;


/**
 * 类名：SqlModel  
 *
 * 功能
 * 该类的主要功能是是为了拼接生成有条件的查询，更新，删除功能的语句
 * 接口设计支持 SQL类型 HQL类型 QBC类型
 * @author dzl 
 * 创建时间：Apr 9, 2013 10:12:48 AM 
 * @version Apr 9, 2013
 */
public interface SqlModel<T> {
	/**
	 * 拼接sql类型的查询语句
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 9, 2013 10:12:08 AM <br/>
	 */
	T selectSqlModel(ISqlMap<String,String,String> qbcMap,Class<?> clazz);
	
	/**
	 * 拼接sql类型的更新语句
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 9, 2013 10:12:08 AM <br/>
	 */
	T updateSqlModel(ISqlMap<String,String,String> qbcMap,Class<?> clazz);
	
	/**
	 * 拼接sql类型的更新语句
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 9, 2013 10:12:08 AM <br/>
	 */
	T deleteSqlModel(ISqlMap<String,String,String> qbcMap,Class<?> clazz);
	
	/**
	 * 拼接HQL类型的查询语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 10:23:26 AM <br/>
	 */
	T selectHqlModel(ISqlMap<String,String,String> qbcMap,Class<?> clazz);
	
	/**
	 * 拼接HQL类型的更新语句 通过Id更新指定属性值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 10:23:26 AM <br/>
	 */
	T updateHqlModel(Object PK,Map<String,Object> setField,Class<?> clazz);
	
	/**
	 * 拼接HQL类型的更新语句 通过条件更新指定属性值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 10:23:26 AM <br/>
	 */
	T updateHqlModel(ISqlMap<String,String,String> qbcMap,Map<String,Object> setField,Class<?> clazz);
	
	/**
	 * 拼接HQL类型的删除语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 10:23:26 AM <br/>
	 */
	T deleteHqlModel(ISqlMap<String,String,String> qbcMap,Class<?> clazz);
	
	/**
	 * 拼接QBC类型的查询语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 10:23:26 AM <br/>
	 */
	T selectQBCModel(ISqlMap<String,String,String> qbcMap,Class<?> clazz);
}
