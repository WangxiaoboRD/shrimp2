package com.cp.epa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * 类名：MethodMeans  <br />
 *
 * 功能：
 * 自定义注解，描述方法
 * @author zp <br />
 * 创建时间：2013-7-12 上午09:30:05  <br />
 * @version 2013-7-12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodMeans {
	
	public abstract String[] param() default {};
	
	public abstract String descs() default "";
	
}
