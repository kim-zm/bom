package com.web.boot.domain;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Repository;

@Repository
public class Search {	
	private int page = 1;
	private int size = 10;
	private String searchType;
	@Length(min=2,max=50)
	private String searchValue;	
	private int responseCode;
	private boolean isEnd;
	private int pageableCount;
	private int totalCount;
	private List<Map<String, Object>> list;
	
    public void initPageAndSize(int page, int size){
    	this.page = page;
    	this.size = size;
    }
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	public boolean getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public int getPageableCount() {
		return pageableCount;
	}
	public void setPageableCount(int pageableCount) {
		this.pageableCount = pageableCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
}
