package com.cp.epa.number.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cp.epa.base.BaseEntity;

@Entity
@Table(name="base_numberdetail")
public class NumberDetail extends BaseEntity {

	private static final long serialVersionUID = 8667147715903941732L;
	private Integer id;//编码
	private String numberScope;//号码段
	private String year;//年份
	private String subobject;//子对象
	private int startNumber;//起始值
	private int endNumber;//结束值
	private String prefix;//前缀
	private String markExt;//是否外用标示
	private String currentNumber;//当前编号
	
	private Number number;//号码对象
	
	private NumberCache numberCache;//号码存储对象

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumberScope() {
		return numberScope;
	}

	public void setNumberScope(String numberScope) {
		this.numberScope = numberScope;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSubobject() {
		return subobject;
	}

	public void setSubobject(String subobject) {
		this.subobject = subobject;
	}

	

	public int getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(int startNumber) {
		this.startNumber = startNumber;
	}

	public int getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(int endNumber) {
		this.endNumber = endNumber;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getMarkExt() {
		return markExt;
	}

	public void setMarkExt(String markExt) {
		this.markExt = markExt;
	}
	
	@ManyToOne
	@JoinColumn(name="numbercode")
	public Number getNumber() {
		return number;
	}

	public void setNumber(Number number) {
		this.number = number;
	}
	
	@OneToOne(mappedBy="numberDetail")  
	public NumberCache getNumberCache() {
		return numberCache;
	}

	public void setNumberCache(NumberCache numberCache) {
		this.numberCache = numberCache;
	}

	public String getCurrentNumber() {
		return currentNumber;
	}

	public void setCurrentNumber(String currentNumber) {
		this.currentNumber = currentNumber;
	}

	
    public boolean equals(NumberDetail obj){  
   
    	if(obj != null){  
    	 	String oldNumber = this.getNumberScope();
    		String newNumber = obj.getNumberScope();
    		
    		if(year != null){
    			oldNumber += this.getYear();
    			newNumber += obj.getYear();
    		}
    		if(subobject !=null){
    			oldNumber += this.getSubobject();
    			newNumber += obj.getSubobject();
    		}
    		if(oldNumber.equals(newNumber)){
    			return true;
    		}
        }  
    	return false;
    } 
    
    @Override  
    public int hashCode(){  
        return subobject.hashCode();  
    }  
	
}
