package com.cp.epa.log.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

@Entity
@Table(name = "base_exceptionlog")
public class ExceptionLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private int code; // 日志编码
	private String operateUserCode;// 操作人编码
	private String operateDate;// 操作时间
	private String ip;// 操作人Ip
	private String operateType;
	private String exceptionInfo;// 操作人名称

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

	public String getExceptionInfo() {
		return exceptionInfo;
	}

	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
}
