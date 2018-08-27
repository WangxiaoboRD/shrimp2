package com.cp.epa.log.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;
/**
 * 
 * 类名：LogClass  <br />
 *
 * 功能：日志类记录
 *
 * @author 席金红 <br />
 * 创建时间：2016-1-25 下午04:21:30  <br />
 * @version 2016-1-25
 */
@Entity
@Table(name="base_log_class")
public class LogClass extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String id; //编号
	private String className;//类名
	private String name;//名称
	
	/**
	 * 获得 id值
	 */
	@Id
	public String getId() {
		return id;
	}
	/**
	 * 将id设置为参数id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获得 className值
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 将className设置为参数className
	 */
	public void setClassName(String className) {
		this.className = className;
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
}
