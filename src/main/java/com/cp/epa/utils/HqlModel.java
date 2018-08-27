package com.cp.epa.utils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.Base;
import com.cp.epa.base.BaseEntity;
import com.cp.epa.permission.entity.Users;
import com.cp.epa.utils.AbstractSqlMap.Condition;

/**
 * HQL模板语句拼接 类名：HQLModel
 * 
 * 功能：
 * 
 * @author dzl 创建时间：Apr 28, 2013 3:19:21 PM
 * @version Apr 28, 2013
 */
// public class HQLModel extends AbstractHQLModel<String> {
public class HqlModel extends Base {

	/** 数据权限工具类注册 */
	@Autowired
	private DataAuthUtil dataAuthUtil;

	// ---------------------------------删除方法----------------------------------------
	/**
	 * 拼接HQL删除语句 功能: <br/>
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Apr 28, 2013 3:06:05 PM<br/>
	 * @param qbcMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.AbstractHQLModel#deleteHqlModel(com.zhongpin.pap.utils.ISqlMap,
	 *      java.lang.Class)
	 */
	public <K, O, V> String deleteHqlModel(ISqlMap<K, O, V> sqlMap, Class<?> clazz) {
		String hql = "delete from " + clazz.getName() + " e where 1=1";
		return buildPosHql(sqlMap, hql, null, new HqlCallBack() {
			public String doIn(String hql, String setHql, String posHql) {
				return hql + posHql;
			}
		});
	}

	// ------------------------------------查询方法----------------------------------------------
	/**
	 * 根据对象创建基础性HQL语句 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Nov 7, 2013 8:51:13 AM <br/>
	 */
	public <T> String buildClassSelectHql(T entity) throws Exception {
		// 查询用户权限中是否包含这个ID
		Users u = SysContainer.get();
		// if(session == null)
		// throw new SystemException("获取用户信息失败");
		//Users u = null;
		//if (session != null)
		//	u = (Users) session.get("CURRENTUSER");
		return dataAuthUtil.buildDataAuthorityEntity(entity.getClass(), u);
	}

	/**
	 * 通过对象的class创建基础HQL语句 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Nov 7, 2013 10:18:37 AM <br/>
	 */
	public <T> String buildClassSelectHql(Class<T> clazz) throws Exception {
//		Map<String, Object> session = SysContext.get();
//		// if(session == null)
//		// throw new SystemException("获取用户信息失败");
//		Users u = null;
//		if (session != null)
//			u = (Users) session.get("CURRENTUSER");
		Users u = SysContainer.get();
		return dataAuthUtil.buildDataAuthorityEntity(clazz, u);
	}

	/**
	 * 拼接Hql查询语句 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Apr 28, 2013 3:07:32 PM<br/>
	 * 
	 * @param sqlMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.AbstractHQLModel#selectHqlModel(com.zhongpin.pap.utils.ISqlMap,
	 *      java.lang.Class)
	 */
	public <K, O, V> String selectHqlModel(ISqlMap<K, O, V> sqlMap, Class<?> clazz) throws Exception {
		// String hql = "from "+ clazz.getName()+" e where 1=1";
		String hql = buildClassSelectHql(clazz);
		return buildPosHql(sqlMap, hql, null, new HqlCallBack() {
			public String doIn(String hql, String setHql, String posHql) {
				return hql + posHql;
			}
		});
	}
	
	/**
	 * 根据条件查询前几条信息
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-11-8 上午11:38:43 <br/>
	 */
	public <K, O, V> String selectHqlModel(ISqlMap<K, O, V> sqlMap,int start,int end,Class<?> clazz)throws Exception{
		String hql = buildClassSelectHql(clazz);
		hql = buildPosHql(sqlMap, hql, null, new HqlCallBack() {
			public String doIn(String hql, String setHql, String posHql) {
				return hql + posHql;
			}
		});
		return hql+" and rownum>"+start+" and rownum<="+end;
	}
	
	/**
	 * 根据条件查询前几条信息
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version 2015-11-8 上午11:38:43 <br/>
	 */
	public <K,O,V> String selectHqlModel(K key,O operator ,V value,int start,int end,Class<?> clazz)throws Exception{
		String hql = buildClassSelectHql(clazz);
		if(operator == null || "".equals(operator) || "=".equals(operator))
			hql = hql+" and "+key+"="+value+" and rownum>"+start+" and rownum<="+end;
		else if("like".equals((operator.toString()).toLowerCase()))
			hql = hql+" and "+key+" like '"+value+"%' and rownum>"+start+" and rownum<="+end;
		else
			hql = hql+" and "+key+" "+operator+" "+value+" and rownum>"+start+" and rownum<="+end;
		return hql;
	}


	/**
	 * 拼接Hql查询语句带分页 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Apr 28, 2013 3:07:32 PM<br/>
	 * 
	 * @param sqlMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.AbstractHQLModel#selectHqlModel(com.zhongpin.pap.utils.ISqlMap,
	 *      java.lang.Class)
	 */
	public <K, O, V, T extends BaseEntity> String selectHqlModel(ISqlMap<K, O, V> sqlMap, Class<?> clazz, Pager<T> page) throws Exception {
		// String hql = "from "+ clazz.getName()+" e where 1=1";
		String hql = buildClassSelectHql(clazz);
		String _hql = buildPosHql(sqlMap, hql, null, new HqlCallBack() {
			public String doIn(String hql, String setHql, String posHql) {
				return hql + posHql;
			}
		});
		if (!_hql.contains("order by"))
			_hql += buildOrderByPage(page);
		return _hql;

	}

	/**
	 * 从页面收集查询条件封装到对象的Map属性，分解生成HQL语句并分页 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version May 20, 2013 9:40:19 AM <br/>
	 */
	public <T extends BaseEntity> String selectHqlModel(T entity, Pager<T> page) throws Exception {
		// String hql = "from "+entity.getClass().getName()+" e where 1=1";
		String hql = buildClassSelectHql(entity);
		hql += buildSimpleQueryHQL(entity);

		if (!hql.contains("order by"))
			hql += buildOrderByPage(page);

		return hql;
	}

	/**
	 * 从页面收集查询条件封装到对象的Map属性，分解生成HQL语句并分页 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version May 20, 2013 9:40:19 AM <br/>
	 */
	public <T extends BaseEntity> String selectHqlModel(T entity) throws Exception {
		// String hql = "from "+entity.getClass().getName()+" e where 1=1";
		String hql = buildClassSelectHql(entity);
		hql += buildSimpleQueryHQL(entity);

		return hql;
	}

	/**
	 * 从页面收集查询条件封装到对象的Map属性，分解生成HQL语句并分页 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version May 20, 2013 9:40:19 AM <br/>
	 */
	public <K, O, V, T extends BaseEntity> String selectHqlModel(T entity, ISqlMap<K, O, V> sqlMap, Pager<T> page) throws Exception {
		// String hql = "from "+entity.getClass().getName()+" e where 1=1";
		String hql = buildClassSelectHql(entity);
		hql += buildSimpleQueryHQL(entity);
		String _hql = buildPosHql(sqlMap, hql, null, new HqlCallBack() {
			public String doIn(String hql, String setHql, String posHql) {
				return hql + posHql;
			}
		});
		if (!_hql.contains("order by"))
			_hql += buildOrderByPage(page);
		return _hql;
	}
	
	/**
	 * 功能：从页面收集查询条件封装到对象的Map属性，分解生成HQL语句并分页 功能（不分页）<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-11-13 上午09:21:05 <br/>
	 */
	public <K, O, V, T extends BaseEntity> String selectHqlModel(T entity, ISqlMap<K, O, V> sqlMap) throws Exception {
		// String hql = "from "+entity.getClass().getName()+" e where 1=1";
		String hql = buildClassSelectHql(entity);
		hql += buildSimpleQueryHQL(entity);
		String _hql = buildPosHql(sqlMap, hql, null, new HqlCallBack() {
			public String doIn(String hql, String setHql, String posHql) {
				return hql + posHql;
			}
		});
		return _hql;
	}

	/**
	 * 通过单个查询条件凭借Hql语句 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Sep 28, 2013 4:27:13 PM <br/>
	 */
	public String selectHqlModel(Class<?> clazz, String property) throws Exception {
		// String hql = "from "+clazz.getName()+" e where 1=1";
		String hql = buildClassSelectHql(clazz);
		if (property != null && !"".equals(property)) {
			hql += " and " + property + " = ?";
		}
		return hql;
	}

	/**
	 * 
	 * 功能：<br/>
	 * 对对象进行属性与函数的混合查询
	 * 
	 * @param propertys
	 *            所要查询的属性数组
	 * @param propertys
	 *            所要查询的函数数组
	 * @author 杜中良
	 * @version Mar 25, 2014 4:59:17 PM <br/>
	 */
	public <K, O, V> String selectHqlModel(String[] propertys, String[] functions, SqlMap<K, O, V> sqlMap, Class<?> clazz) throws Exception {
		String preHql = "select ";
		if (functions != null && functions.length > 0) {
			for (String function : functions) {
				preHql += function + ",";
			}
		}
		if (propertys != null && propertys.length > 0) {
			for (String property : propertys) {
				preHql += property + ",";
			}
		}
		// 拼接的select 前置hql
		preHql = preHql.substring(0, preHql.lastIndexOf(",")) + " ";
		// 生成的权限hql
		String authorityHql = buildClassSelectHql(clazz);
		// 拼接sqlmap中的条件hql,并组合成完整的查询语句
		String hql = buildPosHql(sqlMap, preHql, authorityHql, new HqlCallBack() {
			public String doIn(String preHql, String authorityHql, String posHql) {
				return preHql + authorityHql + posHql;
			}
		});
		return hql;
	}

	/**
	 * 
	 * 功能：<br/>
	 * 对对象进行函数的查询
	 * 
	 * @param propertys
	 *            所要查询的函数数组
	 * @author 杜中良
	 * @version Mar 25, 2014 4:59:17 PM <br/>
	 */
	public <K, O, V> String selectHqlModel(String[] functions, SqlMap<K, O, V> sqlMap, Class<?> clazz) throws Exception {
		String preHql = "select ";
		if (functions != null && functions.length > 0) {
			for (String function : functions) {
				preHql += function + ",";
			}
		}
		// 拼接的select 前置hql
		preHql = preHql.substring(0, preHql.lastIndexOf(",")) + " ";
		// 查询hql
		String fromHql = "from " + clazz.getName() + " e where 1=1";
		// 拼接sqlmap中的条件hql,并组合成完整的查询语句
		String hql = buildPosHql(sqlMap, preHql, fromHql, new HqlCallBack() {
			public String doIn(String preHql, String fromHql, String posHql) {
				return preHql + fromHql + posHql;
			}
		});
		return hql;
	}

	// --------------------------------更新方法-----------------------------------------------------
	/**
	 * 拼接HQL更新语句 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Apr 28, 2013 4:27:09 PM<br/>
	 * 
	 * @param sqlMap
	 * @param clazz
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.AbstractHQLModel#updateHqlModel(com.zhongpin.pap.utils.ISqlMap,
	 *      java.lang.Class)
	 */
	public <K, O, V> String updateHqlModel(ISqlMap<K, O, V> sqlMap, Map<String, Object> setField, Class<?> clazz) {
		// String hql = "from "+ clazz.getName()+" where 1=1";
		String hql = "update " + clazz.getName() + " e set";
		if (setField == null) {
			return null;
		}
		String setValue = buildUpdateField(setField);
		return buildPosHql(sqlMap, hql, setValue, new HqlCallBack() {
			public String doIn(String hql, String setHql, String posHql) {
				return hql + setHql + " where 1=1 " + posHql;
			}
		});
	}

	/**
	 * 拼接HQL类型的更新语句 通过Id更新指定属性值 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Apr 9, 2013 10:23:26 AM <br/>
	 */
	public <ID extends Serializable> String updateHqlModelById(ID PK, Map<String, Object> setField, Class<?> cla) {
		String hql = "update " + cla.getName() + " e set";
		// Class<?> clazz = cla.getClass();
		String idName = TypeUtil.getClassIdField(cla);
		if (idName == null || "".equals(idName))
			return null;
		idName = "e." + idName;
		if (setField == null || setField.size() == 0)
			return null;
		String setHql = buildUpdateField(setField);
		return hql + setHql + " where " + idName + "='" + PK + "'";
	}  

	/**
	 * 通过页面获取更新对象的值，分解对象属性封装为简单的更新对象的HQL语句 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Apr 18, 2013 5:58:23 PM <br/>
	 */
	public <T extends BaseEntity> String updateHqlModelByMap(T entity) {
		return buildSimpleUpdateHQL(entity);
	}

	// -----------------------------------共用方法----------------------------------------------
	/**
	 * 
	 * Hql拼接条件模板 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Apr 28, 2013 4:27:47 PM <br/>
	 */
	@SuppressWarnings("unchecked")
	private <K, O, V> String buildPosHql(ISqlMap<K, O, V> sqlMap, String preHql, String setHql, HqlCallBack callBack) {
		String hql = "";
		if (null != sqlMap) {
			List<Object> list = sqlMap.get();
			if (list != null && list.size() != 0) {
				for (Object _o : list) {
					Condition<K, O, V> _c = (Condition) _o;
					String operator = (String) _c.getOperator();
					if (!"".equals(operator) && operator != null)
						operator = operator.replaceAll(" ", "");
					if ("IN".equalsIgnoreCase(operator) || "NOTIN".equalsIgnoreCase(operator))
						hql += " and e." + _c.getKey() + " " + _c.getOperator() + " (" + _c.getValue() + ")";
					else if ("BETWEEN".equalsIgnoreCase(operator)) {
						String[] value = (String[]) (_c.getValue());
						if (value != null && value.length > 1) {
							hql += " and e." + _c.getKey() + " " + _c.getOperator() + " '" + value[0] + "' and '" + value[1] + "'";
						}
					}else if ("ISNULL".equalsIgnoreCase(operator) || "ISNOTNULL".equalsIgnoreCase(operator))
						hql += " and e." + _c.getKey() + " " + _c.getOperator() + "";
					else if ("ISEMPTY".equalsIgnoreCase(operator) || "ISNOTEMPTY".equalsIgnoreCase(operator))
						hql += " and e." + _c.getKey() + " " + _c.getOperator() + "";
					else if ("ORDERBY".equalsIgnoreCase(operator)) {
						if(hql.contains("order by")){
							String _shql = hql.substring(0, hql.indexOf("order by")+8);
							String _ehql = hql.substring(hql.indexOf("order by")+8);
							hql = _shql +" e."+_c.getKey()+" "+_c.getValue()+","+_ehql;
						}else
							hql += " order by "+" e."+_c.getKey()+" "+_c.getValue();
					}else{
						String opera = (_c.getOperator()+"").trim();
						if(">".equals(opera) || "<".equals(opera) || ">=".equals(opera) || "<=".equals(opera) ){
							if(PapUtil.checkDouble(_c.getValue()+"") )
								hql += " and e." + _c.getKey() + _c.getOperator() + _c.getValue();
							else
								hql += " and e." + _c.getKey() + " " + _c.getOperator() + " '" + _c.getValue() + "'";
						}else
							hql += " and e." + _c.getKey() + " " + _c.getOperator() + " '" + _c.getValue() + "'";
					}
				}
			}
		}
		return callBack.doIn(preHql, setHql, hql);
	}

	/**
	 * 更新set字段拼接 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version May 18, 2013 4:33:56 PM <br/>
	 */
	public String buildUpdateField(Map<String, Object> setField) {
		if (setField == null || setField.size() == 0)
			return null;
		String setValue = "";
		Set<Map.Entry<String, Object>> set = setField.entrySet();
		for (Iterator<Map.Entry<String, Object>> it = set.iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
			setValue += " e." + entry.getKey() + "='" + entry.getValue() + "',";
		}
		setValue = setValue.substring(0, setValue.length() - 1);
		return setValue;
	}

	// /**
	// * 内部类回调接口
	// * 类名：HqlCallBack
	// * 功能：
	// * @author dzl
	// * 创建时间：May 20, 2013 8:58:47 AM
	// * @version May 20, 2013
	// */
	// public interface HqlCallBack{
	// String doIn(String preHql,String setHql,String posHql);
	// }
	/**
	 * 通过页面搜索条件，简单封装HQL语句 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Apr 18, 2013 5:58:23 PM <br/>
	 */
	public <T extends BaseEntity> String buildSimpleQueryHQL(T entity) {
		Map<String, Object> _m = entity.getMap();
		String hql = "";
		if (_m.size() > 0) {
			Set<String> fields = TypeUtil.getTransientField(entity.getClass());
			Iterator<Entry<String, Object>> iterator = _m.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.startsWith("e.")) {
					// if(key.contains("e.map"))
					// continue ;
					if (fields != null) {
						if (fields.contains(key.split("\\.")[1]))
							continue;
					}
					if (value != null && !"".equals((String) value))
						// if("e.createDate".equals(key)){
						// hql +=
						// " and ("+key+" between '"+(String)value+" 00:00:00' and '"+(String)value+" 23:59:59')";
							hql += " and " + key + " like '" + (String) value + "%'";
					// }else
					// hql +=" and "+key+"='"+(String)value+"'";
				}
			}
		}
		return hql;
	}

	/**
	 * 功能：<br/>
	 * 根据Page所携带的排序信息进行排序语句的拼接
	 * 
	 * @author 杜中良
	 * @version May 20, 2013 9:17:19 AM <br/>
	 */
	public <T extends BaseEntity> String buildOrderByPage(Pager<T> page) {
		return " order by e." + page.getSortName() + " " + page.getSortorder();
	}

	/**
	 * 通过页面获取更新对象的值，分解对象属性封装为简单的更新对象的HQL语句 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Apr 18, 2013 5:58:23 PM <br/>
	 */
	public <T extends BaseEntity> String buildSimpleUpdateHQL(T entity) {

		Class<?> clazz = entity.getClass();
		String idName = TypeUtil.getClassIdField(clazz);
		if (idName == null || "".equals(idName))
			return null;
		idName = "e." + idName;
		Map<String, Object> _m = entity.getMap();
		String hql = "";
		if (_m != null && _m.size() > 0) {
			hql = "update " + entity.getClass().getName() + " e set ";
			Set<String> fields = TypeUtil.getTransientField(entity.getClass());
			Iterator<Entry<String, Object>> iterator = _m.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.startsWith("e.")) {
					if (fields != null) {
						String c = key.substring(2);
						//??????????????????????????????????
						if (c.contains(".")) {
							String rec = c.split("\\.")[0];
							if (fields.contains(rec))
								continue;
						}
					}
					// if(value != null && !"".equals((String)value) &&
					// !idName.equals((String)key))
					// hql += key+"='"+(String)value+"',";
					if (!idName.equals((String) key)) {
						if (value == null) {
							hql += key + "=NULL,";
						}else if ("".equals((String) value)) {
							hql += key + "='',";
						}else {
							hql += key + "='" + (String) value + "',";
						}
					}
				}
			}
			// 添加修改时间修改人
			// if(hql != null && !"".equals(hql)){
			// //判断对象有没有修改时间，修改人属性
			// if(TypeUtil.isIncludeField("modifyDate", clazz))
			// hql += "modifyDate='"+PapUtil.date(new Date())+"',";
			// if(TypeUtil.isIncludeField("modifyUser", clazz)){
			// Map<String, Object> session = SysContext.get();
			// //if(session == null)
			// // throw new SystemException("获取用户信息失败");
			// Users u = null;
			// if(session != null){
			// u =(Users)session.get("CURRENTUSER");
			// if(u != null)
			// hql += "modifyUser='"+u.getUserRealName()+"',";
			// }
			// }
			// }
			hql = hql.substring(0, hql.length() - 1);
			// 添加ID
			hql += " where " + idName + "='" + _m.get(idName) + "'";
		}
		//logger.info("生成的Sql:" + hql);
		return hql;
	}

	/**
	 * 通过指定的属性与ID更新对象 功能：<br/>
	 * 
	 * @deprecated
	 * @author 杜中良
	 * @version Apr 29, 2013 3:31:47 PM <br/>
	 */
	protected String buildSimpleUpdateHQL(String idName, String idValue, Map<String, Object> filedValues, Class<?> clazz) {
		if (filedValues == null || filedValues.size() == 0)
			return null;
		String hql = "update " + clazz.getName() + " e set ";
		Iterator<Entry<String, Object>> iterator = filedValues.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null && !"".equals((String) value))
				hql += "e." + key + "='" + (String) value + "',";

		}
		hql = hql.substring(0, hql.length() - 1);
		hql += " where " + idName + "='" + idValue + "'";
		return hql;
	}

	/**
	 * 功能：获得基本统计语句<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-11-8 下午03:38:55 <br/>
	 */
	public String getBasicCountHql(Class<?> clazz) throws Exception {
		return "select count(*) from " + clazz.getName() + " e where 1=1";
	}
	
	/**
	 * 功能：获得基本查询语句<br/>
	 *
	 * @author 孟雪勤
	 * @version 2014-11-10 下午05:02:15 <br/>
	 */
	public String getBasicSelectHql(Class<?> clazz) throws Exception {
		return "from " + clazz.getName() + " e where 1=1";
	}

	/**
	 * 功能：根据查询条件构建Hql语句<br/>
	 * 
	 * @author 孟雪勤
	 * @version 2014-11-8 下午03:55:19 <br/>
	 */
	public <K, O, V> String buildSelectHql(String hql, ISqlMap<K, O, V> sqlMap) throws Exception {
		return buildPosHql(sqlMap, hql, null, new HqlCallBack() {
			public String doIn(String hql, String setHql, String posHql) {
				return hql + posHql;
			}
		});
	}
}


