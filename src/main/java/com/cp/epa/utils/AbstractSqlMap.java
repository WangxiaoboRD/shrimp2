package com.cp.epa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

public abstract class AbstractSqlMap<K,O,V> implements ISqlMap<K,O,V> {
	
	//protected Map<K,Object> map=new LinkedHashMap<K,Object>();
	protected List<Object> list = new ArrayList<Object>();
	
	/**
	 * 清空容器内容
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 7, 2013 4:21:19 PM<br/>
	 * @see com.zhongpin.pap.utils.ICUDConditionCarryMap#clear()
	 */
	public void clear() {
		// TODO Auto-generated method stub
		//map.clear();
		list.clear();
	}
	
	/**
	 * 获取与这个Key相同的一批对象
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 9, 2013 4:36:00 PM<br/>
	 * @param key
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.ISqlMap#get(java.lang.Object)
	 */
	public abstract List<Object> get(K key);
	
	/**
	 * 判断容器是否为空
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 9, 2013 4:35:15 PM<br/>
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.ISqlMap#isEmpty()
	 */
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		//return map.isEmpty();
		return list.isEmpty();
	}

	public abstract void put(K key, O operator, V value);
	
	/**
	 * 通过某个Key去除掉Key与之相同的一批对象
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 9, 2013 4:34:11 PM<br/>
	 * @param key <br/>
	 * @see com.zhongpin.pap.utils.ISqlMap#remove(java.lang.Object)
	 */
	public abstract void remove(K key);
	
	/**
	 * 遍历容器内所有对象
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 9, 2013 4:31:46 PM <br/>
	 */
	public abstract List<Object> get();
	
	/**
	 * 容器大小
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 9, 2013 4:33:51 PM<br/>
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.ISqlMap#size()
	 */
	public int size() {
		// TODO Auto-generated method stub
		//map.size();
		return list.size();
	}
	/**
	 * 内置对象
	 * 类名：Condition  
	 * 功能：
	 * 保存 K O V条件的载体
	 * @author dzl 
	 * 创建时间：Apr 9, 2013 4:38:52 PM 
	 * @version Apr 9, 2013
	 */
	public static class Condition<K,O,V>{
		private K key;
		private O operator;
		private V value;
		public Condition(K key,O operator,V value){
			this.key = key;
			this.operator = operator;
			this.value = value;
		}
		public K getKey() {
			return key;
		}
		public void setKey(K key) {
			this.key = key;
		}
		public O getOperator() {
			return operator;
		}
		public void setOperator(O operator) {
			this.operator = operator;
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * 转化为Map
	 * 功能：<br/>
	 * @author 杜中良
	 * @version Apr 7, 2013 10:52:36 AM <br/>
	 */
	@SuppressWarnings("unchecked")
	public Map<K,V> toMap(){
		Assert.notEmpty(list, "SqlMap没有设置元素");
		Map<K,V> map = new HashMap<K,V>();
		for(Object _o : list){
			Condition<K,O,V> _c = (Condition<K,O,V>)_o;
			map.put(_c.getKey(), _c.getValue());
		}
		return map;
	}
}
