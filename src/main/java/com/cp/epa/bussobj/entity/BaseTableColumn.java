package com.cp.epa.bussobj.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.cp.epa.base.BaseEntity;
/**
 * 
 * 类名：BaseTableColumn  <br />
 *
 * 功能：
 *
 * @author zp <br />
 * 创建时间：2013-7-5 上午09:22:59  <br />
 * @version 2013-7-5
 */
@SuppressWarnings("serial")
@Entity
@Table(name="base_basetablecolumn")
public class BaseTableColumn extends BaseEntity{

	
	private Integer id;
	
	/**字段编码**/
	private String fdcode;
	
	private BaseTable baseTable;

	/**业务元素**/
	private BussinessEle bussinessEle;
	
	
	/**引用表**/
	private String refTab;
	/**引用表key**/
	private String refKey;
	/**默认值**/
	private String defaultValue;
	/**是否是主键**/
	private Character isPk;
	
	/**手动**/
	private String isHand;
	
	/**关键属性**/
	private String isImportantKey;
	
	/**描述**/
	private String descs;
	
	/**标示类型，列标示或者行标示**/
	private String flagType;
	
	
	
	//手动输入
	private String dataType;
	//手动
	private Integer len;
	//手动
	private Integer decimalLen;
	
	
	//DTO 显示错误信息
	private String error;
	
	
	
	public BaseTableColumn(){
		
	}
	
	
	public BaseTableColumn(String dataType){
		
		this.dataType=dataType;		
	}
	
	
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getLen() {
		return len;
	}
	public void setLen(Integer len) {
		this.len = len;
	}
	public Integer getDecimalLen() {
		return decimalLen;
	}
	public void setDecimalLen(Integer decimalLen) {
		this.decimalLen = decimalLen;
	}
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="baseTable")
	public BaseTable getBaseTable() {
		return baseTable;
	}
	public void setBaseTable(BaseTable baseTable) {
		this.baseTable = baseTable;
	}
	
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name="bussinessEle")
	public BussinessEle getBussinessEle() {
		return bussinessEle;
	}
	public void setBussinessEle(BussinessEle bussinessEle) {
		this.bussinessEle = bussinessEle;
	}
	public String getRefTab() {
		return refTab;
	}
	public void setRefTab(String refTab) {
		this.refTab = refTab;
	}
	public String getRefKey() {
		return refKey;
	}
	public void setRefKey(String refKey) {
		this.refKey = refKey;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Character getIsPk() {
		return isPk;
	}
	public void setIsPk(Character isPk) {
		this.isPk = isPk;
	}
	public String getFdcode() {
		return fdcode;
	}
	public void setFdcode(String fdcode) {
		this.fdcode = fdcode;
	}
	public String getIsHand() {
		return isHand;
	}
	public void setIsHand(String isHand) {
		this.isHand = isHand;
	}


	@Transient
	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public String getIsImportantKey() {
		return isImportantKey;
	}


	public void setIsImportantKey(String isImportantKey) {
		this.isImportantKey = isImportantKey;
	}


	public String getDescs() {
		return descs;
	}


	public void setDescs(String descs) {
		this.descs = descs;
	}


	public String getFlagType() {
		return flagType;
	}


	public void setFlagType(String flagType) {
		this.flagType = flagType;
	}


	
	
}
