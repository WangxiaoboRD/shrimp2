package com.cp.epa.quartz.services;

import java.util.Date;

import com.cp.epa.base.IBaseService;
import com.cp.epa.quartz.entity.QuartzJob;

public interface IQuartzJobService extends IBaseService<QuartzJob> {
	
	/**
	 * 加载某项任务
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 30, 2013 11:12:56 AM <br/>
	 */
	public Date loadingJob(QuartzJob quartzJob)throws Exception;
	
	/**
	 * 卸载某项任务
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 30, 2013 11:12:56 AM <br/>
	 */
	public boolean unloadJob(QuartzJob quartzJob)throws Exception;
	
	/**
	 * 调度启动
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 28, 2013 4:08:08 PM <br/>
	 */
	public boolean quartzRun(QuartzJob quartzJob)throws Exception;
	
	/**
	 * 调度停止
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 28, 2013 4:09:42 PM <br/>
	 */
	public void stop() throws Exception;
	
	/**
	 * 停止某一项任务
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 28, 2013 4:49:47 PM <br/>
	 * @throws Exception 
	 */
	public boolean quartzStopJob(QuartzJob quartzJob) throws Exception;
	
	/**
	 * 暂停某一项任务
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 28, 2013 4:49:47 PM <br/>
	 * @throws Exception 
	 */
	public void quartzPauseJob(QuartzJob quartzJob) throws Exception;
	
	/**
	 * 恢复某一项任务
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Dec 28, 2013 4:49:47 PM <br/>
	 * @throws Exception 
	 */
	public void quartzResumeJob(QuartzJob quartzJob) throws Exception;
	
	/**
     * 暂停一个触发器与之相关的JobDetail 都会停
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:08:05 AM <br/>
     */
    public void quartzPauseTrigger(QuartzJob quartzJob)throws Exception;
    
    /**
     * 恢复一个触发器
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Dec 28, 2013 10:09:21 AM <br/>
     */
    public void quartzResumeTrigger(QuartzJob quartzJob)throws Exception;
    
    /**
     * 通过quartzJob的ID获得QuartzJob对象
     * 功能：<br/>
     *
     * @author 杜中良
     * @version Jan 6, 2014 10:05:55 AM <br/>
     */
    public QuartzJob getQuartzJob(QuartzJob quartzJob)throws Exception;
	
}
