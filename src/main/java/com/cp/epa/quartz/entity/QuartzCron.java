/**
 * 文件名：@QuartzCron.java <br/>
 * 包名：com.zhongpin.pap.quartz.entity <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.quartz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：QuartzCron  <br />
 *
 * 功能：调动作业规则表达式
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-1-6 下午02:52:17  <br />
 * @version 2014-1-6
 */
@Entity
@Table(name="base_quartzcron")
public class QuartzCron extends BaseEntity {

	private static final long serialVersionUID = -1197657984999102949L;

	/** 编号 */
	private Integer id;
	/** Cron 表达式 */
	private String expression;
	
	/** 规则设置项: 手动设置，自定义 */
	private Integer setOptions;
	
	/** 自定义项 */
	/** 秒 */
	private String dsecond;  
	/** 分钟 */
	private String dminute; 
	/** 小时 */
	private String dhour; 
	/** 天 */
	private String dday;  
	/** 月 */
	private String dmonth;  
	/** 星期 */
	private String dweek;  
	/** 年 */
	private String dyear;  
	
	/** 手动设置项 */
	/** 分钟条件项 */
	private Integer minuteOptions; 
	/** 开始分钟 */
	private Integer startMinute; 
	/** 结束分钟 */
	private Integer endMinute;
	/** 间隔分钟 */
	private Integer intervalMinute; 
	/** 指定分钟 */
	private String assignMinutes;  
	
	/** 小时条件项 */
	private Integer hourOptions;  
	/** 开始小时 */
	private Integer startHour;  
	/** 结束小时 */
	private Integer endHour;  
	/** 间隔小时 */
	private Integer intervalHour; 
	/** 指定小时 */
	private String assignHours;  
	
	/** 天条件项 */
	private Integer dayOptions; 
	/** 开始天 */
	private Integer startDay;  
	/** 结束天 */
	private Integer endDay;  
	/** 间隔天 */
	private Integer intervalDay;  
	/** 指定天 */
	private String assignDays;  
	
	/** 月份条件项 */
	private Integer monthOptions;  
	/** 开始月份 */
	private Integer startMonth;  
	/** 结束月份 */
	private Integer endMonth; 
	/** 间隔月份 */
	private Integer intervalMonth; 
	/** 指定月份 */
	private String assignMonths; 
	
	/** 使用星期 */
	private Character useWeek; 
	/** 星期条件项 */
	private Integer weekOptions; 
	/** 指定星期 */
	private String assignWeeks;  
	/** 所属作业的ID */
	private String quartzJobId;
	
	/**
	 * 获得 id值
	 */
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	/**
	 * 将id设置为参数id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获得 expression值
	 */
	public String getExpression() {
		return expression;
	}
	/**
	 * 将expression设置为参数expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}
	/**
	 * 获得 setOptions值
	 */
	public Integer getSetOptions() {
		return setOptions;
	}
	/**
	 * 将setOptions设置为参数setOptions
	 */
	public void setSetOptions(Integer setOptions) {
		this.setOptions = setOptions;
	}
	/**
	 * 获得 dsecond值
	 */
	public String getDsecond() {
		return dsecond;
	}
	/**
	 * 将dsecond设置为参数dsecond
	 */
	public void setDsecond(String dsecond) {
		this.dsecond = dsecond;
	}
	/**
	 * 获得 dminute值
	 */
	public String getDminute() {
		return dminute;
	}
	/**
	 * 将dminute设置为参数dminute
	 */
	public void setDminute(String dminute) {
		this.dminute = dminute;
	}
	/**
	 * 获得 dhour值
	 */
	public String getDhour() {
		return dhour;
	}
	/**
	 * 将dhour设置为参数dhour
	 */
	public void setDhour(String dhour) {
		this.dhour = dhour;
	}
	/**
	 * 获得 dday值
	 */
	public String getDday() {
		return dday;
	}
	/**
	 * 将dday设置为参数dday
	 */
	public void setDday(String dday) {
		this.dday = dday;
	}
	/**
	 * 获得 dmonth值
	 */
	public String getDmonth() {
		return dmonth;
	}
	/**
	 * 将dmonth设置为参数dmonth
	 */
	public void setDmonth(String dmonth) {
		this.dmonth = dmonth;
	}
	/**
	 * 获得 dweek值
	 */
	public String getDweek() {
		return dweek;
	}
	/**
	 * 将dweek设置为参数dweek
	 */
	public void setDweek(String dweek) {
		this.dweek = dweek;
	}
	/**
	 * 获得 dyear值
	 */
	public String getDyear() {
		return dyear;
	}
	/**
	 * 将dyear设置为参数dyear
	 */
	public void setDyear(String dyear) {
		this.dyear = dyear;
	}
	/**
	 * 获得 minuteOptions值
	 */
	public Integer getMinuteOptions() {
		return minuteOptions;
	}
	/**
	 * 将minuteOptions设置为参数minuteOptions
	 */
	public void setMinuteOptions(Integer minuteOptions) {
		this.minuteOptions = minuteOptions;
	}
	/**
	 * 获得 startMinute值
	 */
	public Integer getStartMinute() {
		return startMinute;
	}
	/**
	 * 将startMinute设置为参数startMinute
	 */
	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}
	/**
	 * 获得 endMinute值
	 */
	public Integer getEndMinute() {
		return endMinute;
	}
	/**
	 * 将endMinute设置为参数endMinute
	 */
	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}
	/**
	 * 获得 intervalMinute值
	 */
	public Integer getIntervalMinute() {
		return intervalMinute;
	}
	/**
	 * 将intervalMinute设置为参数intervalMinute
	 */
	public void setIntervalMinute(Integer intervalMinute) {
		this.intervalMinute = intervalMinute;
	}
	/**
	 * 获得 assignMinutes值
	 */
	public String getAssignMinutes() {
		return assignMinutes;
	}
	/**
	 * 将assignMinutes设置为参数assignMinutes
	 */
	public void setAssignMinutes(String assignMinutes) {
		this.assignMinutes = assignMinutes;
	}
	/**
	 * 获得 hourOptions值
	 */
	public Integer getHourOptions() {
		return hourOptions;
	}
	/**
	 * 将hourOptions设置为参数hourOptions
	 */
	public void setHourOptions(Integer hourOptions) {
		this.hourOptions = hourOptions;
	}
	/**
	 * 获得 startHour值
	 */
	public Integer getStartHour() {
		return startHour;
	}
	/**
	 * 将startHour设置为参数startHour
	 */
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}
	/**
	 * 获得 endHour值
	 */
	public Integer getEndHour() {
		return endHour;
	}
	/**
	 * 将endHour设置为参数endHour
	 */
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}
	/**
	 * 获得 intervalHour值
	 */
	public Integer getIntervalHour() {
		return intervalHour;
	}
	/**
	 * 将intervalHour设置为参数intervalHour
	 */
	public void setIntervalHour(Integer intervalHour) {
		this.intervalHour = intervalHour;
	}
	/**
	 * 获得 assignHours值
	 */
	public String getAssignHours() {
		return assignHours;
	}
	/**
	 * 将assignHours设置为参数assignHours
	 */
	public void setAssignHours(String assignHours) {
		this.assignHours = assignHours;
	}
	/**
	 * 获得 dayOptions值
	 */
	public Integer getDayOptions() {
		return dayOptions;
	}
	/**
	 * 将dayOptions设置为参数dayOptions
	 */
	public void setDayOptions(Integer dayOptions) {
		this.dayOptions = dayOptions;
	}
	/**
	 * 获得 startDay值
	 */
	public Integer getStartDay() {
		return startDay;
	}
	/**
	 * 将startDay设置为参数startDay
	 */
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}
	/**
	 * 获得 endDay值
	 */
	public Integer getEndDay() {
		return endDay;
	}
	/**
	 * 将endDay设置为参数endDay
	 */
	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}
	/**
	 * 获得 intervalDay值
	 */
	public Integer getIntervalDay() {
		return intervalDay;
	}
	/**
	 * 将intervalDay设置为参数intervalDay
	 */
	public void setIntervalDay(Integer intervalDay) {
		this.intervalDay = intervalDay;
	}
	/**
	 * 获得 assignDays值
	 */
	public String getAssignDays() {
		return assignDays;
	}
	/**
	 * 将assignDays设置为参数assignDays
	 */
	public void setAssignDays(String assignDays) {
		this.assignDays = assignDays;
	}
	/**
	 * 获得 monthOptions值
	 */
	public Integer getMonthOptions() {
		return monthOptions;
	}
	/**
	 * 将monthOptions设置为参数monthOptions
	 */
	public void setMonthOptions(Integer monthOptions) {
		this.monthOptions = monthOptions;
	}
	/**
	 * 获得 startMonth值
	 */
	public Integer getStartMonth() {
		return startMonth;
	}
	/**
	 * 将startMonth设置为参数startMonth
	 */
	public void setStartMonth(Integer startMonth) {
		this.startMonth = startMonth;
	}
	/**
	 * 获得 endMonth值
	 */
	public Integer getEndMonth() {
		return endMonth;
	}
	/**
	 * 将endMonth设置为参数endMonth
	 */
	public void setEndMonth(Integer endMonth) {
		this.endMonth = endMonth;
	}
	/**
	 * 获得 intervalMonth值
	 */
	public Integer getIntervalMonth() {
		return intervalMonth;
	}
	/**
	 * 将intervalMonth设置为参数intervalMonth
	 */
	public void setIntervalMonth(Integer intervalMonth) {
		this.intervalMonth = intervalMonth;
	}
	/**
	 * 获得 assignMonths值
	 */
	public String getAssignMonths() {
		return assignMonths;
	}
	/**
	 * 将assignMonths设置为参数assignMonths
	 */
	public void setAssignMonths(String assignMonths) {
		this.assignMonths = assignMonths;
	}
	/**
	 * 获得 useWeek值
	 */
	public Character getUseWeek() {
		return useWeek;
	}
	/**
	 * 将useWeek设置为参数useWeek
	 */
	public void setUseWeek(Character useWeek) {
		this.useWeek = useWeek;
	}
	/**
	 * 获得 weekOptions值
	 */
	public Integer getWeekOptions() {
		return weekOptions;
	}
	/**
	 * 将weekOptions设置为参数weekOptions
	 */
	public void setWeekOptions(Integer weekOptions) {
		this.weekOptions = weekOptions;
	}
	/**
	 * 获得 assignWeeks值
	 */
	public String getAssignWeeks() {
		return assignWeeks;
	}
	/**
	 * 将assignWeeks设置为参数assignWeeks
	 */
	public void setAssignWeeks(String assignWeeks) {
		this.assignWeeks = assignWeeks;
	}
	public String getQuartzJobId() {
		return quartzJobId;
	}
	public void setQuartzJobId(String quartzJobId) {
		this.quartzJobId = quartzJobId;
	}
	
}
