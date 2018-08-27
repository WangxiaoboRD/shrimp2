package com.cp.epa.check.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 审核对象
 * 类名：Check  
 *
 * 功能：
 *
 * @author dzl 
 * 创建时间：Dec 3, 2014 3:37:35 PM 
 * @version Dec 3, 2014
 */
@Entity
@Table(name="base_check")
public class Check extends BaseEntity{
	
	private int code; //编码
	private String objName;//单据类名
	private String bussName;//单据名
	private String objLevel;//审核级别
	
	@Id
	@GeneratedValue
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getObjLevel() {
		return objLevel;
	}
	public void setObjLevel(String objLevel) {
		this.objLevel = objLevel;
	}
	public String getBussName() {
		return bussName;
	}
	public void setBussName(String bussName) {
		this.bussName = bussName;
	}
}
