package com.wguzgg.raspberry.service;

import com.wguzgg.raspberry.data.dto.HospitalSearchWrapper;
import com.wguzgg.raspberry.data.entity.HospitalEntity;

public interface HospitalService extends IService<HospitalEntity>, ISearch<HospitalEntity, HospitalSearchWrapper> {

}
