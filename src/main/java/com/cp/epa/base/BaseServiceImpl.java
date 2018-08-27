package com.cp.epa.base;

import java.beans.Introspector;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.persistence.Transient;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.cp.epa.utils.ExcelUtil;
import com.cp.epa.utils.ISqlMap;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;
import com.cp.epa.utils.TypeUtil;


//public abstract class BaseServiceImpl<T extends BaseEntity<ID>,Dao extends IBaseDao<T,ID> ,ID extends Serializable> extends Base implements ApplicationContextAware,IBaseService<T ,ID> {
public abstract class BaseServiceImpl<T extends BaseEntity,Dao extends IBaseDao<T>> extends Base implements ApplicationContextAware,IBaseService<T> {

	//当前service所对应的Dao持久化操作组件
	protected Dao dao;
	//spring容器ApplicationContext对象，并通过该对象的getBean(Object Name)方法获取实例
	protected ApplicationContext ctx;
	//审核对象处理
	
	
	
	/**
	 * 获取spring容器ApplicationContext对象
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 16, 2013 11:09:39 AM<br/>
	 * @param applicationContext
	 * @throws BeansException <br/>
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		// TODO Auto-generated method stub
		this.ctx = applicationContext;
	}
	/**
	 * 初始化当前service所对应的Dao持久化操作组件
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 16, 2013 11:10:40 AM <br/>
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		Class<Dao> _daoClass = (Class<Dao>)TypeUtil.getActualTypes(this.getClass())[1];
		String _daoName = Introspector.decapitalize(_daoClass.getSimpleName().substring(1)+"Impl");
		dao = (Dao)ctx.getBean(_daoName);
	}
	
	/**
	 * 简单添加功能
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 22, 2013 10:36:54 AM<br/>
	 * @param entity 被保存的实体
	 * @return <br/>
	 * @see com.zhongpin.pap.services.IBaseService#save(com.zhongpin.pap.entity.BaseEntity)
	 */
	public Object save(T entity) throws Exception{
		return dao.insert(entity);
	}
	
	
	/**
	 * 保存单据类型的方法
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @param entity 实体对象
	 * @version Mar 15, 2013 5:05:08 PM <br/>
	 * @return ID 返回值为ID
	 */
	public Object saveAndDetail(T entity) throws Exception{
		return dao.insert(TypeUtil.getEntitySetDetail(entity));
	}
	
	/**
	 * 保存一批对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 8:57:49 AM <br/>
	 */
	public void save(Collection<T> elist)throws Exception{
		for(T t : elist){
			save(t);
		}
	}
	/**
	 * 简单查询对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
	//public List<T> selectAll(Class<?> entityClass){
	public List<T> selectAll()throws Exception{
		//return dao.selectAll(entityClass);
		return dao.selectAll();
	}
	
	/**
	 * 简单对象分页查询
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
     //List<T> selectAll(Class<?> entityClass);
	public void selectAll(Pager<T> page)throws Exception{
		dao.selectAll(page);
		
	}
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public void selectAll(T entity,Pager<T> page)throws Exception{
		dao.selectAll(entity, page);
	}
	
	/**
	 * 按照页面搜索条件分页查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	public List<T> selectAll(T entity)throws Exception{
		return dao.selectAll(entity);
	}
	
	/**
	 * 通过ID获取对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
	/*@SuppressWarnings("hiding")
	public T selectById(Class<?> entityClass,String PK){*/
	public <ID extends Serializable> T selectById(ID PK)throws Exception{
		return dao.selectById(PK);
	}
	
	/**
	 * 按照设置的条件进行查询
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：May 30, 2013 9:57:45 PM<br/>
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param sqlMap
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseService#selectByConditionHQL(com.zhongpin.pap.utils.ISqlMap)
	 */
	public <K, O, V> List<T> selectHQL(ISqlMap<K, O, V> sqlMap) throws Exception{
		// TODO Auto-generated method stub
		return dao.selectByConditionHQL(sqlMap);
	}
	
	/**
	 * 按照指定的条件进行查询并进行分页
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：May 30, 2013 9:57:53 PM<br/>
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param sqlMap
	 * @param page <br/>
	 * @see com.zhongpin.pap.base.IBaseService#selectByConditionHQL(com.zhongpin.pap.utils.ISqlMap, com.zhongpin.pap.utils.Pager)
	 */
	public <K, O, V> void selectHQL(ISqlMap<K, O, V> sqlMap,Pager<T> page) throws Exception{
		dao.selectByConditionHQL(sqlMap, page);
	}
	
	/**
	 * 按照页面条件，与指定条件进行分页查询
	 * 功能：<br/>
	 * @author 杜中良
	 * @version May 30, 2013 10:01:53 PM <br/>
	 */
	public <K, O, V> void selectHQL(T entity,ISqlMap<K, O, V> SqlMap, Pager<T> page) throws Exception{
		dao.selectByConditionHQL(entity, SqlMap, page);
	}
	
	/**
	 * 按照页面获得的对象的所属属性与非对象条件进行查询
	 * 功能：<br/>
	 * @author 杜中良  不分页
	 * @version May 19, 2013 7:16:47 PM <br/>
	 */
	public <K,O,V> List<T> selectHQL(T entity,ISqlMap<K,O,V> SqlMap)throws Exception{
		return dao.selectByConditionHQL(entity,SqlMap);
	}
	
//	/**
//	 * 通过拼接的Hql语句查询对象并分页
//	 * 功能：<br/>
//	 *
//	 * @author 杜中良
//	 * @version Sep 12, 2013 5:09:49 PM <br/>
//	 */
//	public void selectByHQLPage(String hql,Pager<T> page)throws Exception{
//		dao.selectByHQLPage(hql, page);
//	}
	
	/**
	 * 通过拼接的Hql语句查询对象不分页
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	/*Collection<T>*/ 
	public List<T> selectByHQL(String hql)throws Exception{
		return dao.selectByHQL(hql);
	}
	
	/**
	 * 通过拼接的Hql语句查询单个
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	public T selectByHQLSingle(String hql)throws Exception{
		return dao.selectByHQLSingle(hql);
	}
	
	/**
	 * hql语句函数 返回值是字符串
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	/*Collection<T>*/ 
	public Object selectByHQLFun(String hql)throws Exception{
		return dao.selectByHQLFun(hql);
	}
	
	/**
	 * 通过单个条件查询一个对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 28, 2013 4:14:09 PM <br/>
	 */
	public T selectBySinglet(String property,Object value)throws Exception{
		return dao.selectBySinglet(property,value);
	}
	
	/**
	 * 通过单个条件获得一组对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 28, 2013 4:14:09 PM <br/>
	 */
	public List<T> selectBySingletAll(String property,Object value)throws Exception{
		return dao.selectBySingletAll(property,value);
	}
	
	/**
	 * 
	 * 功能：<br/>
	 * 查询对象的某几个属性
	 * @param propertys 所要查询的属性数组
	 * @author 杜中良
	 * @version Mar 25, 2014 4:59:17 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	public <K,O,V> List selectByProperty(String[] propertys,SqlMap<K,O,V> sqlMap)throws Exception{
		return dao.selectByProperty(propertys, sqlMap);
	}
	
	/**
	 * 
	 * 功能：<br/>
	 * 对对象进行函数查询
	 * @param propertys 所要查询的函数数组
	 * @author 杜中良
	 * @version Mar 25, 2014 4:59:17 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	public <K,O,V> List selectByFunction(String[] functions,SqlMap<K,O,V> sqlMap,boolean useAuth)throws Exception{
		return dao.selectByFunction(functions, sqlMap,useAuth);
	}
	
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
	public <K,O,V> List selectByPAndF(String[] propertys,String[] functions,SqlMap<K,O,V> sqlMap)throws Exception{
		return dao.selectByPAndF(propertys, functions, sqlMap);
	}
	/**
	 * 功能: 根据单一条件查询数据条数<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-8 下午04:20:30<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param property
	 * @param operator
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseService#selectTotalRows(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, O, V> int selectTotalRows(K property, O operator, V value) throws Exception {
		return dao.selectTotalRows(property, operator, value);
	}
	/**
	 * 功能: 根据单一条件查询数据条数，默认操作符是=<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-8 下午04:20:30<br/>
	 * 
	 * @param <K>
	 * @param <V>
	 * @param property
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseService#selectTotalRows(java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, V> int selectTotalRows(K property, V value) throws Exception {
		return dao.selectTotalRows(property, value);
	}
	/**
	 * 功能: 查询符合条件的信息总条数<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-8 下午04:20:30<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param sqlMap
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseService#selectTotalRows(com.zhongpin.pap.utils.SqlMap)
	 */
	@Override
	public <K, O, V> int selectTotalRows(SqlMap<K, O, V> sqlMap) throws Exception {
		return dao.selectTotalRows(sqlMap);
	}
	
	/**
	 * 功能: 根据单一条件查询<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午05:10:59<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param property
	 * @param operator
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseService#selectByConditionHQL(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, O, V> List<T> selectByConditionHQL(K property, O operator, V value) throws Exception {
		return dao.selectByConditionHQL(property, operator, value);
	}
	/**
	 * 功能: 根据单一条件查询（操作符默认为=）<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午05:10:59<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param property
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseService#selectByConditionHQL(java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, O, V> List<T> selectByConditionHQL(K property, V value) throws Exception {
		return dao.selectByConditionHQL(property, value);
	}
	/**
	 * 更新一个对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
	public void update(T entity)throws Exception{
		dao.update(entity);
	}
	
	/**
	 * 更新一组对象
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
	public void updateList(Collection<T> entitys)throws Exception{
		dao.updateList(entitys);
	}
	
	/**
	 * 更新缓存中已有的对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 16, 2013 5:56:15 PM <br/>
	 */
	public void merge(T entity)throws Exception{
		dao.merge(entity);
	}
	
	/**
	 * 功能: <br/>
	 * 从页面上获取数据封装对象进行更新
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：May 19, 2013 10:14:17 AM<br/>
	 * 
	 * @param entity
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseService#updateHql(com.zhongpin.pap.base.BaseEntity)
	 */
	public int updateHql(T entity)throws Exception{
		//if(entity != null)
		//	throw new SystemException("对象不为空");
		return dao.updateHql(entity);
	}

	/**
	 * 更新对象通过ID，set属性
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 11:58:06 AM <br/>
	 */
	public <ID extends Serializable> int updateById(ID PK,Map<String,Object> filedValues)throws Exception{
		return dao.updateById(PK, filedValues);
	}

	/**
	 * 功能: <br/>
	 * 指定更新条件，指定更新字段进行更新
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：May 30, 2013 10:04:18 PM<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param sqlMap  更新条件
	 * @param filedValues 更新字段
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseService#update(com.zhongpin.pap.utils.SqlMap, java.util.Map)
	 */
	public <K, O, V> int updateByCod(SqlMap<K, O, V> sqlMap,Map<String, Object> filedValues)throws Exception {
		return dao.updateByCod(sqlMap, filedValues);
	}
	
	/**
	 * 功能: <br/>
	 * 更新指定Id列表，指定更新字段的操作
	 * 重写：杜中良 <br/>
	 * @version ：May 30, 2013 10:05:44 PM<br/>
	 * @param <ID>
	 * @param PK 更新ID列表
	 * @param filedValues 更新字段
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseService#update(java.util.Collection, java.util.Map)
	 */
	public <ID extends Serializable> int updateByIds(ID[] PK,Map<String, Object> filedValues) throws Exception {
		// TODO Auto-generated method stub
		return dao.updateByIds(PK, filedValues);
	}
	
	/**
	 * 功能: <br/>
	 * 更新指定Id列表，指定更新字段的操作
	 * 重写：杜中良 <br/>
	 * @version ：May 30, 2013 10:05:44 PM<br/>
	 * @param <ID>
	 * @param PK 更新ID列表
	 * @param filedValues 更新字段
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseService#update(java.util.Collection, java.util.Map)
	 */
	public <ID extends Serializable> int updateByIds(Collection<ID> PK,Map<String, Object> filedValues) throws Exception {
		// TODO Auto-generated method stub
		return dao.updateByIds(PK, filedValues);
	}
	
//	/**
//	 * 单据模式的更新
//	 * 功能：<br/>
//	 *
//	 * @author 杜中良
//	 * @version Jun 16, 2013 5:23:21 PM <br/>
//	 */
//	public int updateByDetail(T entity){
//		
//		
//		
//		
//		return 0;
//	}
	
	/**
	 * 通过拆分对象为HQL语句更新对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
//	public int updateHql(T entity){
//		return dao.updateHql(entity);
//	}
	
	/**
	 * 简单删除一个实体
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:11:16 AM <br/>
	 */
	public int delete(T entity)throws Exception{
		return dao.delete(entity);
	}
	
	/**
	 * 删除一组对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 9:42:14 AM <br/>
	 */
	public int delete(List<T> elist)throws Exception{
		return dao.delete(elist);
	}
	
	/**
	 * 通过对象ID删除对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:11:44 AM <br/>
	 */
	public <ID extends Serializable> int deleteById(ID PK)throws Exception{
		return dao.deleteById(PK);
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] PK)throws Exception{
		return dao.deleteByIds(PK);
	}
	
	/**
	 * 清空对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 10:20:54 AM <br/>
	 */
	public int delete()throws Exception{
		return dao.delete();
	}
	
	/**
	 * 按照传入的条件进行删除
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：May 30, 2013 9:57:33 PM<br/>
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param sqlMap
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseService#delete(com.zhongpin.pap.utils.ISqlMap)
	 */
	public <K, O, V> int delete(ISqlMap<K, O, V> sqlMap)throws Exception {
		return dao.delete(sqlMap);
	}
	
	/**
	 * 通过单个条件删除一组对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 28, 2013 4:14:09 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	public void deleteBySingletAll(String property,Object value)throws Exception{
		dao.deleteBySingletAll(property, value);
	}
	
	/**
	 * 执行DDL SQL语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 1, 2014 4:11:43 PM <br/>
	 */
	public void execute(String ddlSQL)throws Exception{
		dao.execute(ddlSQL);
	}
	
	/**
	 * 执行普通插入sql语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 13, 2014 10:27:21 AM <br/>
	 */
	public void executeUpdate(String sql)throws Exception{
		dao.execute(sql);
	}
	
	
	/**
	 * 文件操作
	 * 功能：<br/>
	 * 对于导入的文件进行操作，用户可以从写这个方法完成对文件的操作，比如存入数据库，或者设置保存路径等
	 * @author 杜中良
	 * @version Jun 30, 2013 11:50:26 AM <br/>
	 * @throws Exception 
	 */
	public Boolean operFile(File file,Object...objects) throws Exception{
		return false;
	}
	
	/**
	 * 文件导出功能
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Jul 10, 2013 10:43:13 AM<br/>
	 * 
	 * @param entity
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseService#exportFile(com.zhongpin.pap.base.BaseEntity)
	 */
	public InputStream exportFile(T entity)throws Exception{
		List<T> datas = dao.selectAll(entity);
//		Map<String,Object> map = entity.getMap();
//		String[] files = (String[])map.get("fields");
//		String value = PapUtil.arrayToString(files);
//		//value = new String(value.getBytes("iso-8859-1"),"utf-8");
//		String[] headers = value.split(",");
//		map.clear();
		
		String fields = entity.getExportFields();
		String[] headers = fields.split(",");
		
		return ExcelUtil.exportExcel(headers, datas);
	}
	
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
//	public String get(String number,String numberScope,String year,String subobject)throws Exception{
//		
//		System.out.println("-------:"+numberService);
//		
//		return numberService.get(number, numberScope, year, subobject);
//	}
	
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
	public Boolean selectTableIsExit(String sql)throws Exception{
		return dao.selectTableIsExit(sql);
	}
	
	/**
	 *根据对象ID的数组，获得对象List
	 * 功能：<br/>
	 *
	 * @author 席金红
	 * @version 2015-10-20 上午09:17:18 <br/>
	 */
	public <ID extends Serializable> List<T> selectByIds(ID[] PK)throws Exception{
		List<T> tlist = new ArrayList<T>();
		for(ID id : PK){
			T t = dao.selectById(id);
			tlist.add(t);
		}
		return tlist;	
	}
	
	/**
	 * 功能：根据条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	public <K,O,V> List<T> selectTopValue(SqlMap<K, O, V> sqlMap,int num) throws Exception{
		return dao.selectTopValue(sqlMap, 0, num);
	}
	
	/**
	 * 功能：根据条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	public <K,O,V> List<T> selectTopValue(SqlMap<K, O, V> sqlMap,int start,int end) throws Exception{
		return dao.selectTopValue(sqlMap, start, end);
	}
	
	/**
	 * 功能：根据单个条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	public <K,V> List<T> selectTopValue(K property, V value,int start,int end) throws Exception{
		return dao.selectTopValue(property,null,value,start, end);
	}
	
	/**
	 * 功能：根据单个条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	public <K,V> List<T> selectTopValue(K property, V value,int num) throws Exception{
		return dao.selectTopValue(property,null,value,0, num);
	}
	
	/**
	 * 功能：根据单个条件查询数据 取前num数据  带操作符号<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	public <K,O,V> List<T> selectTopValue(K property,O operator ,V value,int num) throws Exception{
		return dao.selectTopValue(property,operator,value,0, num);
	}
	/**
	 * 判断page里的sortName是否可以排序
	 * @param page
	 * @return
	 * @throws Exception
	 */
	protected boolean siSort(Pager<T> page)throws Exception{
		String sortName=page.getSortName();
//		String sortOrder=page.getSortorder();
//		boolean flagSort=false;
		Class<T> clazz=(Class<T>)TypeUtil.getActualTypes(this.getClass())[0];
		Field[] fields=clazz.getDeclaredFields();
		for(Field field:fields){
			String fieldName=field.getName();
			if(sortName.equals(fieldName)){
				//判断属性是不是transient
				String methodName="get"+PapUtil.toFirstLetterUpperCase(fieldName);
				Method method=clazz.getMethod(methodName, null);
				if(method.isAnnotationPresent(Transient.class))
					continue;
				return true;
			}
		}
		return false;
	}
}
