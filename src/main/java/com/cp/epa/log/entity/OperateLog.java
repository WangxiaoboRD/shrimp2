package com.cp.epa.log.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.annotation.BussEle;
import com.cp.epa.base.BaseEntity;

@BussEle(name = "操作日志")
@Entity
@Table(name="base_operatelog")
public class OperateLog extends BaseEntity{
	
	private static final long serialVersionUID = 5541243246548631438L;
	
	private int code;
	private String operateUserCode;
	private String operateDate;
	private String ip;
	private String operateType;
	private String operateModel;
	
	@Id
	@GeneratedValue
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getOperateUserCode() {
		return operateUserCode;
	}
	public void setOperateUserCode(String operateUserCode) {
		this.operateUserCode = operateUserCode;
	}
	public String getOperateDate() {
		return operateDate;
	}
	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperateModel() {
		return operateModel;
	}
	public void setOperateModel(String operateModel) {
		this.operateModel = operateModel;
	}
	
}
