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
 * 类名：SysParam  <br />
 *
 * 功能：系统参数
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-19 上午10:33:21  <br />
 * @version 2013-12-19
 */
@Entity
@Table(name="base_sysparam")
public class SysParam extends BaseEntity {

	private static final long serialVersionUID = -574711548131846358L;

	/** 编码 */
	private String code;
	/** 名称 */
	private String name;
	/** 值 */
	private String value;
	/** 默认值 */
	private String defaultValue;
	/** 描述 */
	private String description;
	
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
	 * 获得 value值
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 将value设置为参数value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获得 defaultValue值
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * 将defaultValue设置为参数defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	/**
	 * 获得 description值
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 将description设置为参数description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
