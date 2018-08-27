package com.cp.epa.quartz.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;
import com.cp.epa.quartz.utils.QuartzStatus;

@Entity
@Table(name="base_quartzjob")
public class QuartzJob extends BaseEntity{

	private static final long serialVersionUID = -9076801875232491650L;
	private Integer id; //编码
	private String quartzName;//名称
	private QuartzGroup quartzGroup;//所属工作组
	private String className;//类全名
	private QuartzCron cronExpress;//调度周期表达式
	private String quartzDesc;//调度描述
	private QuartzStatus quartzStatus;//状态 装载状态 LOAD 启动状态 ON 暂停状态 PAUSE 停止状态 STOP 卸载UNLOAD
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuartzName() {
		return quartzName;
	}
	public void setQuartzName(String quartzName) {
		this.quartzName = quartzName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cronExpress")
	public QuartzCron getCronExpress() {
		return cronExpress;
	}
	public void setCronExpress(QuartzCron cronExpress) {
		this.cronExpress = cronExpress;
	}
	public String getQuartzDesc() {
		return quartzDesc;
	}
	public void setQuartzDesc(String quartzDesc) {
		this.quartzDesc = quartzDesc;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quartzgroup")
	public QuartzGroup getQuartzGroup() {
		return quartzGroup;
	}
	
	public void setQuartzGroup(QuartzGroup quartzGroup) {
		this.quartzGroup = quartzGroup;
	}
	
	@Enumerated(value=EnumType.STRING)
	@Column(insertable=false,updatable=true)
	public QuartzStatus getQuartzStatus() {
		return quartzStatus;
	}
	
	public void setQuartzStatus(QuartzStatus quartzStatus) {
		this.quartzStatus = quartzStatus;
	}
	
}
