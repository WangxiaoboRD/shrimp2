package com.cp.epa.utils;

import java.util.List;
import java.util.Map;

/**
 * 类名：ICUDConditionCarryMap 增删改条件运载容器 
 * 功能：
 * 主要作用是为了将前台所需要的条件统一传送到后台比如要查询用户信息表中用户名称为A的用户可以用这个对象 
 * HQLMap.put("userName","=","A");
 * @author dzl 
 * 创建时间：Apr 7, 2013 9:10:23 AM 
 * @version Apr 7, 2013
 */
public interface ISqlMap<K,O,V>{
	
	/**
	 * 清空容器，但是对象还存在
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 7, 2013 9:08:30 AM <br/>
	 */
	public void clear() ;
	
	/**
	 * 通过K获取
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 7, 2013 9:09:03 AM <br/>
	 */
	public List<Object> get(K key) ;
	/**
	 * 判断容器是不是为空
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 7, 2013 9:14:15 AM <br/>
	 */
	public boolean isEmpty();
	
	/**
	 * 将信息加入到容器中
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 7, 2013 9:14:33 AM <br/>
	 */
	public void put(K key, O operator, V value) ;
	
	/**
	 * 通过K删除某个值
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Apr 7, 2013 9:15:06 AM <br/>
	 */
	public void remove(K key) ;
	
	/**
	 * 容器大小
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 7, 2013 9:15:47 AM <br/>
	 */
	public int size() ;
	
	/**
	 * 遍历容器内所有对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 9, 2013 4:31:46 PM <br/>
	 */
	public List<Object> get();
	
	/**
	 * 转化为Map
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 7, 2013 10:52:36 AM <br/>
	 */
	public Map<K,V> toMap();
}
