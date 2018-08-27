/**
 * 文件名：@VacantRule.java <br/>
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
 * 类名：VacantRule  <br />
 *
 * 功能：空号规则
 *
 * @author 孟雪勤 <br />
 * 创建时间：2015-10-7 上午11:01:59  <br />
 * @version 2015-10-7
 */
@Entity
@Table(name = "base_vacantrule")
public class VacantRule extends BaseEntity {

	private static final long serialVersionUID = -1128767497983125911L;

	/** 编号 */
	private Integer id;
	/** 号码数量 */
	private Integer numberNum;
	/** 生成条件：0=每天，1=指定 */
	private String genOptions;
	/** 执行日期 */
	private String execDate;
	/** 开始小时 */
	private Integer startHour;
	/** 开始分钟 */
	private Integer startMinute;
	/** 结束小时 */
	private Integer endHour;
	/** 结束分钟 */
	private Integer endMinute;
	/** 空号规则 */
	private String vacantRule;
	/** 空号管理编号 */
	private Integer manageId;
	
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
	 * 获得 numberNum值
	 */
	public Integer getNumberNum() {
		return numberNum;
	}
	/**
	 * 将numberNum设置为参数numberNum
	 */
	public void setNumberNum(Integer numberNum) {
		this.numberNum = numberNum;
	}
	/**
	 * 获得 startHour值
	 */
	public Integer getStartHour() {
		return startHour;
	}
	/**
	 * 将startHour设置为参数startHour
	 */
	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}
	/**
	 * 获得 startMinute值
	 */
	public Integer getStartMinute() {
		return startMinute;
	}
	/**
	 * 将startMinute设置为参数startMinute
	 */
	public void setStartMinute(Integer startMinute) {
		this.startMinute = startMinute;
	}
	/**
	 * 获得 endHour值
	 */
	public Integer getEndHour() {
		return endHour;
	}
	/**
	 * 将endHour设置为参数endHour
	 */
	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}
	/**
	 * 获得 vacantRule值
	 */
	public String getVacantRule() {
		return vacantRule;
	}
	/**
	 * 将vacantRule设置为参数vacantRule
	 */
	public void setVacantRule(String vacantRule) {
		this.vacantRule = vacantRule;
	}
	/**
	 * 获得 execDate值
	 */
	public String getExecDate() {
		return execDate;
	}
	/**
	 * 将execDate设置为参数execDate
	 */
	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}
	/**
	 * 获得 manageId值
	 */
	public Integer getManageId() {
		return manageId;
	}
	/**
	 * 将manageId设置为参数manageId
	 */
	public void setManageId(Integer manageId) {
		this.manageId = manageId;
	}
	/**
	 * 获得 endMinute值
	 */
	public Integer getEndMinute() {
		return endMinute;
	}
	/**
	 * 将endMinute设置为参数endMinute
	 */
	public void setEndMinute(Integer endMinute) {
		this.endMinute = endMinute;
	}
	/**
	 * 获得 genOptions值
	 */
	public String getGenOptions() {
		return genOptions;
	}
	/**
	 * 将genOptions设置为参数genOptions
	 */
	public void setGenOptions(String genOptions) {
		this.genOptions = genOptions;
	}
	
}
