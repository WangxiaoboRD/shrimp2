package com.cp.epa.bussobj.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;
/**
 * 
 * 类名：MethodDesc  <br />
 *
 * 功能：
 * 方法主要描述
 * @author zp <br />
 * 创建时间：2013-7-12 上午10:24:26  <br />
 * @version 2013-7-12
 */
@Entity
@Table(name="base_methoddesc")
public class MethodDesc extends BaseEntity{

	private Integer id;
	
	/**方法名称**/
	private String methodName;
	
	/**描述**/
	private String descs;
	
	/**所在类**/
	private String classplace;
	
	/**参数列表**/
	private String paramlist;
	
	/**所属业务对象**/
	private BussinessObject bussinessObject;
	
	
    @Id
    @GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public String getClassplace() {
		return classplace;
	}

	public void setClassplace(String classplace) {
		this.classplace = classplace;
	}

	public String getParamlist() {
		return paramlist;
	}

	public void setParamlist(String paramlist) {
		this.paramlist = paramlist;
	}

	@ManyToOne
	@JoinColumn(name="bussCode")
	public BussinessObject getBussinessObject() {
		return bussinessObject;
	}

	public void setBussinessObject(BussinessObject bussinessObject) {
		this.bussinessObject = bussinessObject;
	}
	
	
	
}
