package com.cp.epa.utils;

/**
 * 类名：AbstractModel  
 *
 * 功能：拼接不同模式的CRUD 语句的适配器
 * @deprecated
 * @author dzl 
 * 创建时间：Apr 28, 2013 11:30:15 AM 
 * @version Apr 28, 2013
 */
public abstract class AbstractModel<T> implements SqlModel<T>{

	/**
	 * 拼接HQL模式下的删除查询语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 11:36:03 AM<br/>
	 * @param sqlMap 携带条件的Map
	 * @param clazz 要删除的主对象
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#deleteHqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T deleteHqlModel(ISqlMap<String, String, String> sqlMap,Class<?> clazz);
	/**
	 * 拼接sql模式下的删除语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 11:34:52 AM<br/>
	 * @param sqlMap 携带条件的Map
	 * @param clazz 要删除的主对象
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#deleteSqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T deleteSqlModel(ISqlMap<String, String, String> sqlMap,Class<?> clazz);
	/**
	 * 拼接Hql模式下的查询语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 11:38:29 AM<br/>
	 * @param sqlMap 携带条件的Map
	 * @param clazz 要查询的主对象
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#selectHqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T selectHqlModel(ISqlMap<String, String, String> sqlMap,Class<?> clazz);
	/**
	 * 拼接QBC模式下的查询语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 11:39:35 AM<br/>
	 * @param sqlMap 携带条件的Map
	 * @param clazz 要查询的主对象
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#selectQBCModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T selectQBCModel(ISqlMap<String, String, String> sqlMap,Class<?> clazz);
	/**
	 * 拼接SQL模式下的查询语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 11:43:04 AM<br/>
	 * @param sqlMap 携带条件的Map
	 * @param clazz 要查询的主对象
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#selectSqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T selectSqlModel(ISqlMap<String, String, String> sqlMap,Class<?> clazz);
	
	/**
	 * 拼接更新HQL语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 11:49:01 AM<br/>
	 * @param sqlMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#updateHqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T updateHqlModel(ISqlMap<String, String, String> sqlMap,Class<?> clazz);
	/**
	 * 拼接更新SQL语句
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 28, 2013 11:49:53 AM<br/>
	 * @param sqlMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.SqlModel#updateSqlModel(com.zhongpin.pap.utils.ISqlMap, java.lang.Class)
	 */
	public abstract T updateSqlModel(ISqlMap<String, String, String> sqlMap,Class<?> clazz);
}
