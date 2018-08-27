package com.cp.epa.quartz.utils;

import org.quartz.Job;
import org.springframework.context.ApplicationContext;

import com.cp.epa.base.Base;
import com.cp.epa.utils.ApplicationContextUtil;


public abstract class QuartzAbstractJob extends Base implements Job{
	//获得spring容器
	protected ApplicationContext ctx = ApplicationContextUtil.getContext();
}

