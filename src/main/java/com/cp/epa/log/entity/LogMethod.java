package com.cp.epa.log.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：LogMethod  <br />
 *
 * 功能：日志方法
 *
 * @author 席金红 <br />
 * 创建时间：2016-1-25 上午09:40:36  <br />
 * @version 2016-1-25
 */
@Entity
@Table(name="base_log_method")
public class LogMethod extends BaseEntity {
	/**编号*/
	private Integer id;
	/**中文名称*/
	private String name;
//	/**类名称*/
//	private String className;
	/**方法名称*/
	private String methodName;
	/***类名称*/
	private LogClass logClass;
	private LogType logType;//日志类别
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
	 * 获得 name值
	 */
	public String getName() {
		return name;
	}
	/**
	 * 将name设置为参数name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获得 methodName值
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 将methodName设置为参数methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获得 logClass值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logclass")
	public LogClass getLogClass() {
		return logClass;
	}
	/**
	 * 将logClass设置为参数logClass
	 */
	public void setLogClass(LogClass logClass) {
		this.logClass = logClass;
	}
	/**
	 * 获得 logType值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logtype")
	public LogType getLogType() {
		return logType;
	}
	/**
	 * 将logType设置为参数logType
	 */
	public void setLogType(LogType logType) {
		this.logType = logType;
	}
}
