package com.jajb.data.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.jajb.annotation.DeleteDelegator;

@Entity
@Table(name = "user")
@org.hibernate.annotations.DynamicUpdate
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@DeleteDelegator(value = "deleteWithSignDelegator", columnName="isDeleted")
public class UserEntity extends AbstractIdentityIDEntity {

	private static final long serialVersionUID = 1L;
	@Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name = "display_name")
    private String displayName;
    
    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
