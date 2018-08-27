/**
 * 文件名：@SysParam.java <br/>
 * 包名：com.zhongpin.pap.permission.entity <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.sysconfig.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：SysSwitch  <br />
 *
 * 功能：系统开关
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-19 上午10:33:21  <br />
 * @version 2013-12-19
 */
@Entity
@Table(name="base_sysswitch")
public class SysSwitch extends BaseEntity {

	private static final long serialVersionUID = 6202873982616685122L;
	/** 编码 */
	private String code;
	/** 名称 */
	private String name;
	/** 状态: 开启=Y/未开启=N */
	private String status;
	
	/**
	 * 构造方法
	 */
	public SysSwitch() {
		super();
	}
	/**
	 * 构造方法
	 */
	public SysSwitch(String code, String status) {
		super();
		this.code = code;
		this.status = status;
	}
	/**
	 * 获得 code值
	 */
	@Id
	public String getCode() {
		return code;
	}
	/**
	 * 将code设置为参数code
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * 获得 status值
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 将status设置为参数status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
