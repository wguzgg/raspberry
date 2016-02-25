package com.wguzgg.raspberry.dao.hibernate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.wguzgg.raspberry.dao.UserDao;
import com.wguzgg.raspberry.data.entity.UserEntity;

@Repository("userDao")
public class UserHibernateDaoImpl extends AbstractHibernateDao<UserEntity> implements UserDao {

	@Override
	public Class<UserEntity> getPersistantType() {
		return UserEntity.class;
	}

	@Override
	protected void merge(UserEntity entity, UserEntity obj) {
		if (obj.getName() != null) {
			entity.setName(obj.getName());
		}
		if (obj.getDisplayName() != null) {
			entity.setDisplayName(obj.getDisplayName());
		}
	}

	@Override
	public Criterion searchUsers(String name, String displayName) {
		Disjunction or = Restrictions.disjunction();
		if (name != null && name.length() > 0) {
			or.add(Restrictions.ilike("name", "%" + name + "%"));
		}
		if (displayName != null && displayName.length() > 0) {
			or.add(Restrictions.ilike("displayName", "%" + displayName + "%"));
		}
		return or;
	}

}
