package com.jajb.service;

import com.jajb.data.dto.HospitalSearchWrapper;
import com.jajb.data.entity.HospitalEntity;

public interface HospitalService extends IService<HospitalEntity>, ISearch<HospitalEntity, HospitalSearchWrapper> {

}
