/**
 * 文件名：@QuartzCronAction.java <br/>
 * 包名：com.zhongpin.pap.quartz.actions <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.quartz.actions;

import com.cp.epa.base.BaseAction;
import com.cp.epa.quartz.entity.QuartzCron;
import com.cp.epa.quartz.services.IQuartzCronService;

/**
 * 类名：QuartzCronAction  <br />
 *
 * 功能：调动作业规则表达式控制层处理
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-1-6 下午03:28:56  <br />
 * @version 2014-1-6
 */
public class QuartzCronAction extends BaseAction<QuartzCron, IQuartzCronService> {
	private static final long serialVersionUID = 7136586360310532523L;
}
