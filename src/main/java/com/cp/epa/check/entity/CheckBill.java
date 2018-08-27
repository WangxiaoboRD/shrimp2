package com.cp.epa.check.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 审核单据
 * 类名：CheckBill  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 3:37:35 PM 
 * @version Dec 3, 2014
 */
@Entity
@Table(name="base_check_bill")
public class CheckBill extends BaseEntity{
	
	private int code; //编码
	private String className;//单据类名
	private String FullName;//单据全名
	private String bussName;//业务名称
	
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
	public String getFullName() {
		return FullName;
	}
	public void setFullName(String fullName) {
		FullName = fullName;
	}
	public String getBussName() {
		return bussName;
	}
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}
}
