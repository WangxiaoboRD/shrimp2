/**
 * 文件名：@AuthObj.java <br/>
 * 包名：com.zhongpin.pap.permission.entity <br/>
 * 项目名：PAP1.0 <br/>
 * @author 孟雪勤 <br/>
 */
package com.cp.epa.permission.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.cp.epa.annotation.BussEle;
import com.cp.epa.base.BaseEntity;

/**
 * 类名：AuthObj  <br />
 *
 * 功能：权限对象
 *
 * @author 孟雪勤 <br />
 * 创建时间：2013-11-6 上午09:29:29  <br />
 * @version 2013-11-6
 */
@BussEle(name = "权限对象")
@Entity
@Table(name="base_authobj")
public class AuthObj extends BaseEntity {

	private static final long serialVersionUID = -3543019328855997885L;

	/** 编码 */
	private String code;
	/** 名称 */
	private String name;
	/** 类型 */
	private String type;
	/** 明细信息类型不能用Set，用Set的话接收不到前台传过来的值，所以用List */
	//private Set<AuthField> fieldSet = new HashSet<AuthField>();
	/** 所拥有的权限字段集合 */
	private List<AuthField> fieldSet = new ArrayList<AuthField>();
	/**
	 * 获得 code值
	 */
	@Id
	public String getCode() {
		return code;
	}
	/**
	 * 将code设置为参数code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获得 name值
	 */
	public String getName() {
		return name;
	}
	/**
	 * 将name设置为参数name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获得 type值
	 */
	public String getType() {
		return type;
	}
	/**
	 * 将type设置为参数type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 获得 fieldSet值
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "base_authobjfield", joinColumns = @JoinColumn(name = "authobjcode"), inverseJoinColumns = @JoinColumn(name = "authfieldcode"))
	public List<AuthField> getFieldSet() {
		return fieldSet;
	}
	/**
	 * 将fieldSet设置为参数fieldSet
	 */
	public void setFieldSet(List<AuthField> fieldSet) {
		this.fieldSet = fieldSet;
	}
	
}
