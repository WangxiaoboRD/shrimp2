/**
 * 文件名：@IQuartzCronService.java <br/>
 * 包名：com.zhongpin.pap.quartz.services <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.quartz.services;

import com.cp.epa.base.IBaseService;
import com.cp.epa.quartz.entity.QuartzCron;

/**
 * 类名：IQuartzCronService  <br />
 *
 * 功能：调度作业规则表达式业务逻辑层定义
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-1-6 下午03:24:55  <br />
 * @version 2014-1-6
 */
public interface IQuartzCronService extends IBaseService<QuartzCron> {
	
	/**
	 * 生成cron表达式
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 4:21:03 PM <br/>
	 */
	public String getCronExpress(QuartzCron quartzCron)throws Exception;
	
}
