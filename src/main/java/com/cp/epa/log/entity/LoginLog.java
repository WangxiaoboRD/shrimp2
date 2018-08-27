package com.cp.epa.log.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

@Entity
@Table(name="base_loginlog")
public class LoginLog extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private int code; //日志编码
	private String userCode;//操作人编码
	private String userName;//操作人名称
	private String ip;//操作人Ip
	private String loginDate;//操作时间
	
	@Id
	@GeneratedValue
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	
	

}
