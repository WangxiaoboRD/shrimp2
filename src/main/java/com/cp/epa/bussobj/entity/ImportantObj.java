package com.cp.epa.bussobj.entity;
//DTO 关键属性
public class ImportantObj {

	private String id;
	
	private String value;

	private Integer baseColumnId;
	
	public ImportantObj(){}
	
	public ImportantObj(String id,String value,Integer baseColumnId){
		this.id=id;
		this.value=value;
		this.baseColumnId=baseColumnId;
	}
	
	
	public Integer getBaseColumnId() {
		return baseColumnId;
	}

	public void setBaseColumnId(Integer baseColumnId) {
		this.baseColumnId = baseColumnId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
