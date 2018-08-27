package com.cp.epa.number.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.cp.epa.base.BaseEntity;
import com.cp.epa.bussobj.entity.BussinessEle;

@Entity
@Table(name="base_number")
public class Number extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private String id;//号码编号
	private String numberName;//号码对象名称
	private int length;//号码长度
	private int step;//步长
	private int cacheNumber;//缓存数
	private int warnNumber;//警告数
	private int markYear;//年度标示
	private BussinessEle markSub;//子对象标示
	
	private List<NumberDetail> numberDetails = new ArrayList<NumberDetail>();
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumberName() {
		return numberName;
	}
	public void setNumberName(String numberName) {
		this.numberName = numberName;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getCacheNumber() {
		return cacheNumber;
	}
	public void setCacheNumber(int cacheNumber) {
		this.cacheNumber = cacheNumber;
	}
	public int getWarnNumber() {
		return warnNumber;
	}
	public void setWarnNumber(int warnNumber) {
		this.warnNumber = warnNumber;
	}
	public int getMarkYear() {
		return markYear;
	}
	public void setMarkYear(int markYear) {
		this.markYear = markYear;
	}
	@ManyToOne
	@JoinColumn(name="markSub")
	public BussinessEle getMarkSub() {
		return markSub;
	}
	public void setMarkSub(BussinessEle markSub) {
		this.markSub = markSub;
	}
	@OneToMany(mappedBy="number")
	@Cascade({CascadeType.ALL})
	public List<NumberDetail> getNumberDetails() {
		return numberDetails;
	}
	public void setNumberDetails(List<NumberDetail> numberDetails) {
		this.numberDetails = numberDetails;
	}
	
}
