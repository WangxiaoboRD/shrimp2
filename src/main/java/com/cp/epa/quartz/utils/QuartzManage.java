package com.cp.epa.quartz.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;


public class QuartzManage{
	
	//创建调度对象
	private static Scheduler scheduler = getNewScheduler();
	
	/**
	 * 创建调度对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 25, 2013 4:28:21 PM <br/>
	 */
	public static Scheduler getScheduler(){
		//创建调度对象工厂
		DirectSchedulerFactory schedulerFactory = DirectSchedulerFactory.getInstance();
		
		//SchedulerFactory schedulerFactory2 = DirectSchedulerFactory.getInstance();
		
		//SchedulerFactory schedulerFactory3 = new StdSchedulerFactory();
		
		//System.out.println("-----:"+schedulerFactory.hashCode());
		//System.out.println("-----:"+schedulerFactory2.hashCode());
		
		if(scheduler == null){
			try {
				schedulerFactory.createVolatileScheduler(10);
				scheduler = schedulerFactory.getScheduler();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return scheduler;
	}
	
	/**
	 * 创建方式2
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 27, 2013 4:43:10 PM <br/>
	 */
	public static Scheduler getNewScheduler(){
		//创建调度对象工厂
		//SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		if(scheduler == null){
			try {
				//scheduler = schedulerFactory.getScheduler();
				scheduler = StdSchedulerFactory.getDefaultScheduler();   
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return scheduler;
	}
	
	/**
	 * 调度启动
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 27, 2013 4:48:02 PM <br/>
	 */
	public static void start() throws SchedulerException{
		scheduler.start();
	}
	
	/**
	 * 判断是否启动
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 27, 2013 4:49:21 PM <br/>
	 */
	public static boolean isStarted() throws SchedulerException{
		return scheduler.isStarted();
	}
	
	/**
	 * 关闭调度
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 27, 2013 4:50:20 PM <br/>
	 */
    public static void shutdown() throws SchedulerException {
    	scheduler.shutdown();
    }
    /**
     * 注册事件
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 27, 2013 5:01:11 PM <br/>
     */ 
    public static Date scheduleJob(JobDetail jobdetail, Trigger trigger) throws SchedulerException{
    	return scheduler.scheduleJob(jobdetail, trigger);
    }

    /**
     * 注册一个触发器
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 27, 2013 5:06:00 PM <br/>
     */ 
    public static Date scheduleJob(Trigger trigger) throws SchedulerException{
        return scheduler.scheduleJob(trigger);
    }
     
    /**
     * 注册多个事件
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 27, 2013 5:09:07 PM <br/>
     */
    public static void scheduleJobs(Map<JobDetail, Set<? extends Trigger>> triggersAndJobs, boolean replace) throws SchedulerException{
       scheduler.scheduleJobs(triggersAndJobs, replace);
    } 
    /**
     * 停止调度 通过停止触发器
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 9:44:09 AM <br/>
     */
    public static boolean unscheduleJob(TriggerKey triggerkey) throws SchedulerException{
    	return scheduler.unscheduleJob(triggerkey);
    }

    /**
     * 停止多个触发器相关的调度
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 9:44:53 AM <br/>
     */
    public static boolean unscheduleJobs(List<TriggerKey> triggerKeylist) throws SchedulerException{
       return scheduler.unscheduleJobs(triggerKeylist);
    }
    
    /**
     * 删除原始的TriggerKey 并提供一个新的Trigger 并启动
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 9:45:48 AM <br/>
     */
    public static Date rescheduleJob(TriggerKey triggerkey, Trigger trigger)throws SchedulerException{
    	return scheduler.rescheduleJob(triggerkey, trigger);
    }
    /**
     * 添加相关的job任务 并且与Trigger 没有关联
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:01:09 AM <br/>
     */
    public static void addJob(JobDetail jobdetail, boolean flag)throws SchedulerException   {
        scheduler.addJob(jobdetail, flag);
    }

    /**
     * 删除相关的job任务
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:01:32 AM <br/>
     */
    public static boolean deleteJob(JobKey jobkey) throws SchedulerException{
    	return scheduler.deleteJob(jobkey);
    }

    /**
     * 删除一组相关的job任务
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:01:56 AM <br/>
     */
    public static boolean deleteJobs(List<JobKey> jobKeys)throws SchedulerException{
        return scheduler.deleteJobs(jobKeys);
    }

    /**
     * 为触发器添加一个作业 这个操作必须放在 addJob（JobDetail） 后面，也即是为trigger添加的JobDetail必须存在于scheduler中
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:02:20 AM <br/>
     */
    public static void triggerJob(JobKey jobkey) throws SchedulerException    {
        scheduler.triggerJob(jobkey);
    }

    public static void triggerJob(JobKey jobkey, JobDataMap jobdatamap) throws SchedulerException   {
        scheduler.triggerJob(jobkey, jobdatamap);
    }

    /**
     * 暂停一个Job 与该Job有关的Triger都停止
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:07:33 AM <br/>
     */
    public static void pauseJob(JobKey jobkey) throws SchedulerException  {
        scheduler.pauseJob(jobkey);
    }

    /**
     * 暂停一组job
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:07:48 AM <br/>
     */
    public static void pauseJobs(GroupMatcher<JobKey> groupmatcher) throws SchedulerException   {
        scheduler.pauseJobs(groupmatcher);
    }

    /**
     * 暂停一个触发器与之相关的JobDetail 都会停
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:08:05 AM <br/>
     */
    public static void pauseTrigger(TriggerKey triggerkey)throws SchedulerException   {
        scheduler.pauseTrigger(triggerkey);
    }

    /**
     * 暂停一组触发器
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:08:20 AM <br/>
     */
    public static void pauseTriggers(GroupMatcher<TriggerKey> groupmatcher)throws SchedulerException   {
        scheduler.pauseTriggers(groupmatcher);
    }

    /**
     * 恢复一个调度
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:08:58 AM <br/>
     */
    public static void resumeJob(JobKey jobkey) throws SchedulerException {
        scheduler.resumeJob(jobkey);
    }

    /**
     * 恢复一组调度
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:09:09 AM <br/>
     */
    public static void resumeJobs(GroupMatcher<JobKey> matcher) throws SchedulerException   {
        scheduler.resumeJobs(matcher);
    }

    /**
     * 恢复一个触发器
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:09:21 AM <br/>
     */
    public static void resumeTrigger(TriggerKey triggerkey)throws SchedulerException   {
        scheduler.resumeTrigger(triggerkey);
    }

    /**
     * 恢复一组触发器
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:09:31 AM <br/>
     */
    public static void resumeTriggers(GroupMatcher<TriggerKey>  groupmatcher)throws SchedulerException {
        scheduler.resumeTriggers(groupmatcher);
    }

    /**
     * 暂停所有调度
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:09:45 AM <br/>
     */
    public static void pauseAll() throws SchedulerException{
        scheduler.pauseAll();
    }

    /**
     * 恢复所有调度
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:10:02 AM <br/>
     */
    public static void resumeAll() throws SchedulerException{
        scheduler.resumeAll();
    }
	public static void main(String[] args) throws Exception{
		
		Scheduler scheduler = QuartzManage.getNewScheduler();
		
		Scheduler scheduler2 = QuartzManage.getNewScheduler();
		
		System.out.println("--ss---:"+scheduler.hashCode());
		System.out.println("--ss---:"+scheduler2.hashCode());
		
	}
}
