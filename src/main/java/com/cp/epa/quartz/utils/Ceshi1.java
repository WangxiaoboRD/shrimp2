package com.cp.epa.quartz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cp.epa.quartz.entity.QuartzJob;
import com.cp.epa.quartz.services.IQuartzJobService;

@Component
public class Ceshi1 extends QuartzAbstractJob {

	@Autowired
	private IQuartzJobService quartzJobService;
	public void execute(JobExecutionContext context)throws JobExecutionException {
		
		
		System.out.println("---:"+quartzJobService);
		
		try {
			List<QuartzJob> ql = quartzJobService.selectAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("-------------------------我是测试一--------------------当前时间：-------"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

}
