package com.wguzgg.raspberry.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wguzgg.raspberry.dao.UserDao;
import com.wguzgg.raspberry.dao.hibernate.UserHibernateDaoImpl;
import com.wguzgg.raspberry.data.Pagination;
import com.wguzgg.raspberry.data.entity.UserEntity;
import com.wguzgg.raspberry.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl extends AbstractService<UserEntity, UserDao> implements UserService {

	public Class<?> getDaoClass() {
		return UserHibernateDaoImpl.class;
	}

	@Override
	public List<UserEntity> search(UserEntity wrapper, Pagination page) {
		return dao.search(dao.searchUsers(wrapper.getName(), wrapper.getDisplayName()), page);
	}

	@Override
	public Long count(UserEntity wrapper) {
		return dao.count(dao.searchUsers(wrapper.getName(), wrapper.getDisplayName()));
	}
}
