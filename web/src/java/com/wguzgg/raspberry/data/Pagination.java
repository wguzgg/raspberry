package com.wguzgg.raspberry.data;

public class Pagination {

	private Integer start;
	private Integer limit;
	private String order;
	private String sort;
	
	public Pagination(Integer start, Integer limit, String order, String sort) {
		this.start = start;
		this.limit = limit;
		this.order = order;
		this.sort = sort;
	}
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
}
