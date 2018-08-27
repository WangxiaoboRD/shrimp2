package com.cp.epa.permission.entity;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cp.epa.annotation.BussEle;
import com.cp.epa.base.BaseEntity;

@BussEle(name = "功能栏目")
@Entity
@Table(name="base_item")
public class Item extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String itemName;
	private Integer itemType;
	private String functionName;
	private String url;
	private String urlParam;
	//private String parentCode;
	private Integer rank;
	private String itemDesc;
	private String status;
	private Integer childrens;
	private Integer parentId; 
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getChildrens() {
		return childrens;
	}
	public void setChildrens(Integer childrens) {
		this.childrens = childrens;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemType() {
		return itemType;
	}
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getUrlParam() {
		return urlParam;
	}
	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}
//	@ManyToOne
//	@NotFound(action = NotFoundAction.IGNORE) 
//    @JoinColumn(name="parentId") 
//	public Item getParentId() {
//		return parentId;
//	}
//	public void setParentId(Item parentId) {
//		this.parentId = parentId;
//	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
}
