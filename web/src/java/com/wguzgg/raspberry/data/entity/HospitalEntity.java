package com.wguzgg.raspberry.data.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.wguzgg.raspberry.annotation.DeleteDelegator;
import com.wguzgg.raspberry.annotation.SearchWrapper;
import com.wguzgg.raspberry.data.dto.HospitalSearchWrapper;

@Entity
@Table(name = "hospital")
@org.hibernate.annotations.DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DeleteDelegator(value = "deleteWithSignDelegator", columnName="isDeleted")
@SearchWrapper(HospitalSearchWrapper.class)
public class HospitalEntity extends AbstractIdentityIDEntity {

	private static final long serialVersionUID = 1L;
	@Column(name = "name", unique = true, nullable = false)
    private String name;
    private String code;
    private String address;
	@Column(name = "alias", unique = true, nullable = false)
    private String alias;
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
}
