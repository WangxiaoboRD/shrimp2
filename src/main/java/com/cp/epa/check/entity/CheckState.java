package com.cp.epa.check.entity;

/**
 * 审核状况
 * 类名：CheckBill  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 3:37:35 PM 
 * @version Dec 3, 2014
 */
public class CheckState{
	
	private Character checkState;//审核状态
	private String userName;//审核人名称
	private String checkDate;//审核日期
	private String level;
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Character getCheckState() {
		return checkState;
	}
	public void setCheckState(Character checkState) {
		this.checkState = checkState;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
}
