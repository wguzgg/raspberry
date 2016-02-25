package com.jajb.data.dto;

public class HospitalSearchWrapper implements IDTO {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String alias;
	private String address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
