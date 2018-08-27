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
 * 类名：MethodDescDetail  <br />
 *
 * 功能：方法明细model
 *
 * @author zp <br />
 * 创建时间：2013-7-12 上午10:30:08  <br />
 * @version 2013-7-12
 */
@Entity
@Table(name="base_methoddescdetail")
public class MethodDescDetail extends BaseEntity{
	
	private Integer id;

	/**参数类型**/
	private String paramType;
	
	/**参数名称**/
	private String paramName;
	
	/**参数描述**/
	private String paramdesc;
	
	/**model对象**/
	private String model;	
	/**参数列表**/
	private String paramList;
	
	private MethodDesc methodDesc;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamdesc() {
		return paramdesc;
	}

	public void setParamdesc(String paramdesc) {
		this.paramdesc = paramdesc;
	}

	@ManyToOne
	@JoinColumn(name="methodId")
	public MethodDesc getMethodDesc() {
		return methodDesc;
	}

	public void setMethodDesc(MethodDesc methodDesc) {
		this.methodDesc = methodDesc;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getParamList() {
		return paramList;
	}

	public void setParamList(String paramList) {
		this.paramList = paramList;
	}
	
	
	
	
	
	
}
