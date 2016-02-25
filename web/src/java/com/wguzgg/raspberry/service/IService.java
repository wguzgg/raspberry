package com.wguzgg.raspberry.service;

import java.util.List;

import com.wguzgg.raspberry.data.entity.IEntity;

public interface IService<T extends IEntity> {

	T save(T t);
	List<T> findAll();
	T getById(Long id);
	void deleteById(Long id);
	int deleteAll();
	Class<?> getDaoClass();
}
