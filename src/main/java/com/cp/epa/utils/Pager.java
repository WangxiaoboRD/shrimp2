package com.cp.epa.utils;

import java.util.Collections;
import java.util.List;
import com.cp.epa.base.BaseEntity;



public class Pager<T extends BaseEntity> {
	//正排序
	public static final String ASC = "asc";
	//降序
	public static final String DESC = "desc";
	//默认每页显示数
	private  static final int PAGECOUNT = 10;
	//默认的排序字段
	private static final String CREATEDATE="createDate";
	//实体类对象
	private T entity;
	
	//当前页数
	private int pageNo;  
	//每页显示条数
	private int pageSize ;
	//总条数
	private int totalCount;
	//总页数
	private int totalPage;
	//下一页
	private int nextPage;
	//上一页
	private int upPage;
	//排序字段
	private String sortName;
	//排序方式
	private String sortorder;
	// 返回结果
	protected List<T> result = Collections.emptyList();

	public String getSortorder() {
		if(sortorder == null || "".equals(sortorder))
			sortorder=DESC;
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public int getPageNo() {
		if (pageNo < 1) {
			this.pageNo = 1;
		}
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		if (pageSize < 1)
			this.pageSize = PAGECOUNT;
		return pageSize;
	}
	
	//设置每页显示条数默认为10条
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	//获取总条数
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	//获取总页数
	public int getTotalPage() {
		if (totalCount < 0)
			return -1;
		return (int)Math.ceil((double)totalCount/pageSize);
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	} 
	
	//获取下一页
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	//获取上一页
	public int getUpPage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}
	
	public void setUpPage(int upPage) {
		this.upPage = upPage;
	}
	//有没有下一页
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPage());
	}
	//有没有上一页
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}
	public String getSortName() {
		if(sortName == null || "".equals(sortName))
			sortName = CREATEDATE;
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
}