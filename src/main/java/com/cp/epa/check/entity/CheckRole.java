package com.cp.epa.check.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cp.epa.base.BaseEntity;
import com.cp.epa.permission.entity.Role;

/**
 * 角色对每个单据的审核权限
 * 类名：Check  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 3:37:35 PM 
 * @version Dec 3, 2014
 */
@Entity
@Table(name="base_check_role")
public class CheckRole extends BaseEntity{
	
	private int code; //编码
	private String className;//类名
	private String bussName;//单据类名
	private Role role;//角色编码
	private String checkLevels;//角色审核权限
	
	private String checkLevelName;//审核权限名称
	
	@Id
	@GeneratedValue
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getBussName() {
		return bussName;
	}
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rolecode")
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getCheckLevels() {
		return checkLevels;
	}
	public void setCheckLevels(String checkLevels) {
		this.checkLevels = checkLevels;
	}
	@Transient
	public String getCheckLevelName() {
		return checkLevelName;
	}
	public void setCheckLevelName(String checkLevelName) {
		this.checkLevelName = checkLevelName;
	}
}
