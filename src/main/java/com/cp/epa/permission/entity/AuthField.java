/**
 * 文件名：@AuthField.java <br/>
 * 包名：com.zhongpin.pap.permission.entity <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

import com.cp.epa.base.BaseEntity;
import com.cp.epa.bussobj.entity.BussinessEle;

/**
 * 类名：AuthField  <br />
 *
 * 功能：权限字段
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 上午09:17:48  <br />
 * @version 2013-11-6
 */
@Entity
@Table(name="base_authfield")
public class AuthField extends BaseEntity {

	private static final long serialVersionUID = -7863237976657175208L;

	/** 编码 */
	private String code;
	/** 名称 */
	private String name;
	/** 类型 */
	private String type;
	/** 业务元素 */
	private BussinessEle bussinessEle;
	
	/** 权限树 1=是，0=否 */
	private Integer tree;
	
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
	 * 获得 tree值
	 */
	public Integer getTree() {
		return tree;
	}
	/**
	 * 将tree设置为参数tree
	 */
	public void setTree(Integer tree) {
		this.tree = tree;
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
	 * 获得 type值
	 */
	public String getType() {
		return type;
	}
	/**
	 * 将type设置为参数type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获得 bussinessEle值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "businesselecode")
	public BussinessEle getBussinessEle() {
		return bussinessEle;
	}
	/**
	 * 将bussinessEle设置为参数bussinessEle
	 */
	public void setBussinessEle(BussinessEle bussinessEle) {
		this.bussinessEle = bussinessEle;
	}
	
}
