package com.cp.epa.quartz.actions;


import com.cp.epa.base.BaseAction;
import com.cp.epa.quartz.entity.QuartzJob;
import com.cp.epa.quartz.services.IQuartzJobService;

public class QuartzJobAction extends BaseAction<QuartzJob, IQuartzJobService> {

	/**
	 * 加载并启动job
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 10:03:31 AM <br/>
	 */
	public String run() throws Exception{
		boolean b = service.quartzRun(e);
		return text(b+"");
	}
	
	/**
	 * 暂停
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 10:15:50 AM <br/>
	 */
	public String pauseJob()throws Exception{
		service.quartzPauseJob(e);
		return text(true+"");
	}
	
	/**
	 * 恢复
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 10:15:50 AM <br/>
	 */
	public String resumeJob()throws Exception{
		service.quartzResumeJob(e);
		return text(true+"");
	}
	
	/**
	 * 按照触发器暂停
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 10:24:43 AM <br/>
	 */
	public String pauseTrigger()throws Exception{
		service.quartzPauseTrigger(e);
		return text(true+"");
	}
	
	/**
	 * 按照触发器恢复
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 10:25:49 AM <br/>
	 */
	public String resumeTrigger()throws Exception {
		service.quartzResumeTrigger(e);
		return text(true+"");
	}
	
	/**
	 * 停止并卸载job
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 10:27:20 AM <br/>
	 */
	public String stop() throws Exception{
		boolean b = service.quartzStopJob(e);
		return text(b+"");
	}
	
	/**
	 * 停止整个容器
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 10:28:41 AM <br/>
	 */
	public String stopAll() throws Exception{
		service.stop();
		return text(true+"");
	}
}
