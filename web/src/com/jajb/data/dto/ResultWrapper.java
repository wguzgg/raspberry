package com.jajb.data.dto;

import java.util.List;

public class ResultWrapper implements IDTO {

	private static final long serialVersionUID = 1L;

	protected List<?> result;
	protected Long count;
	
	public List<?> getResult() {
		return result;
	}
	public void setResult(List<?> result) {
		this.result = result;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	
}
