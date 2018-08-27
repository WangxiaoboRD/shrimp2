package com.cp.epa.bussobj.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.cp.epa.annotation.BussEle;
import com.cp.epa.base.BaseEntity;

/**
 * 
 * 类名：BussinessEle <br />
 * 
 * 功能： 业务元素
 * 
 * @author zp <br />
 *         创建时间：2013-7-1 上午10:24:33 <br />
 * @version 2013-7-1
 */
@BussEle(name = "业务元素")
@Entity
@Table(name = "base_bussinessele")
@SuppressWarnings("serial")
public class BussinessEle extends BaseEntity {

	/** 业务元素编码 */
	private String ecode;
	/** 业务元素名称 */
	private String ename;
	/** 数据类型 */
	private String dataType;
	/** 类型长度 */
	private Integer len;
	/** 小数位数 */
	private Integer decimalLen;
	/** 参考业务元素 */
	private BussinessEle refCode;
	/** 类别 */
	private String classType;

	/** 参考业务元素的超级父类 */
	// private BusinessEle refCodeSuperFather;
	/** 指定的业务组件名称 */
	private String component;
	/** 指定业务组件的参数 */
	private String param;

	/** 值类型: 0-无，1-范围段，2-参考表，3-固定值 */
	private String valueType;

	/** 值类型【1】的最小值 */
	private Integer lessValue;
	/** 值类型【1】的最大值 */
	private Integer greatValue;

	/*
	 * 选择标示为2时 可以填充以下三个字段：refTable,refTabField,refTabParTield
	 * 分别指代：参考表、参考表字段、参考表中的父字段
	 */
	/** 值类型【2】的参考表 */
	private String refTable;
	/** 值类型【2】的参考表字段 */
	private String refTabField;
	/** 值类型【2】的参考表父级字段 */
	private String refTabParField;
	/** 值类型【2】的参考表字段描述 */
	private String refFieldName;

	/** Y=是，N=否 */
	private String flagLog;
	/** 描述 */
	private String descs;

	// DTO
	private String fieldcode;
	// DTO
	private String fieldname;
	// DTO
	private List<BussinessEleDetail> details;

	public BussinessEle() {

	}

	public BussinessEle(String fieldcode, String fieldname) {
		this.fieldcode = fieldcode;
		this.fieldname = fieldname;
	}

	@Id
	public String getEcode() {
		return ecode;
	}

	public void setEcode(String ecode) {
		this.ecode = ecode;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
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

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "refCode")
	public BussinessEle getRefCode() {
		return refCode;
	}

	public void setRefCode(BussinessEle refCode) {
		this.refCode = refCode;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Integer getLessValue() {
		return lessValue;
	}

	public void setLessValue(Integer lessValue) {
		this.lessValue = lessValue;
	}

	public Integer getGreatValue() {
		return greatValue;
	}

	public void setGreatValue(Integer greatValue) {
		this.greatValue = greatValue;
	}

	public String getRefTable() {
		return refTable;
	}

	public void setRefTable(String refTable) {
		this.refTable = refTable;
	}

	public String getRefTabField() {
		return refTabField;
	}

	public void setRefTabField(String refTabField) {
		this.refTabField = refTabField;
	}

	public String getRefTabParField() {
		return refTabParField;
	}

	public void setRefTabParField(String refTabParField) {
		this.refTabParField = refTabParField;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getFlagLog() {
		return flagLog;
	}

	public void setFlagLog(String flagLog) {
		this.flagLog = flagLog;
	}

	@Transient
	public String getFieldcode() {
		return fieldcode;
	}

	public void setFieldcode(String fieldcode) {
		this.fieldcode = fieldcode;
	}

	@Transient
	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	@Transient
	public List<BussinessEleDetail> getDetails() {
		return details;
	}

	public void setDetails(List<BussinessEleDetail> details) {
		this.details = details;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public String getRefFieldName() {
		return refFieldName;
	}

	public void setRefFieldName(String refFieldName) {
		this.refFieldName = refFieldName;
	}

}
