/**
 * 文件名：@BussObjAuthProperty.java <br/>
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cp.epa.annotation.BussEle;
import com.cp.epa.base.BaseEntity;
import com.cp.epa.bussobj.entity.BussObjProperty;
import com.cp.epa.bussobj.entity.BussinessObject;

/**
 * 类名：BussObjAuthProperty  <br />
 *
 * 功能：业务对象权限过滤属性
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 上午09:54:42  <br />
 * @version 2013-11-6
 */
@BussEle(name = "业务对象权限过滤属性")
@Entity
@Table(name="base_bussobjauthproperty")
public class BussObjAuthProperty extends BaseEntity {

	private static final long serialVersionUID = -968274626208809989L;

	/** 编号 */
	private Integer id;
	/** 业务对象 */
	private BussinessObject bussObj;
	/** 权限过滤属性 */
	private BussObjProperty bussObjProperty;
	/** 业务对象属性全名称 */
	private String propertyFullName;
	/** 权限对象 */
	private AuthObj authObj;
	/** 权限字段 */
	private AuthField authField;
	/** 启用标识  1=已启用  0=未启用*/
	private Integer enabled;
	
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
	 * 获得 bussObj值
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bussobjcode")
	public BussinessObject getBussObj() {
		return bussObj;
	}
	/**
	 * 将bussObj设置为参数bussObj
	 */
	public void setBussObj(BussinessObject bussObj) {
		this.bussObj = bussObj;
	}
	/**
	 * 获得 bussproperty值
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "busspropertycode")
	public BussObjProperty getBussObjProperty() {
		return bussObjProperty;
	}
	/**
	 * 将bussproperty设置为参数bussproperty
	 */
	public void setBussObjProperty(BussObjProperty bussObjProperty) {
		this.bussObjProperty = bussObjProperty;
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
	 * 获得 enabled值
	 */
	public Integer getEnabled() {
		return enabled;
	}
	/**
	 * 将enabled设置为参数enabled
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	/**
	 * 获得 propertyFullName值
	 */
	public String getPropertyFullName() {
		return propertyFullName;
	}
	/**
	 * 将propertyFullName设置为参数propertyFullName
	 */
	public void setPropertyFullName(String propertyFullName) {
		this.propertyFullName = propertyFullName;
	}
}
