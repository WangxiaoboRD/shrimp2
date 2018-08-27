/**
 * 文件名：@Report.java <br/>
 * 包名：com.zhongpin.pap.bussobj.entity <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.report.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：Report  <br />
 *
 * 功能：报表
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-12-10 上午11:42:14  <br />
 * @version 2013-12-10
 */
@Entity
@Table(name="base_report")
public class Report extends BaseEntity {

	private static final long serialVersionUID = 3046863673020089916L;

	/** 编码 */
	private String code;
	/** 名称 */
	private String name;
	/** 资源 */
	private String urlPath;
	/** 参数 */
	private String params;
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
	 * 获得 params值
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 将params设置为参数params
	 */
	public void setParams(String params) {
		this.params = params;
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
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
}
