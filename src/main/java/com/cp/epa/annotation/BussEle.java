package com.cp.epa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * 类名：BussEle  <br />
 *
 * 功能：
 * 标示业务对象注解
 * @author zp <br />
 * 创建时间：2013-7-11 上午10:41:12  <br />
 * @version 2013-7-11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BussEle {
	public String name()default "";
	public String type()default "";
}
