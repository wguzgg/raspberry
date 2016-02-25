package com.wguzgg.raspberry.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;

import com.wguzgg.raspberry.data.Pagination;
import com.wguzgg.raspberry.data.entity.IEntity;

public interface IDao<T extends IEntity> extends IPersistentType<T> {
    T save(T entity);
    List<T> search(Criterion crit, Pagination page);
    Long count(Criterion crit);
    List<T> find(String hql, Map<String, Object> params);
    List<T> findAll();
    T getById(Long id);
    void deleteById(Long id);
    void delete(T entity);
    int deleteAll();
}
