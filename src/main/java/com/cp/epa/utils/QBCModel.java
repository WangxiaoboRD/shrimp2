package com.cp.epa.utils;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.Assert;

import com.cp.epa.utils.AbstractSqlMap.Condition;

/**
 * 
 * 类名：QBCModel  
 * 功能：
 * @author dzl 
 * 创建时间：Apr 9, 2013 11:16:51 AM 
 * @version Apr 9, 2013
 */
//public class QBCModel extends AbstractQBCModel<DetachedCriteria> {
public class QBCModel{
	/**
	 * 拼接QBC类型的查询语句 功能：<br/>
	 * 
	 * @author 杜中良
	 * @version Apr 9, 2013 10:41:36 AM <br/>
	 * 
	 * ???????????????????????????????? 这个方法将来要修正
	 */
	public static <K,O,V> DetachedCriteria selectQBCModel(ISqlMap<K,O,V> sqlMap, Class<?> clazz) {
		// TODO Auto-generated method stub
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		List<Object> list = sqlMap.get();
		Assert.notEmpty(list, "SqlMap没有设置元素");
		for(Object _o : list){
			if(_o instanceof Condition){
				Condition<K,O,V> _c = (Condition)_o;
				String operator = (String)_c.getOperator();
				if(!"".equals(operator) && operator != null)
					operator = operator.replaceAll(" ", "");
				if("".equals(operator) || "=".equals(operator)){
					dc.add(Restrictions.eq((String)_c.getKey(),_c.getValue()));
				}else if(">".equals(operator)){
					dc.add(Restrictions.gt((String)_c.getKey(),_c.getValue()));
				}else if("<".equals(operator)){
					dc.add(Restrictions.lt((String)_c.getKey(),_c.getValue()));
				}else if(">=".equals(operator)){
					dc.add(Restrictions.ge((String)_c.getKey(),_c.getValue()));
				}else if("<=".equals(operator)){
					dc.add(Restrictions.le((String)_c.getKey(),_c.getValue()));
				}else if("LIKE".equalsIgnoreCase(operator)){
					dc.add(Restrictions.like((String)_c.getKey(),_c.getValue()));
				}else if("IN".equalsIgnoreCase(operator)){
					dc.add(Restrictions.in((String)_c.getKey(),((String)_c.getValue()).split(",")));
				}else if("NOTIN".equalsIgnoreCase(operator)){
					dc.add(Restrictions.not(Restrictions.in((String)_c.getKey(),((String)_c.getValue()).split(","))));
				}else if("ISNULL".equalsIgnoreCase(operator)){
					dc.add(Restrictions.isNull((String)_c.getKey()));
				}else if("ISNOTNULL".equalsIgnoreCase(operator)){
					dc.add(Restrictions.isNotNull((String)_c.getKey()));
				}else if("BETWEEN".equalsIgnoreCase(operator)){
					String[] value = (String[])_c.getValue();
					dc.add(Restrictions.between((String)_c.getKey(), value[0],value[1]));
				}else if("DESC".equalsIgnoreCase(operator)){
					dc.addOrder(Order.desc((String)_c.getKey()));
				}else if("ASC".equalsIgnoreCase(operator)){
					dc.addOrder(Order.asc((String)_c.getKey()));
				}
			}
		}
		return dc;
	}

	
}
