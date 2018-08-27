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
@Table(name="base_debugsystem")
public class DebugSystem extends BaseEntity {

	private static final long serialVersionUID = -4476620150136436686L;
	private Integer id;//编码
	private String operatorId;//操作人ID
	private String operatorName;//操作人
	private String operTime;//操作时间
	private String operBusiness;//跟踪业务
	private String operFunction;//操作方法
	private String serviceFunction;//服务方法
	private String followFunction;//跟踪方法
	private String timeConsuming;//耗时
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getServiceFunction() {
		return serviceFunction;
	}
	public void setServiceFunction(String serviceFunction) {
		this.serviceFunction = serviceFunction;
	}
}
