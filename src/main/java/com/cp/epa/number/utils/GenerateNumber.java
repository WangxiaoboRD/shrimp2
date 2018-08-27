/**
 * 文件名：@GenerateNumber.java <br/>
 * 包名：com.zhongpin.pap.number.utils <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.utils;


import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.cp.epa.number.services.IVacantNumberService;
import com.cp.epa.quartz.utils.QuartzAbstractJob;
import com.cp.epa.number.entity.Number;

/**
 * 类名：GenerateNumber  <br />
 *
 * 功能：生成空号
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-12-14 下午05:16:32  <br />
 * @version 2015-12-14
 */
public class GenerateNumber extends QuartzAbstractJob {

	/** 空号业务注册 */
	private IVacantNumberService vacantNumberService;
	
	/**
	 * 功能: 号码生成执行方法<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-15 上午10:00:21<br/>
	 * 
	 * @param arg0
	 * @throws JobExecutionException <br/>
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if (null == vacantNumberService)
			vacantNumberService = (IVacantNumberService) ctx.getBean("vacantNumberServiceImpl");
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		if (null != dataMap) {
			String objCode = dataMap.getString("objCode");
			if (null != objCode) {
				try {
					vacantNumberService.createNumber(objCode);
				}catch (Exception e) {
					logger.error(objCode + "业务对象空号号码生成失败！");
					e.printStackTrace();
				}
			}
		}
	}
	
}
