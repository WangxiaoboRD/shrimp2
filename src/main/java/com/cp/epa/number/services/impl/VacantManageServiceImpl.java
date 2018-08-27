package com.cp.epa.number.services.impl;

import java.io.Serializable;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.number.dao.IVacantManageDao;
import com.cp.epa.number.entity.VacantManage;
import com.cp.epa.number.services.IVacantManageService;
import com.cp.epa.quartz.utils.QuartzManage;
import com.cp.epa.utils.PapUtil;
import com.cp.epa.utils.SqlMap;

/**
 * 类名：VacantManageServiceImpl  <br />
 *
 * 功能：
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 下午02:48:49  <br />
 * @version 2015-10-7
 */
public class VacantManageServiceImpl extends BaseServiceImpl<VacantManage, IVacantManageDao> implements IVacantManageService {

	/**
	 * 功能: 启动空号作业<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-14 下午04:35:57<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.number.services.IVacantManageService#start(com.zhongpin.pap.number.entity.VacantManage)
	 */
	@Override
	public void operateStart(VacantManage entity) throws Exception {
		entity = selectById(entity.getId());
		//创建任务对象
		String className = "com.zhongpin.pap.number.utils.GenerateNumber";
		String jobGroup = "VacantManage";
		JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(className)).withIdentity(entity.getBussObj(), jobGroup).build();
		jobDetail.getJobDataMap().put("objCode", entity.getBussObj()); // 传参
		
		//创建触发器
		CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(entity.getBussObj(), jobGroup).withSchedule(CronScheduleBuilder.cronSchedule(entity.getVacantRule().getVacantRule())).build();
		//绑定调度器
		QuartzManage.scheduleJob(jobDetail, trigger);
		
		if(!QuartzManage.isStarted())
			//调度启动
			QuartzManage.start();
		entity.setStatus('Y');
	}

	/**
	 * 功能: 停止空号作业<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-14 下午04:35:57<br/>
	 * 
	 * @param entity
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.number.services.IVacantManageService#stop(com.zhongpin.pap.number.entity.VacantManage)
	 */
	@Override
	public void operateStop(VacantManage entity) throws Exception {
		entity = selectById(entity.getId());
		String jobGroup = "VacantManage";
		TriggerKey triggerKey = new TriggerKey(entity.getBussObj(), jobGroup);
		QuartzManage.unscheduleJob(triggerKey);
		entity.setStatus('N'); // 调度状态
		entity.setRunStatus('N'); // 可运行状态
		JobKey jobkey = new JobKey(entity.getBussObj(), jobGroup);
		QuartzManage.deleteJob(jobkey);
	}

	/**
	 * 功能: 删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2015-12-15 下午05:24:39<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		
		// 空号作业已启动不允许删除
		SqlMap<String, String, Object> sqlMap = new SqlMap<String, String, Object>();
		sqlMap.put("id", "in", PapUtil.arrayToStr(PK));
		sqlMap.put("status", "=", 'Y');
		int rows = dao.selectTotalRows(sqlMap);
		if (rows > 0)
			throw new SystemException("所选空号作业已启动正在运行，不允许删除！");
		
		return dao.deleteByIds(PK);
	}
	
}
