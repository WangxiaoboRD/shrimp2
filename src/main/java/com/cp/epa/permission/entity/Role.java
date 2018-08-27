package com.cp.epa.permission.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.struts2.json.annotations.JSON;

import com.cp.epa.annotation.BussEle;
import com.cp.epa.base.BaseEntity;
@BussEle(name = "角色")
@Entity
@Table(name="base_role")
public class Role extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String roleCode;
	private String roleName;
	private int roleStatus;
	private String roleDesc;
	
	private List<Users> users = new ArrayList<Users>();
	/** 权限对象集合：多对多 */
	private List<AuthObj> authObjSet = new ArrayList<AuthObj>();
	/** DTO角色权限规则值集合：一对多 */
	private List<RoleAuthValue> authValues = new ArrayList<RoleAuthValue>();
	
	
	@Id
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(int roleStatus) {
		this.roleStatus = roleStatus;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	//@ManyToMany(mappedBy = "roleSet",fetch = FetchType.LAZY)
	@Transient
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	/**
	 * 获得 authValues值
	 */
	@Transient
	public List<RoleAuthValue> getAuthValues() {
		return authValues;
	}
	/**
	 * 将authValues设置为参数authValues
	 */
	public void setAuthValues(List<RoleAuthValue> authValues) {
		this.authValues = authValues;
	}
	/**
	 * 获得 authObjSet值
	 */
	@JSON(serialize=false)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_roleauthobj",joinColumns=@JoinColumn(name="rolecode"),inverseJoinColumns=@JoinColumn(name="authobjcode"))
	public List<AuthObj> getAuthObjSet() {
		return authObjSet;
	}
	/**
	 * 将authObjSet设置为参数authObjSet
	 */
	public void setAuthObjSet(List<AuthObj> authObjSet) {
		this.authObjSet = authObjSet;
	}
}
