package com.cp.epa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * 类名：Bill  <br />
 *
 * 功能：
 * 标示业务对象注解
 * @author DZL <br />
 * 创建时间：2013-7-11 上午10:41:12  <br />
 * @version 2013-7-11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bill {
	public String name()default "";//单据名称
	//public String type()default "";//单据类型
}
