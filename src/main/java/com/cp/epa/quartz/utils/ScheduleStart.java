package com.cp.epa.quartz.utils;
import javax.annotation.PostConstruct;

import org.quartz.SchedulerException;

import com.cp.epa.base.Base;

public class ScheduleStart extends Base {
	@PostConstruct
	protected void init(){
		 try {
			QuartzManage.getNewScheduler().start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
