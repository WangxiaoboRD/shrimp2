package com.cp.epa.bussobj.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cp.epa.annotation.BussEle;
import com.cp.epa.base.BaseEntity;
/**
 * 
 * 类名：BussinessEleDetail  <br />
 *
 * 功能：
 * 业务元素明细
 * @author zp <br />
 * 创建时间：2013-7-2 上午09:39:00  <br />
 * @version 2013-7-2
 */
@BussEle(name = "业务元素明细")
@SuppressWarnings("serial")
@Entity
@Table(name="base_bussinesseledetail")
public class BussinessEleDetail extends BaseEntity{

	private String dcode;
	
	private String value;
	
	private BussinessEle bussinessEle;
	
	/** 备用字段1 */
	private String spareField1;
	/** 备注字段2 */
	private String spareField2;
	
	/**
	 * 构造方法
	 */
	public BussinessEleDetail(){
		
	}
	/**
	 * 构造方法
	 */
	public BussinessEleDetail(String dcode){
		this.dcode=dcode;
	}
	/**
	 * 构造方法
	 */
	public BussinessEleDetail(String dcode, String value) {
		super();
		this.dcode = dcode;
		this.value = value;
	}
	@Id
	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@ManyToOne
	@JoinColumn(name="bussinessEle")
	public BussinessEle getBussinessEle() {
		return bussinessEle;
	}

	public void setBussinessEle(BussinessEle bussinessEle) {
		this.bussinessEle = bussinessEle;
	}
	/**
	 * 获得 spareField1值
	 */
	public String getSpareField1() {
		return spareField1;
	}
	/**
	 * 将spareField1设置为参数spareField1
	 */
	public void setSpareField1(String spareField1) {
		this.spareField1 = spareField1;
	}
	/**
	 * 获得 spareField2值
	 */
	public String getSpareField2() {
		return spareField2;
	}
	/**
	 * 将spareField2设置为参数spareField2
	 */
	public void setSpareField2(String spareField2) {
		this.spareField2 = spareField2;
	}
}
