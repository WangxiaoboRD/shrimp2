package com.cp.epa.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

public class SqlMap<K,O,V> extends AbstractSqlMap<K,O,V> implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	public List<Object> get(K key) {
		// TODO Auto-generated method stub
		Assert.notNull(key, "key is not null");
		List<Object> rlist = new ArrayList<Object>();
		for(Object o : list){
			if((((Condition)o).getKey())!= null){
				if((((Condition)o).getKey()).equals(key)){
					rlist.add(o);
				}
			}
		}
		return rlist;
	}
	public void put(K key, O operator, V value) {
//		Object[] oArray = new Object[2];
//		oArray[0]=operator;
//		oArray[1]=value;
		list.add(new Condition(key,operator,value));
	}
	
	public void remove(K key) {
		//return map.remove(key);
		for (Iterator it = list.iterator(); it.hasNext();) {
			Condition _c = (Condition)it.next();
			if(_c.getKey() != null){
				if(_c.getKey().equals(key)){
					it.remove();
				}
			}
        }
	}

	/**
	 * 遍历容器所有对象
	 * 功能: <br/>
	 * 重写：杜中良 <br/>
	 * @version ：Apr 9, 2013 4:40:47 PM<br/>
	 * @return <br/>
	 * @see com.zhongpin.pap.utils.AbstractSqlMap#get()
	 */
	public List<Object> get() {
		// TODO Auto-generated method stub
		return list;
	}
	
}
