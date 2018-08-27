package com.cp.epa.base;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.cp.epa.utils.ISqlMap;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.SqlMap;

//public interface IBaseService<T extends BaseEntity<ID>,ID extends Serializable>{
public interface IBaseService<T extends BaseEntity> {
	/**
	 * 保存实体对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @param entity 实体对象
	 * @version Mar 15, 2013 5:05:08 PM <br/>
	 * @return ID 返回值为ID
	 */
	Object save(T entity) throws Exception;
	
	/**
	 * 保存一批对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 8:57:49 AM <br/>
	 */
	void save(Collection<T> elist)throws Exception;
	
	/**
	 * 简单查询对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
	List<T> selectAll()throws Exception;
	
	/**
	 * 简单对象分页查询
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
     //List<T> selectAll(Class<?> entityClass);
	void selectAll(Pager<T> page)throws Exception;
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	void selectAll(T entity,Pager<T> page)throws Exception;
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	List<T> selectAll(T entity)throws Exception;
	
	/**
	 * 通过ID获取对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
	/*@SuppressWarnings("hiding")
	T selectById(Class<?> entityClass,String PK);*/
	<ID extends Serializable> T selectById(ID PK)throws Exception;
	
	/**
	 * 带条件类型的查询采用HQL模式
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 11:54:57 AM <br/>
	 */
	<K,O,V> List<T> selectHQL(ISqlMap<K,O,V> sqlMap) throws Exception;

	/**
	 * 带条件类型的查询采用HQL模式附带分页
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 11:54:57 AM <br/>
	 */
	<K,O,V> void selectHQL(ISqlMap<K,O,V> sqlMap,Pager<T> page)throws Exception;
	
	/**
	 * 按照页面获得的对象的所属属性与非对象条件进行查询
	 * 功能：<br/>
	 * @author 杜中良
	 * @version May 19, 2013 7:16:47 PM <br/>
	 */
	<K,O,V> void selectHQL(T entity,ISqlMap<K,O,V> SqlMap,Pager<T> page)throws Exception;
	
	/**
	 * 按照页面获得的对象的所属属性与非对象条件进行查询
	 * 功能：<br/>
	 * @author 杜中良  不分页
	 * @version May 19, 2013 7:16:47 PM <br/>
	 */
	<K,O,V> List<T> selectHQL(T entity,ISqlMap<K,O,V> SqlMap)throws Exception;
	
//	/**
//	 * 通过拼接的Hql语句查询对象并分页
//	 * 功能：<br/>
//	 *
//	 * @author 杜中良
//	 * @version Sep 12, 2013 5:09:49 PM <br/>
//	 */
//	void selectByHQLPage(String hql,Pager<T> page)throws Exception;
	
	/**
	 * 通过拼接的Hql语句查询对象不分页
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	/*Collection<T>*/ 
	List<T> selectByHQL(String hql)throws Exception;
	
	/**
	 * 通过拼接的Hql语句查询单个
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	/*Collection<T>*/ 
	T selectByHQLSingle(String hql)throws Exception;
	
	
	/**
	 * hql语句函数 返回值是字符串
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	/*Collection<T>*/ 
	Object selectByHQLFun(String hql)throws Exception;
	
	/**
	 * 通过单个条件查询一个对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 28, 2013 4:14:09 PM <br/>
	 */
	T selectBySinglet(String property,Object value)throws Exception;
	
	/**
	 * 通过单个条件获得一组对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 28, 2013 4:14:09 PM <br/>
	 */
	List<T> selectBySingletAll(String property,Object value)throws Exception;
	
	/**
	 * 
	 * 功能：<br/>
	 * 查询对象的某几个属性
	 * @param propertys 所要查询的属性数组
	 * @author 杜中良
	 * @version Mar 25, 2014 4:59:17 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	<K,O,V> List selectByProperty(String[] propertys,SqlMap<K,O,V> sqlMap)throws Exception;
	
	/**
	 * 
	 * 功能：<br/>
	 * 对对象进行函数查询
	 * @param propertys 所要查询的函数数组
	 * @param useAuth 是否要使用权限
	 * @author 杜中良
	 * @version Mar 25, 2014 4:59:17 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	<K,O,V> List selectByFunction(String[] functions,SqlMap<K,O,V> sqlMap,boolean useAuth)throws Exception;
	
	/**
	 * 
	 * 功能：<br/>
	 * 对对象进行属性与函数的混合查询
	 * @param propertys 所要查询的属性数组
	 * @param propertys 所要查询的函数数组
	 * @author 杜中良
	 * @version Mar 25, 2014 4:59:17 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	<K,O,V> List selectByPAndF(String[] propertys,String[] functions,SqlMap<K,O,V> sqlMap)throws Exception;
	
	/**
	 * 功能：查询符合条件的信息总条数<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-11-8 下午03:26:13 <br/>
	 */
	<K,O,V> int selectTotalRows(final SqlMap<K, O, V> sqlMap) throws Exception;
	
	/**
	 * 功能：根据单一条件查询数据条数<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-11-8 下午04:11:20 <br/>
	 */
	<K,O,V> int selectTotalRows(K property, O operator, V value) throws Exception;
	
	/**
	 * 功能：根据条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	<K,O,V> List<T> selectTopValue(SqlMap<K, O, V> sqlMap,int num) throws Exception;
	
	/**
	 * 功能：根据条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	<K,O,V> List<T> selectTopValue(SqlMap<K, O, V> sqlMap,int start,int end) throws Exception;
	
	/**
	 * 功能：根据单个条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	<K,V> List<T> selectTopValue(K property, V value,int start,int end) throws Exception;
	
	/**
	 * 功能：根据单个条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	<K,V> List<T> selectTopValue(K property, V value,int num) throws Exception;
	
	/**
	 * 功能：根据单个条件查询数据 取前num数据  带操作符号<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	<K,O,V> List<T> selectTopValue(K property,O operator ,V value,int num) throws Exception;
	
	/**
	 * 功能：根据单一条件查询数据条数，默认操作符是=<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	<K,V> int selectTotalRows(K property, V value) throws Exception;
	
	/**
	 * 功能：单个条件类型查询<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-11-10 下午04:59:14 <br/>
	 */
	<K,O,V> List<T> selectByConditionHQL(K property, O operator, V value)throws Exception;
	/**
	 * 功能：单个条件查询，默认操作符为=<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-11-10 下午04:59:44 <br/>
	 */
	<K,O,V> List<T> selectByConditionHQL(K property, V value)throws Exception;
	
	/**
	 * 更新一个对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
	void update(T entity)throws Exception;
	
	/**
	 * 更新一组对象
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 * @throws Exception 
	 */
	void updateList(Collection<T> entitys) throws Exception;
	
	/**
	 * 更新缓存中已有的对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 16, 2013 5:56:15 PM <br/>
	 * @throws Exception 
	 */
	void merge(T entity) throws Exception;
	
	/**
	 * 通过拆分对象为HQL语句更新对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
	int updateHql(T entity)throws Exception;

	/**
	 * 更新对象通过ID，set属性
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 11:58:06 AM <br/>
	 */
	<ID extends Serializable> int updateById(ID PK,Map<String,Object> filedValues)throws Exception;
	
	/**
	 * 更新对象通过ID，set属性
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 11:59:28 AM <br/>
	 */
	<K,O,V> int updateByCod(SqlMap<K,O,V> sqlMap,Map<String,Object> filedValues)throws Exception;
	
	/**
	 * 功能：<br/>
	 * 更新一组指定ID与指定更新属性的对象
	 * @author 杜中良
	 * @version May 19, 2013 10:45:54 AM <br/>
	 */
	<ID extends Serializable> int updateByIds(ID[] PK,Map<String,Object> filedValues)throws Exception;
	
	/**
	 * 功能：<br/>
	 * 更新一组指定ID与指定更新属性的对象
	 * @author 杜中良
	 * @version May 19, 2013 10:45:54 AM <br/>
	 */
	<ID extends Serializable> int updateByIds(Collection<ID> PK,Map<String,Object> filedValues)throws Exception;
	
	
	
//	/**
//	 * 单据模式的更新
//	 * 功能：<br/>
//	 *
//	 * @author 杜中良
//	 * @version Jun 16, 2013 5:23:21 PM <br/>
//	 */
//	int updateByDetail(T entity);
	
	/**
	 * 简单删除一个实体
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:11:16 AM <br/>
	 */
	int delete(T entity)throws Exception;
	
	/**
	 * 删除一组对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 9:42:14 AM <br/>
	 */
	int delete(List<T> elist)throws Exception;
	
	/**
	 * 通过对象ID删除对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:11:44 AM <br/>
	 */
	<ID extends Serializable>  int deleteById(ID PK)throws Exception;
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	<ID extends Serializable> int deleteByIds(ID[] PK)throws Exception;
	
	/**
	 * 清空对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 10:20:54 AM <br/>
	 */
	int delete()throws Exception;
	
	/**
	 * 带条件属性的删除
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version May 21, 2013 3:26:08 PM <br/>
	 */
	<K,O,V> int delete(ISqlMap<K,O,V> sqlMap)throws Exception;
	
	/**
	 * 通过单个条件删除一组对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 28, 2013 4:14:09 PM <br/>
	 */
	void deleteBySingletAll(String property,Object value)throws Exception;
	
	
	/**
	 * 保存单据类型的方法
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @param entity 实体对象
	 * @version Mar 15, 2013 5:05:08 PM <br/>
	 * @return ID 返回值为ID
	 */
	Object saveAndDetail(T entity) throws Exception;
	
	/**
	 * 执行DDL SQL语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 1, 2014 4:11:43 PM <br/>
	 */
	void execute(String ddlSQL)throws Exception;
	
	/**
	 * 执行普通插入sql语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 13, 2014 10:27:21 AM <br/>
	 */
	void executeUpdate(String sql)throws Exception;
	
	/**
	 * 文件操作
	 * 功能：<br/>
	 * 对于导入的文件进行操作，用户可以从写这个方法完成对文件的操作，比如存入数据库，或者设置保存路径等
	 * @author 杜中良
	 * @version Jun 30, 2013 11:50:26 AM <br/>
	 */
	Boolean operFile(File file,Object...objects)throws Exception;
	
	/**
	 * 导出信息
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jul 10, 2013 10:42:18 AM <br/>
	 */
	InputStream exportFile(T entity)throws Exception;
	
	/**
	 * 获取号码对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Aug 3, 2013 4:04:24 PM <br/>
	 * @ number           号码对象编码
	 * @ numberScope      阶段号编码
	 * @ year             年标示
	 * @ subobject		  子对象标示
	 */
	//String get(String number,String numberScope,String year,String subobject)throws Exception;
	/**
	 * 通过表名验证该表在该用户下面是不是已经存在
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：May 11, 2014 4:52:08 PM<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseDaoImpl#selectById(java.io.Serializable)
	 */
	Boolean selectTableIsExit(String sql)throws Exception;
	
	
	/**
	 *根据对象ID的数组，获得对象List
	 * 功能：<br/>
	 *
	 * @author 席金红
	 * @version 2015-10-20 上午09:17:18 <br/>
	 */
	public <ID extends Serializable> List<T> selectByIds(ID[] PK)throws Exception;
}
