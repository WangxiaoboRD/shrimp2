package com.cp.epa.utils;

import java.util.Map;

/**
 * 
 * 类名：AbstractQBCModel  
 *
 * 功能：
 * @author dzl 
 * 创建时间：Apr 9, 2013 11:16:25 AM 
 * @version Apr 9, 2013
 */
public abstract class AbstractQBCModel<T> implements SqlModel<T>{
	
	/**
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 9, 2013 10:34:26 AM<br/>
	 * @param qbcMap 携带条件的Map
	 * @param clazz 要查询的主对象
	 * @param dc <br/> 接受组装好的拼接语句对象
	 * @see com.zhongpin.pap.utils.SqlModel#selectQBCModel(com.zhongpin.pap.utils.ICUDConditionCarryMap, java.lang.Class, org.hibernate.criterion.DetachedCriteria)
	 */
	public abstract T selectQBCModel(ISqlMap<String,String,String> qbcMap,Class<?> clazz);

	public T deleteHqlModel(
			ISqlMap<String, String, String> qbcMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public T deleteSqlModel(
			ISqlMap<String, String, String> qbcMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public T selectHqlModel(
			ISqlMap<String, String, String> qbcMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public T selectSqlModel(
			ISqlMap<String, String, String> qbcMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public T updateHqlModel(
			ISqlMap<String, String, String> qbcMap,Map<String,Object> setField, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public T updateHqlModel(Object PK,Map<String,Object> setField,Class<?> clazz){
		return null;
	}

	public T updateSqlModel(
			ISqlMap<String, String, String> qbcMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
