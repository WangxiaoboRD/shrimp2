package com.cp.epa.base;

import javax.annotation.PostConstruct;
import org.apache.log4j.Logger;

public abstract class Base {
	// 日志记录器 
	protected static final Logger logger = Logger.getLogger(Base.class);

	/**
	 * 功能：<br/>
	 * 获取当前实体类的类型
	 * @author 杜中良
	 * @version Mar 17, 2013 11:07:01 AM <br/>
	 */
	//@SuppressWarnings("unchecked")
	//@PostConstruct
	//public void init(){
		//Class<T> entityClass = (Class<T>)TypeUtil.getActualTypes(this.getClass())[0];
		//System.out.println("---------entityClass--------:"+entityClass.getName());
	//}
}
