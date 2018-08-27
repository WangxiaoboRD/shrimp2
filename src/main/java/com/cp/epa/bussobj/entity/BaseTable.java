package com.cp.epa.bussobj.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.cp.epa.base.BaseEntity;

/**
 * 类名：BaseTable  <br />
 *
 * 功能：表
 *
 * @author 孟雪勤 <br />
 * 创建时间：2014-7-1 下午05:23:12  <br />
 * @version 2014-7-1
 */
@Entity
@Table(name="base_basetable")
public class BaseTable extends BaseEntity{
	
	private static final long serialVersionUID = -3906882281685522329L;
	/** 表编码 */
	private String tabCode;
	/** 表描述 */
	private String tabName;
	/**版本号**/
	private Integer tabVer;
	/**表类型: S=系统表，B=业务表*/
	private Character tabType;
	
	/**是否激活**/
	private Character isActiveable;
	
	/** DTO 表列明细 */
	private List<BaseTableColumn> details;

	@Id
	public String getTabCode() {
		return tabCode;
	}

	public void setTabCode(String tabCode) {
		this.tabCode = tabCode;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public Integer getTabVer() {
		return tabVer;
	}

	public void setTabVer(Integer tabVer) {
		this.tabVer = tabVer;
	}

	/**
	 * 获得 tabType值
	 */
	public Character getTabType() {
		return tabType;
	}

	/**
	 * 将tabType设置为参数tabType
	 */
	public void setTabType(Character tabType) {
		this.tabType = tabType;
	}

	@Column(insertable=false)
	public Character getIsActiveable() {
		return isActiveable;
	}

	public void setIsActiveable(Character isActiveable) {
		this.isActiveable = isActiveable;
	}

	@Transient
	public List<BaseTableColumn> getDetails() {
		return details;
	}

	public void setDetails(List<BaseTableColumn> details) {
		this.details = details;
	}

}
