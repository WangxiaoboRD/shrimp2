package com.cp.epa.bussobj.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cp.epa.base.BaseEntity;

/**
 * 
 * 类名：BussObjProperty  <br />
 *
 * 功能：
 * 业务对象属性
 * @author zp <br />
 * 创建时间：2013-8-8 上午10:28:00  <br />
 * @version 2013-8-8
 */
@SuppressWarnings("serial")
@Entity
@Table(name="base_bussobjproperty")
public class BussObjProperty extends BaseEntity{
	
	private Integer id;
	
	/**属性名**/
	private String propertyName;
	
	/**对应的表字段名称**/
	private String fieldName;
	
	/**是否是根节点**/
	private String isRoot;
	
	/**属性类型类全名**/
	private String className;
	
	/**属性是不是引用的业务对象**/
	private String isBussObj;	
	
	/** 是否为权限过滤属性 */
	private String isAuthProperty;
	/** 所属类 **/
	private String belongClass;
	
	/**父节点**/
	private Integer pid;

	/**子节点**/
	private List<BussObjProperty> children;
	
	/**表管理明细,没有实体存储，因为保存属性的时候，可能还没有创建对应的表管理**/
	private BaseTableColumn btc;
	
	
	
	//DTO 字段类型
	private String fieldtype;
	//DTO 字段长度
	private String fieldLen;
	//DTO 是不是主键
	private String isPk;
	//DTO 业务元素编码
	private String bussEleCode;
	
	//DTO 关键属性
	private String isImportantKey;
	//DTO 描述
	private String descs;
	private String isexpand;
	
	/** DTO引用对象属性 */
	private String quoteObjPropertyName;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public String getPropertyName() {
		return propertyName;
	}
	
	/**
	 * 获得 isAuthProperty值
	 */
	public String getIsAuthProperty() {
		return isAuthProperty;
	}

	/**
	 * 将isAuthProperty设置为参数isAuthProperty
	 */
	public void setIsAuthProperty(String isAuthProperty) {
		this.isAuthProperty = isAuthProperty;
	}

	public String getIsRoot() {
		return isRoot;
	}

	public String getClassName() {
		return className;
	}
	
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setIsRoot(String isRoot) {
		this.isRoot = isRoot;
	}

	public void setClassName(String className) {
		this.className = className;
	}


	public String getIsBussObj() {
		return isBussObj;
	}

	public void setIsBussObj(String isBussObj) {
		this.isBussObj = isBussObj;
	}

	@Transient
	public List<BussObjProperty> getChildren() {
		return children;
	}

	public void setChildren(List<BussObjProperty> children) {
		this.children = children;
	}

	@Transient
	public String getIsexpand() {
		return isexpand;
	}

	public void setIsexpand(String isexpand) {
		this.isexpand = isexpand;
	}

	@Transient
	public BaseTableColumn getBtc() {
		return btc;
	}

	public void setBtc(BaseTableColumn btc) {
		this.btc = btc;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	
	@Transient
	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}
    
	@Transient
	public String getFieldLen() {
		return fieldLen;
	}

	public void setFieldLen(String fieldLen) {
		this.fieldLen = fieldLen;
	}

	@Transient
	public String getIsPk() {
		return isPk;
	}

	public void setIsPk(String isPk) {
		this.isPk = isPk;
	}

	@Transient
	public String getBussEleCode() {
		return bussEleCode;
	}

	public void setBussEleCode(String bussEleCode) {
		this.bussEleCode = bussEleCode;
	}

	@Transient
	public String getIsImportantKey() {
		return isImportantKey;
	}

	public void setIsImportantKey(String isImportantKey) {
		this.isImportantKey = isImportantKey;
	}

	@Transient
	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public String getBelongClass() {
		return belongClass;
	}

	public void setBelongClass(String belongClass) {
		this.belongClass = belongClass;
	}

	/**
	 * 获得 quoteObjPropertyName值
	 */
	@Transient
	public String getQuoteObjPropertyName() {
		return quoteObjPropertyName;
	}

	/**
	 * 将quoteObjPropertyName设置为参数quoteObjPropertyName
	 */
	public void setQuoteObjPropertyName(String quoteObjPropertyName) {
		this.quoteObjPropertyName = quoteObjPropertyName;
	}
}
