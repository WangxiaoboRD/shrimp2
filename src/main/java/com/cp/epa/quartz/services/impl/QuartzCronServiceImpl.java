/**
 * 文件名：@QuartzCronServiceImpl.java <br/>
 * 包名：com.zhongpin.pap.quartz.services.impl <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.quartz.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.cp.epa.base.BaseServiceImpl;
import com.cp.epa.exception.SystemException;
import com.cp.epa.quartz.dao.IQuartzCronDao;
import com.cp.epa.quartz.entity.QuartzCron;
import com.cp.epa.quartz.entity.QuartzJob;
import com.cp.epa.quartz.services.IQuartzCronService;
import com.cp.epa.quartz.services.IQuartzJobService;

/**
 * 类名：QuartzCronServiceImpl  <br />
 *
 * 功能：调度作业规则表达式业务逻辑层实现
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-1-6 下午03:27:52  <br />
 * @version 2014-1-6
 */
public class QuartzCronServiceImpl extends BaseServiceImpl<QuartzCron, IQuartzCronDao> implements IQuartzCronService {

	@Autowired
	private IQuartzJobService quartzJobService;
	/**
	 * 生成cron表达式
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 6, 2014 4:21:03 PM <br/>
	 */
	public String getCronExpress(QuartzCron quartzCron)throws Exception{
		if(quartzCron == null)
			return null;
		String cronExpress="";
		if(quartzCron.getSetOptions() == 0){
			//秒
			if(quartzCron.getDsecond()==null || "".equals(quartzCron.getDsecond()))
				throw new SystemException("自定义表达式不符合规则");
			if("?".equals(quartzCron.getDday()) && "?".equals(quartzCron.getDweek()))
				throw new SystemException("自定义表达式不符合规则");
			if(!"?".equals(quartzCron.getDday()) && !"?".equals(quartzCron.getDweek()))
				throw new SystemException("自定义表达式不符合规则");
			cronExpress += quartzCron.getDsecond();
			//分
			if(quartzCron.getDminute() == null || "".equals(quartzCron.getDminute()))
				cronExpress += " *";
			else
				cronExpress +=" "+quartzCron.getDminute();
			//小时
			if(quartzCron.getDhour() == null || "".equals(quartzCron.getDhour()))
				cronExpress += " *";
			else
				cronExpress +=" "+quartzCron.getDhour();
			//天
			if(quartzCron.getDday() == null || "".equals(quartzCron.getDday()))
				cronExpress += " *";
			else
				cronExpress +=" "+quartzCron.getDday();
			//月
			if(quartzCron.getDmonth() == null || "".equals(quartzCron.getDmonth()))
				cronExpress += " *";
			else
				cronExpress +=" "+quartzCron.getDmonth();
			//周
			if(quartzCron.getDweek() == null || "".equals(quartzCron.getDweek())){
				if(quartzCron.getDday() == null || "".equals(quartzCron.getDday()))
					cronExpress += " ?";
				else{
					if("?".equals(quartzCron.getDday()))
						cronExpress += " *";
					else
						cronExpress += " ?";
				}
			}else
				cronExpress +=" "+quartzCron.getDweek();
			//年
			if(quartzCron.getDyear() != null && !"".equals(quartzCron.getDyear()))
				cronExpress +=" "+quartzCron.getDyear();
		}else{
			
			/**
			 * 手动设置
			 */
			if(quartzCron.getMinuteOptions() == 1 && quartzCron.getHourOptions()==1)
				throw new SystemException("手动设置表达式不符合规则");
			cronExpress ="0"; 
			//1.分处理
			if(quartzCron.getMinuteOptions() == 0){ //每分钟 “*”
				cronExpress += " *";
			}else if(quartzCron.getMinuteOptions() == 1){//按照周期进行设置
				if(quartzCron.getStartMinute()== null && quartzCron.getEndMinute()==null && quartzCron.getIntervalMinute()==null)
					cronExpress += " *";
				if(quartzCron.getStartMinute()== null && quartzCron.getEndMinute()==null && quartzCron.getIntervalMinute() !=null)
					cronExpress += " /"+quartzCron.getIntervalMinute();
				if(quartzCron.getStartMinute()== null && quartzCron.getEndMinute() !=null && quartzCron.getIntervalMinute()!= null){
					if(quartzCron.getEndMinute()==0)
						throw new SystemException("手动设置表达式不符合规则,结束时间不能为0");
					if(quartzCron.getIntervalMinute() > quartzCron.getEndMinute())
						throw new SystemException("手动设置表达式不符合规则,间隔时间不能大于结束时间");
					cronExpress += " 0-"+quartzCron.getEndMinute()+"/"+quartzCron.getIntervalMinute();
				}
				if(quartzCron.getStartMinute()== null && quartzCron.getEndMinute() !=null && quartzCron.getIntervalMinute()== null){
					if(quartzCron.getEndMinute()==0)
						throw new SystemException("手动设置表达式不符合规则,结束时间不能为0");
					cronExpress += " 0-"+quartzCron.getEndMinute();
				}
				if(quartzCron.getStartMinute()!= null && quartzCron.getEndMinute()==null && quartzCron.getIntervalMinute()== null)
					cronExpress += " "+quartzCron.getStartMinute();
				if(quartzCron.getStartMinute()!= null && quartzCron.getEndMinute()!=null && quartzCron.getIntervalMinute()== null){
					if(quartzCron.getEndMinute() <= quartzCron.getStartMinute())
						throw new SystemException("手动设置表达式不符合规则,结束时间不能大于等于开始时间");
					cronExpress += " "+quartzCron.getStartMinute()+"-"+quartzCron.getEndMinute();
				}
				if(quartzCron.getStartMinute()!= null && quartzCron.getEndMinute()==null && quartzCron.getIntervalMinute()!= null)
					cronExpress += " "+quartzCron.getStartMinute()+"/"+quartzCron.getIntervalMinute();
				if(quartzCron.getStartMinute()!= null && quartzCron.getEndMinute()!=null && quartzCron.getIntervalMinute()!= null){
					if(quartzCron.getEndMinute() <= quartzCron.getStartMinute())
						throw new SystemException("手动设置表达式不符合规则,结束时间不能小于等于开始时间");
					if(quartzCron.getIntervalMinute() > quartzCron.getEndMinute())
						throw new SystemException("手动设置表达式不符合规则,间隔时间不能大于结束时间");
					cronExpress += " "+quartzCron.getStartMinute()+"-"+quartzCron.getEndMinute()+"/"+quartzCron.getIntervalMinute();
				}
			}else if(quartzCron.getMinuteOptions() == 2){//指定分钟
				if(quartzCron.getAssignMinutes()==null || "".equals(quartzCron.getAssignMinutes()))
					cronExpress +=" *";
				cronExpress += " "+quartzCron.getAssignMinutes().replaceAll(" ","");
			}
			//2.小时处理
			if(quartzCron.getHourOptions() == 0){ //每小时 “*”
				cronExpress += " *";
			}else if(quartzCron.getHourOptions() == 1){//按照周期进行设置
				if(quartzCron.getStartHour()== null && quartzCron.getEndHour()==null && quartzCron.getIntervalHour()==null)
					cronExpress += " *";
				if(quartzCron.getStartHour()== null && quartzCron.getEndHour()==null && quartzCron.getIntervalHour() !=null)
					cronExpress += " /"+quartzCron.getIntervalHour();
				if(quartzCron.getStartHour()== null && quartzCron.getEndHour() !=null && quartzCron.getIntervalHour()!= null){
					if(quartzCron.getEndHour()==0)
						throw new SystemException("手动设置表达式不符合规则,结束时间不能为0");
					if(quartzCron.getIntervalHour() > quartzCron.getEndHour())
						throw new SystemException("手动设置表达式不符合规则,间隔时间不能大于结束时间");
					cronExpress += " 0-"+quartzCron.getEndHour()+"/"+quartzCron.getIntervalHour();
				}
				if(quartzCron.getStartHour()== null && quartzCron.getEndHour() !=null && quartzCron.getIntervalHour()== null){
					if(quartzCron.getEndHour()==0)
						throw new SystemException("手动设置表达式不符合规则,结束时间不能为0");
					cronExpress += " 0-"+quartzCron.getEndHour();
				}
				if(quartzCron.getStartHour()!= null && quartzCron.getEndHour()==null && quartzCron.getIntervalHour()== null)
					cronExpress += " "+quartzCron.getStartHour();
				if(quartzCron.getStartHour()!= null && quartzCron.getEndHour()!=null && quartzCron.getIntervalHour()== null){
					if(quartzCron.getEndHour() <= quartzCron.getStartHour())
						throw new SystemException("手动设置表达式不符合规则,结束时间不能大于等于开始时间");
					cronExpress += " "+quartzCron.getStartHour()+"-"+quartzCron.getEndHour();
				}
				if(quartzCron.getStartHour()!= null && quartzCron.getEndHour()==null && quartzCron.getIntervalHour()!= null)
					cronExpress += " "+quartzCron.getStartHour()+"/"+quartzCron.getIntervalHour();
				if(quartzCron.getStartHour()!= null && quartzCron.getEndHour()!=null && quartzCron.getIntervalHour()!= null){
					if(quartzCron.getEndHour() <= quartzCron.getStartHour())
						throw new SystemException("手动设置表达式不符合规则,结束时间不能小于等于开始时间");
					if(quartzCron.getIntervalHour() > quartzCron.getEndHour())
						throw new SystemException("手动设置表达式不符合规则,间隔时间不能大于结束时间");
					cronExpress += " "+quartzCron.getStartHour()+"-"+quartzCron.getEndHour()+"/"+quartzCron.getIntervalHour();
				}
			}else if(quartzCron.getHourOptions() == 2){//指定分钟
				if(quartzCron.getAssignHours()==null || "".equals(quartzCron.getAssignHours()))
					cronExpress +=" *";
				cronExpress += " "+quartzCron.getAssignHours().replaceAll(" ","");
			}
			//3.天处理
			Character userWeek = quartzCron.getUseWeek();
			if(null != userWeek && (userWeek.charValue() == 'Y'))
				cronExpress +=" ?";
			else{
				if (null == quartzCron.getDayOptions()) 
					throw new SystemException("[天]条件未选择！");
				if(quartzCron.getDayOptions() == 0)//每天 “*”
					cronExpress += " *";
				else if(quartzCron.getDayOptions() == 1){//指定天
					if(quartzCron.getAssignDays()==null || "".equals(quartzCron.getAssignDays()))
						cronExpress +=" *";
					cronExpress += " "+quartzCron.getAssignDays().replaceAll(" ","");
				}	
			}
			//4.月处理
			if(quartzCron.getMonthOptions() == 0)//每月 “*”
				cronExpress += " *";
			else if(quartzCron.getMonthOptions() == 1){//指定月份
				if(quartzCron.getAssignMonths()==null || "".equals(quartzCron.getAssignMonths()))
					cronExpress +=" *";
				cronExpress += " "+quartzCron.getAssignMonths().replaceAll(" ","");
			}	
			//5.周处理
			if('N' == quartzCron.getUseWeek().charValue())
				cronExpress +=" ?";
			else{
				if (null == quartzCron.getWeekOptions()) 
					throw new SystemException("[星期]条件未选择！");
				
				if(quartzCron.getWeekOptions() == 0)//每周 “*”
					cronExpress += " *";
				else if(quartzCron.getWeekOptions() == 2){//指定周
					if(quartzCron.getAssignWeeks()==null || "".equals(quartzCron.getAssignWeeks()))
						cronExpress +=" *";
					cronExpress += " "+quartzCron.getAssignWeeks().replaceAll(" ","");
				}	
			}
		}
		return cronExpress;
	}
	
	/**
	 * 保存对象
	 * 功能：<br/>
	 *
	 * @author 杜中良
	 * @version Jan 7, 2014 3:43:48 PM <br/>
	 */
	public String save(QuartzCron quartzCron)throws Exception{
		if(quartzCron == null)
			throw new SystemException("保存对象不能为空");
		quartzCron.setExpression(getCronExpress(quartzCron));
		if(quartzCron.getAssignMinutes() !=null && !"".equals(quartzCron.getAssignMinutes()))
			quartzCron.setAssignMinutes(quartzCron.getAssignMinutes().replaceAll(" ", ""));
		if(quartzCron.getAssignHours() !=null && !"".equals(quartzCron.getAssignHours()))
			quartzCron.setAssignHours(quartzCron.getAssignHours().replaceAll(" ", ""));
		if(quartzCron.getAssignDays() !=null && !"".equals(quartzCron.getAssignDays()))
			quartzCron.setAssignDays(quartzCron.getAssignDays().replaceAll(" ", ""));
		if(quartzCron.getAssignMonths()!=null && !"".equals(quartzCron.getAssignMonths()))
			quartzCron.setAssignMonths(quartzCron.getAssignMonths().replaceAll(" ", ""));
		if(quartzCron.getAssignWeeks() !=null && !"".equals(quartzCron.getAssignWeeks()))
			quartzCron.setAssignWeeks(quartzCron.getAssignWeeks().replaceAll(" ", ""));
		Object _o =dao.insert(quartzCron);
		QuartzJob quartzJob = quartzJobService.selectById(Integer.parseInt(quartzCron.getQuartzJobId()));
		quartzJob.setCronExpress(quartzCron);
		quartzJobService.update(quartzJob);
		return _o.toString();
	}
	
	/**
	 * 更新对象
	 * 功能: <br/>
	 * 
	 * 重写：杜中良 <br/>
	 * 
	 * @version ：Jan 7, 2014 4:15:14 PM<br/>
	 * 
	 * @param quartzCron
	 * @throws Exception <br/>
	 * @see com.zhongpin.pap.base.BaseServiceImpl#update(com.zhongpin.pap.base.BaseEntity)
	 */
	public void update(QuartzCron quartzCron)throws Exception{
		if(quartzCron == null)
			throw new SystemException("更新对象不能为空");
		if(quartzCron.getAssignMinutes() !=null && !"".equals(quartzCron.getAssignMinutes()))
			quartzCron.setAssignMinutes(quartzCron.getAssignMinutes().replaceAll(" ", ""));
		if(quartzCron.getAssignHours() !=null && !"".equals(quartzCron.getAssignHours()))
			quartzCron.setAssignHours(quartzCron.getAssignHours().replaceAll(" ", ""));
		if(quartzCron.getAssignDays() !=null && !"".equals(quartzCron.getAssignDays()))
			quartzCron.setAssignDays(quartzCron.getAssignDays().replaceAll(" ", ""));
		if(quartzCron.getAssignMonths()!=null && !"".equals(quartzCron.getAssignMonths()))
			quartzCron.setAssignMonths(quartzCron.getAssignMonths().replaceAll(" ", ""));
		if(quartzCron.getAssignWeeks() !=null && !"".equals(quartzCron.getAssignWeeks()))
			quartzCron.setAssignWeeks(quartzCron.getAssignWeeks().replaceAll(" ", ""));
		quartzCron.setExpression(getCronExpress(quartzCron));
		dao.merge(quartzCron);
	}
}
