package com.cp.epa.log.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：LogType  <br />
 *
 * 功能：日志类型
 *
 * @author 席金红 <br />
 * 创建时间：2016-1-25 上午09:17:49  <br />
 * @version 2016-1-25
 */
@Entity
@Table(name="base_log_type")
public class LogType extends BaseEntity {
	private String id;
	private String name;
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
