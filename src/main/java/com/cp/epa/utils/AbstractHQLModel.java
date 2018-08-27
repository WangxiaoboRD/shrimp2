package com.cp.epa.utils;

import java.util.Map;

/**
 * HQL语言模板适配器
 * 类名：AbstractHQLModel  
 * 功能：
 * @author dzl 
 * 创建时间：Apr 28, 2013 3:00:01 PM 
 * @version Apr 28, 2013
 */
public abstract class AbstractHQLModel<T> implements SqlModel<T>{
	
	public T selectQBCModel(ISqlMap<String,String,String> qbcMap,Class<?> clazz){
		return null;
	}
	/**
	 * 拼接HQL删除语句
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 3:01:37 PM<br/>
	 * @param qbcMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#deleteHqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T deleteHqlModel(ISqlMap<String, String, String> qbcMap, Class<?> clazz);

	public T deleteSqlModel(
			ISqlMap<String, String, String> qbcMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 拼接HQL查询语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 3:02:29 PM<br/>
	 * @param qbcMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#selectHqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T selectHqlModel(ISqlMap<String, String, String> qbcMap, Class<?> clazz);
	
	public T selectSqlModel(
			ISqlMap<String, String, String> qbcMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 拼接HQL更新语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 3:02:56 PM<br/>
	 * @param qbcMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#updateHqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T updateHqlModel(ISqlMap<String, String, String> qbcMap, Map<String,Object> setHql,Class<?> clazz);

	/**
	 * 拼接HQL类型的更新语句 通过Id更新指定属性值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 10:23:26 AM <br/>
	 */
	public abstract T updateHqlModel(Object PK,Map<String,Object> setField,Class<?> clazz);
	
	public T updateSqlModel(
			ISqlMap<String, String, String> qbcMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
