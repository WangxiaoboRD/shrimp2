/**
 * 文件名：@UserAuthValue.java <br/>
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
 * 类名：UserAuthValue  <br />
 *
 * 功能：用户权限规则
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 上午11:01:47  <br />
 * @version 2013-11-6
 */
@Entity
@Table(name="BASE_USERAUTHVALUE")
public class UserAuthValue extends BaseEntity {

	private static final long serialVersionUID = 6116560402568129102L;
	
	/** 编号 */
	private Integer id;
	/** 用户 */
	private Users user;
	/** 角色编码 */
	private String roleCode;
	/** 权限类型 */
	private String authType;
	/** 权限对象编码 */
	private String authObjCode;
	/** 权限字段编码 */
	private String authFieldCode;
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
	 * 获得 user值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usercode")
	public Users getUser() {
		return user;
	}
	/**
	 * 将user设置为参数user
	 */
	public void setUser(Users user) {
		this.user = user;
	}
	/**
	 * 获得 roleCode值
	 */
	public String getRoleCode() {
		return roleCode;
	}
	/**
	 * 将roleCode设置为参数roleCode
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	/**
	 * 获得 authType值
	 */
	public String getAuthType() {
		return authType;
	}
	/**
	 * 将authType设置为参数authType
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	/**
	 * 获得 authObjCode值
	 */
	public String getAuthObjCode() {
		return authObjCode;
	}
	/**
	 * 将authObjCode设置为参数authObjCode
	 */
	public void setAuthObjCode(String authObjCode) {
		this.authObjCode = authObjCode;
	}
	/**
	 * 获得 authFieldCode值
	 */
	public String getAuthFieldCode() {
		return authFieldCode;
	}
	/**
	 * 将authFieldCode设置为参数authFieldCode
	 */
	public void setAuthFieldCode(String authFieldCode) {
		this.authFieldCode = authFieldCode;
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

}
