package com.jajb.data.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractIdentityIDEntity implements IIDEntity, IDeleteSignAble {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    protected Long id;
	
	@Column(name = "is_deleted", columnDefinition="tinyint(1) default 0")
	protected Boolean isDeleted;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Boolean getIsDeleted() {
    	return isDeleted;
    }
    
    public void setIsDeleted(Boolean isDeleted) {
    	this.isDeleted = isDeleted;
    }
    
    protected Date formatDate(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
        try {
            return df.parse(df.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
