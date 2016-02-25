package com.jajb.service;

import java.util.List;

import com.jajb.data.Pagination;
import com.jajb.data.entity.IEntity;

public interface ISearch<T extends IEntity, E extends IEntity> {
	List<T> search(E wrapper, Pagination page);
	Long count(E wrapper);
}
