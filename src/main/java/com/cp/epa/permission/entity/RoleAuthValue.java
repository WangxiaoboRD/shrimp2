/**
 * 文件名：@RoleAuthValue.java <br/>
 * 包名：com.zhongpin.pap.permission.entity <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：RoleAuthValue  <br />
 *
 * 功能：角色权限规则值
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 上午10:56:03  <br />
 * @version 2013-11-6
 */
@Entity
@Table(name="base_roleauthvalue")
public class RoleAuthValue extends BaseEntity {

	private static final long serialVersionUID = -6173152401212521199L;
	
	/** 编号 */
	private Integer id;
	/** 角色 */
	private Role role;
	/** 权限对象 */
	private AuthObj authObj;
	/** 权限字段 */
	private AuthField authField;
	/** 规则值 */
	private String value;
	
	/** DTO 权限分配状态：已分配，未分配 */
	private String allotStatus;
	
	/** 角色权限规则明细集合 */
	private List<RoleAuthValueDetail> details = new ArrayList<RoleAuthValueDetail>();
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
	 * 获得 role值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rolecode")
	public Role getRole() {
		return role;
	}
	
	/**
	 * 获得 authObj值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authobjcode")
	public AuthObj getAuthObj() {
		return authObj;
	}
	/**
	 * 将authObj设置为参数authObj
	 */
	public void setAuthObj(AuthObj authObj) {
		this.authObj = authObj;
	}
	/**
	 * 获得 authField值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authfieldcode")
	public AuthField getAuthField() {
		return authField;
	}
	/**
	 * 将authField设置为参数authField
	 */
	public void setAuthField(AuthField authField) {
		this.authField = authField;
	}
	/**
	 * 将role设置为参数role
	 */
	public void setRole(Role role) {
		this.role = role;
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
	 * 获得 allotStatus值
	 */
	@Transient
	public String getAllotStatus() {
		return allotStatus;
	}
	/**
	 * 将allotStatus设置为参数allotStatus
	 */
	public void setAllotStatus(String allotStatus) {
		this.allotStatus = allotStatus;
	}
	/**
	 * 获得 details值
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "roleAuthValue")
	public List<RoleAuthValueDetail> getDetails() {
		return details;
	}
	/**
	 * 将details设置为参数details
	 */
	public void setDetails(List<RoleAuthValueDetail> details) {
		this.details = details;
	}
	
}
