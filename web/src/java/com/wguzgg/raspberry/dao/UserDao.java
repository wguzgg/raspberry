package com.wguzgg.raspberry.dao;

import org.hibernate.criterion.Criterion;

import com.wguzgg.raspberry.data.entity.UserEntity;

public interface UserDao extends IDao<UserEntity>{

	Criterion searchUsers(String name, String displayName);
}
