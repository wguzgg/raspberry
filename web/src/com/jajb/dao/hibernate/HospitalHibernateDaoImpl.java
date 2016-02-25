package com.jajb.dao.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jajb.dao.HospitalDao;
import com.jajb.data.entity.HospitalEntity;

@Repository("hospitalDao")
public class HospitalHibernateDaoImpl extends AbstractHibernateDao<HospitalEntity> implements HospitalDao {

	@Override
	public Class<HospitalEntity> getPersistantType() {
		return HospitalEntity.class;
	}

	@Override
	protected void merge(HospitalEntity entity, HospitalEntity obj) {
		if (obj.getName() != null) {
			entity.setName(obj.getName());
		}
		if (obj.getAddress() != null) {
			entity.setAddress(obj.getAddress());
		}
		if (obj.getAlias() != null) {
			entity.setAlias(obj.getAlias());
		}
	}

	@Override
	public Criterion searchHospitals(String name, String alias,
			String address) {
		Disjunction or = Restrictions.disjunction();
		if (name != null && name.length() > 0) {
			or.add(Restrictions.ilike("name", "%" + name + "%"));
		}
		if (alias != null && alias.length() > 0) {
			or.add(Restrictions.ilike("alias", "%" + alias + "%"));
		}
		if (address != null && address.length() > 0) {
			or.add(Restrictions.ilike("address", "%" + address + "%"));
		}
		return or;
	}
}
