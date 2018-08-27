/**
 * 文件名：@RoleAuthValue.java <br/>
 * 包名：com.zhongpin.pap.permission.entity <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：RoleAuthValue  <br />
 *
 * 功能：角色权限规则值明细
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 上午10:56:03  <br />
 * @version 2013-11-6
 */
@Entity
@Table(name="base_roleauthvaluedetail")
public class RoleAuthValueDetail extends BaseEntity {

	private static final long serialVersionUID = -6173152401212521199L;
	
	/** 编号 */
	private Integer id;
	/** 角色权限规则值编号 */
	private RoleAuthValue roleAuthValue;
	/** 连接符 */
	private String connector;
	/** 操作符 */
	private String operator;
	/** 规则值 */
	private String value;
	
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
	 * 获得 roleAuthValue值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleauthvalueid")
	public RoleAuthValue getRoleAuthValue() {
		return roleAuthValue;
	}
	/**
	 * 将roleAuthValue设置为参数roleAuthValue
	 */
	public void setRoleAuthValue(RoleAuthValue roleAuthValue) {
		this.roleAuthValue = roleAuthValue;
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
	 * 获得 connector值
	 */
	public String getConnector() {
		return connector;
	}
	/**
	 * 将connector设置为参数connector
	 */
	public void setConnector(String connector) {
		this.connector = connector;
	}
	/**
	 * 获得 operator值
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * 将operator设置为参数operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
