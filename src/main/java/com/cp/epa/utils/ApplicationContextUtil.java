/**
 * 文件名：@ApplicationContextUtil.java <br/>
 * 包名：com.zhongpin.pap.utils <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.cp.epa.base.Base;

/**
 * 类名：ApplicationContextUtil  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-14 上午09:33:42  <br />
 * @version 2013-12-14
 */
public class ApplicationContextUtil extends Base implements ApplicationContextAware {

	/** Spring容器 */
	private static ApplicationContext ctx;
	
	/**
	 * 功能: <br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2013-12-14 上午10:55:58<br/>
	 * 
	 * @param applicationContext
	 * @throws BeansException <br/>
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}
	
	/**
	 * 功能：获得Spring容器上下文<br/>
	 *
	 * @author 孟雪勤
	 * @version 2013-12-14 上午10:56:03 <br/>
	 */
	public static ApplicationContext getContext() {
		return ctx;
	}
}
