package com.cp.epa.quartz.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：QuartzGroup  <br />
 *
 * 功能：调度作业组
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-1-4 下午05:15:20  <br />
 * @version 2014-1-4
 */
@Entity
@Table(name="base_quartzgroup")
public class QuartzGroup extends BaseEntity{

	private static final long serialVersionUID = 7883092323671825125L;

	/** 组编号 */
	private Integer id;
	
	/** 组名称 */
	private String name;

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
	
}
