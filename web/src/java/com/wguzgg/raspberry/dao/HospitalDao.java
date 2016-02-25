package com.wguzgg.raspberry.dao;

import org.hibernate.criterion.Criterion;

import com.wguzgg.raspberry.data.Pagination;
import com.wguzgg.raspberry.data.entity.HospitalEntity;

public interface HospitalDao extends IDao<HospitalEntity> {

	Criterion searchHospitals(String name, String alias, String address);
}
