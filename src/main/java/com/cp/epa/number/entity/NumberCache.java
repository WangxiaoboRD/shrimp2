package com.cp.epa.number.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cp.epa.base.BaseEntity;

@Entity
@Table(name="base_numbercache")
public class NumberCache extends BaseEntity{
	
	private static final long serialVersionUID = -6634684043913548199L;
	private int id;
	private int step;//步长
	private int length;//号码长度
	private int cacheNumber;//缓存个数
	private int currentNumber;//当前值
	private int maxCacheNumber;//最大当前缓存值
	//private int start;//起始值
	//private int end;//结束值
	private String prefix;//前缀
	//private String warnNumber;//警告数
	private NumberDetail numberDetail;
	
	private int cacheNumDyn;//动态变化值，起始值与缓存个数一致，随着号码的使用慢慢减少，该字段与数据库无关联，对于数据库透明，因此字段值得变化不会引起数据库变动
	
	private int currentNumDyn;//动态当前值，在缓存中随时记录当前使用的值，该字段与数据库无关联
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCacheNumber() {
		return cacheNumber;
	}
	public void setCacheNumber(int cacheNumber) {
		this.cacheNumber = cacheNumber;
	}
	public int getCurrentNumber() {
		return currentNumber;
	}
	public void setCurrentNumber(int currentNumber) {
		this.currentNumber = currentNumber;
	}
	public int getMaxCacheNumber() {
		return maxCacheNumber;
	}
	public void setMaxCacheNumber(int maxCacheNumber) {
		this.maxCacheNumber = maxCacheNumber;
	}
	
	@OneToOne 
	@JoinColumn(name="numberDetail")  
	public NumberDetail getNumberDetail() {
		return numberDetail;
	}
	public void setNumberDetail(NumberDetail numberDetail) {
		this.numberDetail = numberDetail;
	}
	
	//判断还有没有缓存个数
	public boolean hasNext(){
		return cacheNumDyn == 0;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	@Transient
	public int getCacheNumDyn() {
		return cacheNumDyn;
	}
	public void setCacheNumDyn(int cacheNumDyn) {
		this.cacheNumDyn = cacheNumDyn;
	}
	@Transient
	public int getCurrentNumDyn() {
		return currentNumDyn;
	}
	public void setCurrentNumDyn(int currentNumDyn) {
		this.currentNumDyn = currentNumDyn;
	}
}
