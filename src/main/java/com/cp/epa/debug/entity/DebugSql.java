/**
 * 文件名：@DebugSql.java <br/>
 * 包名：com.zhongpin.pm.server.dao.model <br/>
 * 项目名：pscm <br/>
 * @author dzl <br/>
 */
package com.cp.epa.debug.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：DebugSql  <br />
 *
 * 功能：sql调试跟踪
 *
 * @author DZL <br />
 * 创建时间：2013-1-8 下午06:15:28  <br />
 * @version 2013-1-8
 */

@Entity
@Table(name="base_debugsql")
public class DebugSql extends BaseEntity {

	private static final long serialVersionUID = -9115984125373586701L;
	private Integer id;
	private String operatorId;//操作人ID
	private String operatorName;//操作人
	private String operTime;//操作时间
	private String operBusiness;//跟踪业务
	private String operFunction;//操作方法
	private String serviceFunction;//service方法
	private String followFunction;//跟踪方法
	private String connectId;//数据库连接id
	private String category;//语句类别
	private String oldHql;
	private String oldSql;//原始sql
	private String newSql;//转换sql
	private String eventMark;//作业标示
	private String timeConsuming;//耗时
	private String fileName;//提取文件名
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getTimeConsuming() {
		return timeConsuming;
	}
	public void setTimeConsuming(String timeConsuming) {
		this.timeConsuming = timeConsuming;
	}
	
	
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public String getEventMark() {
		return eventMark;
	}
	public void setEventMark(String eventMark) {
		this.eventMark = eventMark;
	}
	public String getOperBusiness() {
		return operBusiness;
	}
	public void setOperBusiness(String operBusiness) {
		this.operBusiness = operBusiness;
	}
	public String getOperFunction() {
		return operFunction;
	}
	public void setOperFunction(String operFunction) {
		this.operFunction = operFunction;
	}
	public String getFollowFunction() {
		return followFunction;
	}
	public void setFollowFunction(String followFunction) {
		this.followFunction = followFunction;
	}
	public String getConnectId() {
		return connectId;
	}
	public void setConnectId(String connectId) {
		this.connectId = connectId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getOldSql() {
		return oldSql;
	}
	public void setOldSql(String oldSql) {
		this.oldSql = oldSql;
	}
	public String getNewSql() {
		return newSql;
	}
	public void setNewSql(String newSql) {
		this.newSql = newSql;
	}
	public String getServiceFunction() {
		return serviceFunction;
	}
	public void setServiceFunction(String serviceFunction) {
		this.serviceFunction = serviceFunction;
	}
	public String getOldHql() {
		return oldHql;
	}
	public void setOldHql(String oldHql) {
		this.oldHql = oldHql;
	}
}
