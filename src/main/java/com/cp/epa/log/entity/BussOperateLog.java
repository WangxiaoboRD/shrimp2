/**
 * 文件名：@BussOperateLog.java <br/>
 * 包名：com.zhongpin.pap.log.entity <br/>
 * 项目名：ps <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.log.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

/**
 * 类名：BussOperateLog  <br />
 *
 * 功能：业务操作日志
 *
 * @author 孟雪勤 <br />
 * 创建时间：2016-1-25 下午02:16:33  <br />
 * @version 2016-1-25
 */
@Entity
@Table(name = "base_bussoperatelog")
public class BussOperateLog extends BaseEntity {

	private static final long serialVersionUID = -3436267360378581796L;

	/** 编号 */
	private Integer id;
	/** 业务模块编号 */
	private String moduleId;
	/** 业务模块 */
	private String moduleName;
	/** 业务类路径 */
	private String classPath;
	/** 业务类 */
	private String className;
	/** 业务方法 */
	private String methodName;
	/** 操作用户 */
	private String operUserName;
	/** 操作日期 */
	private String operDate;
	/** 操作IP */
	private String ip;
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
	 * 获得 moduleName值
	 */
	public String getModuleName() {
		return moduleName;
	}
	/**
	 * 将moduleName设置为参数moduleName
	 */
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	/**
	 * 获得 className值
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 将className设置为参数className
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获得 methodName值
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * 将methodName设置为参数methodName
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * 获得 operUserName值
	 */
	public String getOperUserName() {
		return operUserName;
	}
	/**
	 * 将operUserName设置为参数operUserName
	 */
	public void setOperUserName(String operUserName) {
		this.operUserName = operUserName;
	}
	/**
	 * 获得 operDate值
	 */
	public String getOperDate() {
		return operDate;
	}
	/**
	 * 将operDate设置为参数operDate
	 */
	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}
	/**
	 * 获得 ip值
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 将ip设置为参数ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获得 moduleId值
	 */
	public String getModuleId() {
		return moduleId;
	}
	/**
	 * 将moduleId设置为参数moduleId
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	/**
	 * 获得 classPath值
	 */
	public String getClassPath() {
		return classPath;
	}
	/**
	 * 将classPath设置为参数classPath
	 */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}
}
