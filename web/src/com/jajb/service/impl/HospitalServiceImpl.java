package com.jajb.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jajb.dao.HospitalDao;
import com.jajb.dao.hibernate.HospitalHibernateDaoImpl;
import com.jajb.data.Pagination;
import com.jajb.data.dto.HospitalSearchWrapper;
import com.jajb.data.entity.HospitalEntity;
import com.jajb.service.HospitalService;

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
