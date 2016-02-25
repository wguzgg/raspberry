package com.jajb.dao;

import org.hibernate.criterion.Criterion;

import com.jajb.data.entity.UserEntity;

public interface UserDao extends IDao<UserEntity>{

	Criterion searchUsers(String name, String displayName);
}
