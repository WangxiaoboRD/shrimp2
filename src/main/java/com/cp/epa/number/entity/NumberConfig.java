package com.cp.epa.number.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

@Entity
@Table(name="base_numberconfig")
public class NumberConfig extends BaseEntity {
	
	private static final long serialVersionUID = -3086268573593733085L;
	private Integer id;
	private String objectCode;//业务对象编码
	private String objectName;//业务对象名称
	private String hingeKey;//关键属性
	private String hingeValue;//关键属性值
	private NumberDetail numberDetail;//绑定的明细
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getObjectCode() {
		return objectCode;
	}
	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	@ManyToOne
	@JoinColumn(name="numberDetail")
	public NumberDetail getNumberDetail() {
		return numberDetail;
	}
	public void setNumberDetail(NumberDetail numberDetail) {
		this.numberDetail = numberDetail;
	}
	public String getHingeKey() {
		return hingeKey;
	}
	public void setHingeKey(String hingeKey) {
		this.hingeKey = hingeKey;
	}
	public String getHingeValue() {
		return hingeValue;
	}
	public void setHingeValue(String hingeValue) {
		this.hingeValue = hingeValue;
	}
	
	public boolean equals(NumberConfig obj){  
		   
    	if(obj != null){  
    	 	String oldNumberConfig = this.getObjectCode();
    		String newNumberConfig = obj.getObjectCode();
    		
    		if(hingeKey != null){
    			oldNumberConfig += this.getHingeKey();
    			newNumberConfig += obj.getHingeKey();
    		}
    		if(hingeValue !=null){
    			oldNumberConfig += this.getHingeValue();
    			newNumberConfig += obj.getHingeValue();
    		}
    		if(oldNumberConfig.equals(newNumberConfig)){
    			return true;
    		}
        }  
    	return false;
    } 
    
    public int hashCode(){
    	return this.hashCode();
    }
	
}
