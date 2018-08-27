package com.cp.epa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * 类名：MethodMean  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-12 上午09:30:44  <br />
 * @version 2013-7-12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamMean {

	String[] value();
}
