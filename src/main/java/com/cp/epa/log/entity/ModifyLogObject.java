package com.cp.epa.log.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;
import com.cp.epa.bussobj.entity.BussinessObject;

@Entity
@Table(name="base_modifylogobject")
public class ModifyLogObject extends BaseEntity {

	private static final long serialVersionUID = -2507011610513337443L;
	private Integer id;
	private BussinessObject bussObj;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="bussobj")
	public BussinessObject getBussObj() {
		return bussObj;
	}
	public void setBussObj(BussinessObject bussObj) {
		this.bussObj = bussObj;
	}
}
