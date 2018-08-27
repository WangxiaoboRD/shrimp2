package com.cp.epa.quartz.services.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.SystemException;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.quartz.dao.IQuartzJobDao;
import com.cp.epa.quartz.entity.QuartzJob;
import com.cp.epa.quartz.services.IQuartzJobService;
import com.cp.epa.quartz.utils.QuartzManage;
import com.cp.epa.quartz.utils.QuartzStatus;
import com.cp.epa.utils.SqlMap;


public class QuartzJobServiceImpl extends BaseServiceImpl<QuartzJob, IQuartzJobDao> implements IQuartzJobService {
	
	/**
	 * 调度启动
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 28, 2013 4:08:08 PM <br/>
	 */
	public boolean quartzRun(QuartzJob quartzJob) throws Exception{
		quartzJob = getQuartzJob(quartzJob);
		loadingJob(quartzJob);
		if(!QuartzManage.isStarted())
			//调度启动
			QuartzManage.start();
		quartzJob.setQuartzStatus(QuartzStatus.ON);
		super.update(quartzJob);
		return true;
	}
	
	/**
	 * 调度停止
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 28, 2013 4:09:42 PM <br/>
	 */
	public void stop() throws Exception{
		if(QuartzManage.isStarted())
			QuartzManage.shutdown();
	}

	/**
	 * 装载作业
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Dec 30, 2013 3:54:02 PM<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.quartz.services.IQuartzControlService#loadingJob()
	 */
	@SuppressWarnings("unchecked")
	public Date loadingJob(QuartzJob quartzJob) throws Exception {

		quartzJob = getQuartzJob(quartzJob);
		//创建任务对象
		JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(quartzJob.getClassName())).withIdentity(quartzJob.getId()+"", quartzJob.getQuartzGroup().getId()+"").build(); 
		//创建触发器
		CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger().withIdentity(quartzJob.getId()+"", quartzJob.getQuartzGroup().getId()+"").withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCronExpress().getExpression())).build();
		//绑定调度器
		return QuartzManage.scheduleJob(jobDetail, trigger);
		 
	}

	/**
	 * 卸载作业
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Dec 30, 2013 3:56:10 PM<br/>
	 * 
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.quartz.services.IQuartzControlService#unloadJob()
	 */
	public boolean unloadJob(QuartzJob quartzJob) throws Exception {
		
		quartzJob = getQuartzJob(quartzJob);
		// TODO Auto-generated method stub
		JobKey jobkey = new JobKey(quartzJob.getId()+"",quartzJob.getQuartzGroup().getId()+"");
		return QuartzManage.deleteJob(jobkey);
	}
	
	
	/**
	 * 停止某一项任务
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 28, 2013 4:49:47 PM <br/>
	 * @throws Exception 
	 */
	public boolean quartzStopJob(QuartzJob quartzJob) throws Exception{
		
		quartzJob = getQuartzJob(quartzJob);
		TriggerKey triggerKey = new TriggerKey(quartzJob.getId()+"",quartzJob.getQuartzGroup().getId()+"");
		QuartzManage.unscheduleJob(triggerKey);
		quartzJob.setQuartzStatus(QuartzStatus.OFF);
		super.update(quartzJob);
		
		return unloadJob(quartzJob);
		
	}
	
	
	/**
	 * 暂停任务
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Dec 30, 2013 3:54:19 PM<br/>
	 * 
	 * @param quartzControl
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.quartz.services.IQuartzControlService#pauseJob(com.zhongpin.pap.quartz.entity.QuartzControl)
	 */
	public void quartzPauseJob(QuartzJob quartzJob) throws Exception {
		
		quartzJob = getQuartzJob(quartzJob);
		JobKey jobkey = new JobKey(quartzJob.getId()+"",quartzJob.getQuartzGroup().getId()+"");
		QuartzManage.pauseJob(jobkey);
		quartzJob.setQuartzStatus(QuartzStatus.PAUSE);
		super.update(quartzJob);
		
	}

	/**
	 * 恢复任务
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Dec 30, 2013 3:54:35 PM<br/>
	 * 
	 * @param quartzControl
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.quartz.services.IQuartzControlService#rescheduleJob(com.zhongpin.pap.quartz.entity.QuartzControl)
	 */
	public void quartzResumeJob(QuartzJob quartzJob) throws Exception {
		quartzJob = getQuartzJob(quartzJob);
		JobKey jobkey = new JobKey(quartzJob.getId()+"",quartzJob.getQuartzGroup().getId()+"");
		QuartzManage.resumeJob(jobkey);
		
		quartzJob.setQuartzStatus(QuartzStatus.ON);
		super.update(quartzJob);
	}
	
	/**
     * 暂停一个触发器与之相关的JobDetail 都会停
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:08:05 AM <br/>
     */
    public void quartzPauseTrigger(QuartzJob quartzJob)throws Exception{
    	quartzJob = getQuartzJob(quartzJob);
    	TriggerKey triggerkey = new TriggerKey(quartzJob.getId()+"",quartzJob.getQuartzGroup().getId()+"");
    	QuartzManage.pauseTrigger(triggerkey);
    	
    	quartzJob.setQuartzStatus(QuartzStatus.PAUSE);
		super.update(quartzJob);
    }
    
    /**
     * 恢复一个触发器
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:09:21 AM <br/>
     */
    public void quartzResumeTrigger(QuartzJob quartzJob)throws Exception{
    	quartzJob = getQuartzJob(quartzJob);
    	TriggerKey triggerkey = new TriggerKey(quartzJob.getId()+"",quartzJob.getQuartzGroup().getId()+"");
    	QuartzManage.resumeTrigger(triggerkey);
    	
     	quartzJob.setQuartzStatus(QuartzStatus.ON);
		super.update(quartzJob);
    }
    
    
    /**
     * 通过quartzJob的ID获得QuartzJob对象
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Jan 6, 2014 10:05:55 AM <br/>
     */
    public QuartzJob getQuartzJob(QuartzJob quartzJob)throws Exception{
    	if(quartzJob == null)
    		return null;
    	return dao.selectById(quartzJob.getId());
    }
    /**
     * 功能: 修改<br/>
     * 
     * 重写：孟雪勤 <br/>
     * 
     * @version ：2014-1-6 上午11:42:21<br/>
     * 
     * @param entity
     * @return
     * @throws Exception <br/>
     * @see com.zhongpin.pap.base.BaseServiceImpl#updateHql(com.zhongpin.pap.base.BaseEntity)
     */
    @Override
    public int updateHql(QuartzJob entity) throws Exception {
    	
    	QuartzJob job = dao.selectById(entity.getId());
    	if (job.getQuartzStatus() != QuartzStatus.OFF) {
    		throw new SystemException("所选调度作业处于非停止状态，不允许修改！");
    	}
    	
    	return super.updateHql(entity);
    }

	/**
	 * 功能: 根据id集合删除<br/>
	 * 
	 * 重写：孟雪勤 <br/>
	 * 
	 * @version ：2014-1-6 上午11:42:21<br/>
	 * 
	 * @param <ID>
	 * @param PK
	 * @return
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#deleteByIds(ID[])
	 */
	@Override
	public <ID extends Serializable> int deleteByIds(ID[] PK) throws Exception {
		
		// 若被调度作业未处于停止状态，则不允许删除
		SqlMap<String, String, String> sqlMap = new SqlMap<String, String, String>();
		sqlMap.put("id", "in", Arrays.toString(PK).replaceAll("[\\[\\]\\s]", ""));
		sqlMap.put("quartzStatus", "in", "'ON','PAUSE'");
		List<QuartzJob> quartzJobs = dao.selectByConditionHQL(sqlMap);
		if (null != quartzJobs && quartzJobs.size() > 0) {
			throw new SystemException("所选调度作业处于非停止状态，不允许删除！");
		}
		
		return super.deleteByIds(PK);
	}
    
}
