package com.cp.epa.base;
import java.io.Serializable;
import java.util.Map;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

@MappedSuperclass 
//public abstract class BaseEntity<ID extends Serializable> implements Serializable{
public abstract class BaseEntity implements Serializable{	

	// 日志记录器 
	protected static final Logger logger = Logger.getLogger(BaseEntity.class);	
	
	//id
	//private ID id;
	//添加人	
	public String createUser;
	// 创建日期
	public String createDate;
	//修改人
	public String modifyUser;	
	// 修改日期
	public String modifyDate;
	// DTO用来放一些临时数据 
	public Map<String, String> tempStack;
	//存放对象提交的属性
	public Map<String,Object> map;
	//用于处理导出时，所需要导出的字段
	public String exportFields;

	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	/**
	 * 获得 tempStack值
	 */
	@Transient
	public Map<String, String> getTempStack() {
		return tempStack;
	}
	/**
	 * 将tempStack设置为参数tempStack
	 */
	public void setTempStack(Map<String, String> tempStack) {
		this.tempStack = tempStack;
	}
	@Transient
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	@Transient
	public String getExportFields() {
		return exportFields;
	}
	public void setExportFields(String exportFields) {
		this.exportFields = exportFields;
	}
}
