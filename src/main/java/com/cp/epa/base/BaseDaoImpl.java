package com.cp.epa.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.util.Assert;

import com.cp.epa.utils.HqlModel;
import com.cp.epa.utils.ISqlMap;
import com.cp.epa.utils.Pager;
import com.cp.epa.utils.QBCModel;
import com.cp.epa.utils.SqlMap;
import com.cp.epa.utils.TypeUtil;

//public abstract class BaseDaoImpl<T extends BaseEntity<ID>,ID extends Serializable> extends Base implements IBaseDao<T,ID> {
public abstract class BaseDaoImpl<T extends BaseEntity> extends Base implements IBaseDao<T> {		
	//Autowired是spring自身提供的注解方式，默认的搜索方式是按照类型搜索，也就是说如果某个类在spring中如果注册两个对象默认情况下会报错
	//@Autowired
	//Resource注解是按照JSR250标准定制的，属于JDK自带的，默认搜索方式是按照名称，也就是说spring容器中必须存在一个name="hibernateTemplate"的类
	@Resource
	protected HibernateTemplate hibernateTemplate;
	@Resource
	protected JdbcTemplate jdbcTemplate;
	@Resource
	private SessionFactory sessionFactory;
	//实体对象类型
	protected Class<T> entityClass;
	@Resource
	private HqlModel hqlModel;
	
	/**
	 * 初始化当前Dao所对应的Entity实体
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 16, 2013 11:10:40 AM <br/>
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	protected void init(){
		entityClass = (Class<T>)TypeUtil.getActualTypes(this.getClass())[0];
	}
	
	/**
	 * 功能：<br/>
	 * 初始化session
	 * @author 杜中良
	 * @version Mar 26, 2013 5:04:02 PM <br/>
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	//---------------------------------添加方法----------------------------------------

	/**
	 * 保存对象
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 16, 2013 9:56:30 AM<br/>
	 * @param entity
	 * @return <br/>
	 * @see com.zhongpin.pap.dao.IBaseDao#insert(com.zhongpin.pap.entity.BaseEntity)
	 */
	@SuppressWarnings("unchecked")
	public Object insert(T entity)throws Exception {
		// TODO Auto-generated method stub
		//logger.info("dao层实现类保存成功");
		//return hibernateTemplate.save(entity);
		return getSession().save(entity);
	}
	
	/**
	 * 执行普通插入sql语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 13, 2014 10:27:21 AM <br/>
	 */
	public void executeUpdate(String sql)throws Exception{
		jdbcTemplate.execute(sql);
	}
	
//	/**
//	 * 保存一批对象
//	 * 功能：<br/>
//	 * @author 杜中良
//	 * @version Apr 29, 2013 8:57:49 AM <br/>
//	 */
//	public void insert(Collection<T> elist){
//		for(T t : elist){
//			insert(t);
//		}
//	}
	
	//---------------------------------查询方法----------------------------------------

	/**
	 * 简单查询对象
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Mar 22, 2013 10:19:07 AM<br/>
	 * @param 
	 * @return <br/>
	 * @see com.zhongpin.pap.dao.IBaseDao#select(java.lang.Object)
	 */
	@SuppressWarnings({ "unchecked" }) //---------------------------------------------------------------------------
	//public List<T> selectAll(Class<?> entityClass){//这个Class<?> entityClass是从action中获取
	public List<T> selectAll()throws Exception{
		//String hql = "from " + entityClass.getName(); //目前改正为从DAO层中的泛型中获取
		String hql = hqlModel.buildClassSelectHql(entityClass);
		return (List<T>) hibernateTemplate.find(hql);
	}
	
	/**
	 * 简单对象分页查询
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
     //List<T> selectAll(Class<?> entityClass);//-------------------------------------------------------------------------
	public void selectAll(Pager<T> page)throws Exception{
		String hql = hqlModel.buildClassSelectHql(entityClass);
		//String hql = "from " + entityClass.getName() +" as e order by e."+page.getSortName()+" "+page.getSortorder(); 
		hql += " order by e."+page.getSortName()+" "+page.getSortorder(); 
		selectAllByPage(hql,page);
	}
	
	/**
	 * 按照页面提交的搜索条件分页查询对象
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 18, 2013 5:54:29 PM<br/>
	 * @see com.zhongpin.pap.dao.IBaseDao#selectAll()
	 */
	public void selectAll(T entity,Pager<T> page)throws Exception{
		selectAllByPage(hqlModel.selectHqlModel(entity,page),page);
	}
	
	/**
	 * 按照页面搜索条件查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	public List<T> selectAll(T eneity)throws Exception{
		String hql = hqlModel.selectHqlModel(eneity);
		return (List<T>) hibernateTemplate.find(hql);
	}
	
	/**
	 * 通过ID获取对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Mar 22, 2013 10:17:38 AM <br/>
	 */
	@SuppressWarnings({ "unchecked"})
	//public T selectById(Class<?> entityClass,String PK){//---------------------------------------------------------------------------
	public <ID extends Serializable> T selectById(ID PK)throws Exception{
		 return (T) hibernateTemplate.get(entityClass, PK);
	}
	
	/**
	 * 带条件类型的查询
	 * 功能：<br/>
	 *
	 * @author 杜中良？？？？？？？？？
	 * @version Apr 9, 2013 11:54:57 AM <br/>
	 */
	public <K,O,V> List<T> selectByConditionQBC(ISqlMap<K,O,V> sqlMap)throws Exception{
		return selectByConditions(sqlMap);
	}
	
	/**
	 * 带条件类型的查询采用HQL模式
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 11:54:57 AM <br/>
	 */
	public <K, O, V> List<T> selectByConditionHQL(ISqlMap<K, O, V> sqlMap)throws Exception {
		// TODO Auto-generated method stub
		String hql = hqlModel.selectHqlModel(sqlMap, entityClass);
		return selectAll(hql);
	}
	
	/**
	 * 带条件类型的查询采用HQL模式附带分页
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 9, 2013 11:54:57 AM <br/>
	 */
	public <K, O, V>  void selectByConditionHQL(ISqlMap<K, O, V> sqlMap, Pager<T> page)throws Exception {
		// TODO Auto-generated method stub
		String hql = hqlModel.selectHqlModel(sqlMap, entityClass);
		
		if (!hql.contains("order by"))
			hql += " order by e." + page.getSortName() + " " + page.getSortorder();
			
		selectAllByPage(hql,page);
	}
	
	/**
	 * 按照页面搜索条件查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 5:57:37 PM <br/>
	 */
//	public List<T> selectAll(T eneity) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	/**
	 * 按照页面获得的对象的所属属性与非对象条件进行查询
	 * 功能：<br/>
	 * @author 杜中良
	 * @version May 19, 2013 7:16:47 PM <br/>
	 */
	public <K, O, V> void selectByConditionHQL(T entity,ISqlMap<K, O, V> sqlMap, Pager<T> page)throws Exception {
		// TODO Auto-generated method stub
		String hql = hqlModel.selectHqlModel(entity,sqlMap, page);
		selectAllByPage(hql,page);
	}
	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-13 上午09:20:19<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param entity
	 * @param SqlMap
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectByConditionHQL(com.zhongpin.pap.base.BaseEntity, com.zhongpin.pap.utils.ISqlMap)
	 */
	@Override
	public <K, O, V> List<T> selectByConditionHQL(T entity, ISqlMap<K, O, V> sqlMap) throws Exception {
		String hql = hqlModel.selectHqlModel(entity, sqlMap);
		return selectByHQL(hql);
	}

	/**
	 * 通过拼接的Hql语句查询对象并分页
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	public void selectByHQLPage(String hql,Pager<T> page)throws Exception{
		selectAllByPage(hql,page);
	}
	
	/**
	 * 通过单个条件查询一个对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 28, 2013 4:14:09 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	public T selectBySinglet(String property,Object value)throws Exception{
		String hql = hqlModel.selectHqlModel(entityClass,property);
		return (T) getSession().createQuery(hql).setParameter(0, value).uniqueResult();
	}
	
	/**
	 * 通过单个条件获得一组对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 28, 2013 4:14:09 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	public List<T> selectBySingletAll(String property,Object value)throws Exception{
		String hql = hqlModel.selectHqlModel(entityClass,property);
		return getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	/**
	 * 通过拼接的Hql语句查询对象不分页
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	/*Collection<T>*/ 
	@SuppressWarnings("unchecked")
	public List<T> selectByHQL(String hql)throws Exception{
		return (List<T>) hibernateTemplate.find(hql);
	}
	
	/**
	 * 通过拼接的Hql语句查询单个
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	/*Collection<T>*/ 
	@SuppressWarnings("unchecked")
	public T selectByHQLSingle(String hql)throws Exception{
		return selectHQLFunction(hql);
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
		return selectByPAndF(propertys,null,sqlMap);
	}
	
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
	public <K,O,V> List selectByFunction(String[] functions,SqlMap<K,O,V> sqlMap,boolean useAuth)throws Exception{
		if(useAuth)
			return selectByPAndF(null,functions,sqlMap);
		String hql = hqlModel.selectHqlModel(functions, sqlMap, entityClass);
		return hibernateTemplate.find(hql);
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
		String hql = hqlModel.selectHqlModel(propertys, functions, sqlMap, entityClass);
		return hibernateTemplate.find(hql);
	}
	
	/**
	 * 功能：根据条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	@SuppressWarnings("unchecked")
	public <K,O,V> List<T> selectTopValue(SqlMap<K, O, V> sqlMap,int start,int end) throws Exception{
		String hql = hqlModel.selectHqlModel(sqlMap,start,end,entityClass);
		return (List<T>) hibernateTemplate.find(hql);
	}
	
	/**
	 * 功能：根据单个条件查询数据 取前num数据<br/>
	 *
	 * @author 杜中良
	 * @version 2014-11-8 下午04:16:40 <br/>
	 */
	@SuppressWarnings("unchecked")
	public <K,O,V> List<T> selectTopValue(K property,O oper,V value,int start,int end) throws Exception{
		String hql = hqlModel.selectHqlModel(property,oper,value,start,end,entityClass);
		return (List<T>) hibernateTemplate.find(hql);
	}

	
	//---------------------------------更新方法----------------------------------------
	/**
	 * 更新一个对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
	public void update(T entity)throws Exception{
		hibernateTemplate.update(entity);
	}
	
	/**
	 * 更新一组对象
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
	public void updateList(Collection<T> entitys)throws Exception{
		for(T t : entitys)
			update(t);
	}
	
	
	/**
	 * 更新缓存中已有的对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jun 16, 2013 5:56:15 PM <br/>
	 */
	public void merge(T entity)throws Exception{
		getSession().merge(entity);
	}
	
	
	/**
	 * 通过拆分对象为HQL语句更新对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 9:03:32 AM <br/>
	 */
	public int updateHql(T entity)throws Exception{
		return hibernateTemplate.bulkUpdate(hqlModel.updateHqlModelByMap(entity));
		//Query query = getSession().createQuery(hqlModel.updateHqlModelByMap(entity));
		//query.executeUpdate();
		//return 1;
	}
	
	/**
	 * 指定更新属性与ID进行更新
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 29, 2013 3:00:14 PM<br/>
	 * @param PK
	 * @param filedValues
	 * @return <br/>
	 * @see com.zhongpin.pap.dao.IBaseDao#update(java.lang.String, java.util.Map)
	 */
	public <ID extends Serializable> int updateById(ID PK, Map<String, Object> filedValues)throws Exception {
		// TODO Auto-generated method stub
		String hql = hqlModel.updateHqlModelById(PK, filedValues, entityClass);
		return hibernateTemplate.bulkUpdate(hql);
	}

	/**
	 * 指定更新属性,与指定条件进行更新处理
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 29, 2013 3:00:14 PM<br/>
	 * @param PK
	 * @param filedValues
	 * @return <br/>
	 * @see com.zhongpin.pap.dao.IBaseDao#update(java.lang.String, java.util.Map)
	 */
	public <K, O, V>  int updateByCod(SqlMap<K, O, V> sqlMap,Map<String, Object> filedValues)throws Exception {
		String hql = hqlModel.updateHqlModel(sqlMap, filedValues, entityClass);
		return hibernateTemplate.bulkUpdate(hql);
	}
	
	/**
	 * 功能：<br/>
	 * 更新一组指定ID与指定更新属性的对象
	 * @author 杜中良
	 * @version May 19, 2013 10:45:54 AM <br/>
	 */
	public <ID extends Serializable> int updateByIds(ID[] PK,Map<String,Object> filedValues)throws Exception{
		int i= 0;
		for(ID pk : PK){
			if(updateById(pk,filedValues) ==0)
				break ;
			i++;
			
		}
		return i==PK.length?i:0;
	}
	
	/**
	 * 功能：<br/>
	 * 更新一组指定ID与指定更新属性的对象
	 * @author 杜中良
	 * @version May 19, 2013 10:45:54 AM <br/>
	 */
	public <ID extends Serializable> int updateByIds(Collection<ID> PK,Map<String,Object> filedValues)throws Exception{
		int i= 0;
		for(ID pk : PK){
			if(updateById(pk,filedValues) ==0)
				break ;
			i++;
			
		}
		return i==PK.size()?i:0;
	}
	
	//---------------------------------删除方法----------------------------------------

	/**
	 * 简单删除一个实体
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:11:16 AM <br/>
	 */
	public int delete(T entity)throws Exception{
		Assert.notNull(entity, "entity is required");
		hibernateTemplate.delete(entity);
		return 1;
	}
	
	/**
	 * 删除一组对象
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 29, 2013 10:17:38 AM<br/>
	 * @param elist <br/>
	 * @see com.zhongpin.pap.dao.IBaseDao#delete(java.util.List)
	 */
	public int delete(List<T> elist)throws Exception{
		Assert.notEmpty(elist, "elist 不能为空");
		hibernateTemplate.deleteAll(elist);
		return elist.size();
	}
	
	/**
	 * 通过对象ID删除对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:11:44 AM <br/>
	 */
	public <ID extends Serializable> int deleteById(ID PK)throws Exception{
		Assert.notNull(PK, "PK is not null");
		T entity = selectById(PK);
		return delete(entity);
	}
	
	/**
	 * 单个对象批量删除
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 1, 2013 10:12:07 AM <br/>
	 */
	public <ID extends Serializable> int deleteByIds(ID[] ids)throws Exception{
		Assert.notNull(ids, "ids is not null");
		int i=0;
		for(ID id:ids){
			deleteById(id);
			i++;
		}
		return i;
	}
	
	/**
	 * 清空对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 29, 2013 10:20:54 AM <br/>
	 */
	public int delete()throws Exception{
		String hql = "delete from " + entityClass.getName(); //目前改正为从DAO层中的泛型中获取
		return getSession().createQuery(hql).executeUpdate();
		
	}
	
	/**
	 * 指定条件删除
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：May 21, 2013 3:47:01 PM<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param sqlMap
	 * @return <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#delete(com.zhongpin.pap.utils.ISqlMap)
	 */
	public <K, O, V> int delete(ISqlMap<K, O, V> sqlMap)throws Exception {
		// TODO Auto-generated method stub
		String hql = hqlModel.deleteHqlModel(sqlMap, entityClass);
		return getSession().createQuery(hql).executeUpdate();
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
		String hql = "delete from " + entityClass.getName()+" where "+property + " = ?"; 
		getSession().createQuery(hql).setParameter(0, value).executeUpdate();
	}
	
	/**
	 * 执行DDL SQL语句
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 1, 2014 4:11:43 PM <br/>
	 */
	public void execute(String ddlSQL)throws Exception{
		jdbcTemplate.execute(ddlSQL);
	}	
	//---------------------------------辅助方法----------------------------------------

	/**
	 * 简单对象分页辅助
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 7, 2013 9:30:04 AM <br/>
	 */
	@SuppressWarnings({ "unchecked" })
	protected void selectAllByPage(final String hql,final Pager<T> page){
		hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException {
				String countHql = "select count(*) " + hql.substring(hql.indexOf("from"));
				Query count = session.createQuery(countHql);
				Query query = session.createQuery(hql);
				page.setTotalCount(((Long)count.uniqueResult()).intValue());
				//page.setTotalCount(query.list().size());
				query.setFirstResult((page.getPageNo()-1)*page.getPageSize());
				query.setMaxResults(page.getPageSize());
				List list = query.list();
				page.setResult(list);
				return list;
			}
		});
	}
	

	/**
	 * 对象查询不分页
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 7, 2013 9:30:04 AM <br/>
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	protected List<T> selectAll(final String hql){
		return (List<T>) hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException {
				Query query = session.createQuery(hql);
				List list = query.list();
				return list;
			}
		});
	}
	
	/**
	 * 查询HQL函数返回结果集
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 7, 2013 9:30:04 AM <br/>
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	protected T selectHQLFunction(final String hql){
		return (T) hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException {
				Query query = session.createQuery(hql);
				T ol = (T)query.uniqueResult();
				return ol;
			}
		});
	}
	
	/**
	 * hql语句函数 返回值是字符串
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Sep 12, 2013 5:09:49 PM <br/>
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public Object selectByHQLFun(final String hql)throws Exception{
		return hibernateTemplate.execute(new HibernateCallback(){
			public Object doInHibernate(Session session)throws HibernateException{
				Query query = session.createQuery(hql);
				Object v = query.uniqueResult();
				return v;
			}
		});
	}
	
	/**
	 * 通过分解对象构建HQL查询语句并分页
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 18, 2013 5:51:17 PM <br/>
	 */
//	@SuppressWarnings("unchecked")
//	protected void selectAllByPage(final T entity,final Pager<T> page){
//		hibernateTemplate.executeFind(new HibernateCallback(){
//			public Object doInHibernate(Session session)throws HibernateException, SQLException {
//				Query query = session.createQuery(buildSimpleQueryHQL(entity,page));
//				page.setTotalCount(query.list().size());
//				query.setFirstResult((page.getPageNo()-1)*page.getPageSize());
//				query.setMaxResults(page.getPageSize());
//				List list = query.list();
//				page.setResult(list);
//				return list;
//			}
//		});
//	}
//	
	/**
	 * QBC 条件查询
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 18, 2013 11:53:30 AM <br/>
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	protected <K,O,V> List<T> selectByConditions(final ISqlMap<K,O,V> qbcMap){	
		List<T> list = (List<T>) hibernateTemplate.execute(new HibernateCallback(){
			public List<T> doInHibernate(Session session) throws HibernateException{
				// TODO Auto-generated method stub
				DetachedCriteria dc = (DetachedCriteria) QBCModel.selectQBCModel(qbcMap,entityClass);
				Criteria criteria=dc.getExecutableCriteria(session);
				return criteria.list();
			}
		});
		return list;
	}

	/**
	 * 功能: 查询符合查询条件的数据总条数<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-8 下午03:27:17<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param sqlMap
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectTotalRows(com.zhongpin.pap.utils.SqlMap)
	 */
	@Override
	public <K, O, V> int selectTotalRows(SqlMap<K, O, V> sqlMap) throws Exception {
		String hql = hqlModel.buildSelectHql(hqlModel.getBasicCountHql(entityClass), sqlMap);
		Query query = getSession().createQuery(hql);
		Long rows = (Long)query.uniqueResult();
		if (null == rows)
			return 0;
		return rows.intValue();
	}

	/**
	 * 功能: 根据单一条件查询数据条数<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-8 下午04:28:36<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param property
	 * @param operator
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectTotalRows(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, O, V> int selectTotalRows(K property, O operator, V value) throws Exception {
		SqlMap<K, O, V> sqlMap = new SqlMap<K, O, V>();
		sqlMap.put(property, operator, value);
		return selectTotalRows(sqlMap);
	}

	/**
	 * 功能: 根据单一条件查询数据条数，默认操作符是=<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-8 下午04:28:36<br/>
	 * 
	 * @param <K>
	 * @param <V>
	 * @param property
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectTotalRows(java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, V> int selectTotalRows(K property, V value) throws Exception {
		return selectTotalRows(property, "=", value);
	}

	/**
	 * 功能: 根据单一条件查询<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午05:01:44<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param property
	 * @param operator
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectByConditionHQL(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, O, V> List<T> selectByConditionHQL(K property, O operator, V value) throws Exception {
		SqlMap<K, O, V> sqlMap = new SqlMap<K, O, V>();
		sqlMap.put(property, operator, value);
		return selectByConditionHQL(sqlMap);
	}

	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午05:01:44<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param property
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectByConditionHQL(java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, O, V> List<T> selectByConditionHQL(K property, V value) throws Exception {
		return selectByConditionHQL(property, "=", value);
	}

	/**
	 * 功能: 分页查询（无条件限制）<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午06:04:58<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param entity
	 * @param SqlMap
	 * @param page
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectByPageNoAuth(com.zhongpin.pap.base.BaseEntity, com.zhongpin.pap.utils.ISqlMap, com.zhongpin.pap.utils.Pager)
	 */
	@Override
	public <K, O, V> void selectByPageNoAuth(T entity, ISqlMap<K, O, V> sqlMap, Pager<T> page) throws Exception {
		String hql = hqlModel.getBasicSelectHql(entityClass);
		hql += hqlModel.buildSimpleQueryHQL(entity);
		hql = hqlModel.buildSelectHql(hql, sqlMap);
		
		if (!hql.contains("order by"))
			hql += hqlModel.buildOrderByPage(page);

		selectAllByPage(hql, page);
	}

	/**
	 * 功能: 分页查询（无条件限制）<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午06:04:58<br/>
	 * 
	 * @param entity
	 * @param page
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectByPageNoAuth(com.zhongpin.pap.base.BaseEntity, com.zhongpin.pap.utils.Pager)
	 */
	@Override
	public void selectByPageNoAuth(T entity, Pager<T> page) throws Exception {
		String hql = hqlModel.getBasicSelectHql(entityClass);
		hql += hqlModel.buildSimpleQueryHQL(entity);

		if (!hql.contains("order by"))
			hql += hqlModel.buildOrderByPage(page);

		selectAllByPage(hql, page);
	}

	/**
	 * 功能: 根据条件查询（无权限限制）<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午05:01:44<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param sqlMap
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectByConditionHQLNoAuth(com.zhongpin.pap.utils.ISqlMap)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, O, V> List<T> selectByConditionHQLNoAuth(ISqlMap<K, O, V> sqlMap) throws Exception {
		String hql = hqlModel.buildSelectHql(hqlModel.getBasicSelectHql(entityClass), sqlMap);
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	/**
	 * 功能: 根据单一条件查询（无权限限制）<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午05:01:44<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param property
	 * @param operator
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectByConditionHQLNoAuth(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, O, V> List<T> selectByConditionHQLNoAuth(K property, O operator, V value) throws Exception {
		SqlMap<K, O, V> sqlMap = new SqlMap<K, O, V>();
		sqlMap.put(property, operator, value);
		return selectByConditionHQLNoAuth(sqlMap);
	}

	/**
	 * 功能: 根据单一条件查询，操作符默认为=（无权限限制）<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-11-10 下午05:01:44<br/>
	 * 
	 * @param <K>
	 * @param <O>
	 * @param <V>
	 * @param property
	 * @param value
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.IBaseDao#selectByConditionHQLNoAuth(java.lang.Object, java.lang.Object)
	 */
	@Override
	public <K, O, V> List<T> selectByConditionHQLNoAuth(K property, V value) throws Exception {
		return selectByConditionHQLNoAuth(property, "=", value);
	}
	
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
		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		Object object = query.uniqueResult();
		if(object != null){
			int obj = Integer.parseInt(object.toString());
			if(obj == 0)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * 功能:查询聚集函数
	 * 重写:wxb
	 * 2017-9-11
	 * @see com.zd.epa.base.IBaseDao#selectByAggregateHQL(java.lang.String)
	 */
	@Override
	public Double selectByAggregateHQL(String hql) throws Exception {
		Object obj=getSession().createQuery(hql).uniqueResult();
		if(obj==null)
			return new Double(0);
		else
			return Double.parseDouble((String)obj);
	} 
	
	

}
