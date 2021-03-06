package com.wguzgg.raspberry.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wguzgg.raspberry.dao.HospitalDao;
import com.wguzgg.raspberry.dao.hibernate.HospitalHibernateDaoImpl;
import com.wguzgg.raspberry.data.Pagination;
import com.wguzgg.raspberry.data.dto.HospitalSearchWrapper;
import com.wguzgg.raspberry.data.entity.HospitalEntity;
import com.wguzgg.raspberry.service.HospitalService;

@Service("hospitalService")
@Transactional
public class HospitalServiceImpl extends AbstractService<HospitalEntity, HospitalDao> implements HospitalService {

	public Class<?> getDaoClass() {
		return HospitalHibernateDaoImpl.class;
	}

	@Override
	public List<HospitalEntity> search(HospitalSearchWrapper wrapper, Pagination page) {
		return dao.search(dao.searchHospitals(wrapper.getName(), wrapper.getAlias(), wrapper.getAddress()), page);
	}

	@Override
	public Long count(HospitalSearchWrapper wrapper) {
		return dao.count(dao.searchHospitals(wrapper.getName(), wrapper.getAlias(), wrapper.getAddress()));
	}

}
