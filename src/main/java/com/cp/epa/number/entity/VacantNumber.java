/**
 * 文件名：@VacantNumber.java <br/>
 * 包名：com.zhongpin.pap.number.entity <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.number.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：VacantNumber  <br />
 *
 * 功能：空置号码
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 上午10:29:41  <br />
 * @version 2015-10-7
 */
@Entity
@Table(name = "base_vacantnumber")
public class VacantNumber extends BaseEntity {

	private static final long serialVersionUID = 5712224067920406627L;

	/** 编号 */
	private Integer id;
	/** 业务对象编码 */
	private String bussObj;
	/** 业务对象名称 */
	private String bussName;
	/** 生成日期 */
	private String generateDate;
	/** 业务编号 */
	private String bussNumber;
	/** 使用状态：N=未使用，Y=已使用 */
	private Character useStatus;
	
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
	 * 获得 generateDate值
	 */
	public String getGenerateDate() {
		return generateDate;
	}
	/**
	 * 将generateDate设置为参数generateDate
	 */
	public void setGenerateDate(String generateDate) {
		this.generateDate = generateDate;
	}
	/**
	 * 获得 bussNumber值
	 */
	public String getBussNumber() {
		return bussNumber;
	}
	/**
	 * 将bussNumber设置为参数bussNumber
	 */
	public void setBussNumber(String bussNumber) {
		this.bussNumber = bussNumber;
	}
	/**
	 * 获得 useStatus值
	 */
	public Character getUseStatus() {
		return useStatus;
	}
	/**
	 * 将useStatus设置为参数useStatus
	 */
	public void setUseStatus(Character useStatus) {
		this.useStatus = useStatus;
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
	
}
