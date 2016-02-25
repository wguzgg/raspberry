package com.wguzgg.raspberry.service;

import java.util.List;

import com.wguzgg.raspberry.data.Pagination;
import com.wguzgg.raspberry.data.entity.IEntity;

public interface ISearch<T extends IEntity, E extends IEntity> {
	List<T> search(E wrapper, Pagination page);
	Long count(E wrapper);
}
