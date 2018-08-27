package com.cp.epa.bussobj.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cp.epa.annotation.BussEle;
import com.cp.epa.base.BaseEntity;
import com.cp.epa.permission.entity.BussObjAuthProperty;
/**
 * 
 * 类名：BussinessObject  <br />
 *
 * 功能：
 * 业务对象
 * @author zp <br />
 * 创建时间：2013-7-10 上午10:03:12  <br />
 * @version 2013-7-10
 */
@BussEle(name = "业务对象")
@Entity
@Table(name="base_bussinessobject")
public class BussinessObject extends BaseEntity{

	private static final long serialVersionUID = -3590381474069208034L;

	/**编码**/
	private String bussCode;
	/**名称**/
	private String bussName;
	/**所在类全名**/
	private String fullClassName;
	/**业务对象类型**/
	private String bussType;
	/** 对应表编码 */
	private String tableCode;
	/** DTO业务对象权限过滤属性 */
	private List<BussObjAuthProperty> authProperties = new ArrayList<BussObjAuthProperty>();
	
	//变更日志标示 取值"N",与"Y","Y"代表允许记录日志，"N"代表不允许记录日志
	//private String logFlag;
	
	public String getBussName() {
		return bussName;
	}

	public void setBussName(String bussName) {
		this.bussName = bussName;
	}

	/**关联表管理**/
	private BaseTable baseTable;

	@Id
	public String getBussCode() {
		return bussCode;
	}

	public void setBussCode(String bussCode) {
		this.bussCode = bussCode;
	}

	@Transient
	public BaseTable getBaseTable() {
		return baseTable;
	}

	public void setBaseTable(BaseTable baseTable) {
		this.baseTable = baseTable;
	}

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}


	public String getBussType() {
		return bussType;
	}

	public void setBussType(String bussType) {
		this.bussType = bussType;
	}

	/**
	 * 获得 authProperties值
	 */
	@Transient
	public List<BussObjAuthProperty> getAuthProperties() {
		return authProperties;
	}

	/**
	 * 将authProperties设置为参数authProperties
	 */
	public void setAuthProperties(List<BussObjAuthProperty> authProperties) {
		this.authProperties = authProperties;
	}

	/**
	 * 获得 tableCode值
	 */
	public String getTableCode() {
		return tableCode;
	}

	/**
	 * 将tableCode设置为参数tableCode
	 */
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

//	@Column(insertable=false)
//	public String getLogFlag() {
//		return logFlag;
//	}
//
//	public void setLogFlag(String logFlag) {
//		this.logFlag = logFlag;
//	}
}
