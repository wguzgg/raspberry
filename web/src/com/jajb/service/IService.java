package com.jajb.service;

import java.util.List;

import com.jajb.data.entity.IEntity;

public interface IService<T extends IEntity> {

	T save(T t);
	List<T> findAll();
	T getById(Long id);
	void deleteById(Long id);
	int deleteAll();
	Class<?> getDaoClass();
}
