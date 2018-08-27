/**
 * 文件名：@VacantManage.java <br/>
 * 包名：com.zhongpin.pap.number.entity <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：VacantManage  <br />
 *
 * 功能：空号管理
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 上午10:29:03  <br />
 * @version 2015-10-7
 */
@Entity
@Table(name = "base_vacantmanage")
public class VacantManage extends BaseEntity {

	private static final long serialVersionUID = -7478367707571272749L;

	/** 编号 */
	private Integer id;
	/** 业务对象编码 */
	private String bussObj;
	/** 业务对象名称 */
	private String bussName;
	/** 号码规则 */
	private VacantRule vacantRule;
	/** 运行状态*/
	private Character runStatus;
	/** 状态：N=禁用，Y=启用 */
	private Character status;

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
	public String getBussObj() {
		return bussObj;
	}

	/**
	 * 将bussObj设置为参数bussObj
	 */
	public void setBussObj(String bussObj) {
		this.bussObj = bussObj;
	}

	/**
	 * 获得 bussName值
	 */
	public String getBussName() {
		return bussName;
	}

	/**
	 * 将bussName设置为参数bussName
	 */
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}

	/**
	 * 获得 vacantRule值
	 */
	@OneToOne
	@JoinColumn(name = "vacantRuleId")
	public VacantRule getVacantRule() {
		return vacantRule;
	}

	/**
	 * 将vacantRule设置为参数vacantRule
	 */
	public void setVacantRule(VacantRule vacantRule) {
		this.vacantRule = vacantRule;
	}
	/**
	 * 获得 runStatus值
	 */
	public Character getRunStatus() {
		return runStatus;
	}

	/**
	 * 将runStatus设置为参数runStatus
	 */
	public void setRunStatus(Character runStatus) {
		this.runStatus = runStatus;
	}

	/**
	 * 获得 status值
	 */
	public Character getStatus() {
		return status;
	}

	/**
	 * 将status设置为参数status
	 */
	public void setStatus(Character status) {
		this.status = status;
	}
}
