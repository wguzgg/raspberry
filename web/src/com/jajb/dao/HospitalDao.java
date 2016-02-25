package com.jajb.dao;

import org.hibernate.criterion.Criterion;

import com.jajb.data.Pagination;
import com.jajb.data.entity.HospitalEntity;

public interface HospitalDao extends IDao<HospitalEntity> {

	Criterion searchHospitals(String name, String alias, String address);
}
