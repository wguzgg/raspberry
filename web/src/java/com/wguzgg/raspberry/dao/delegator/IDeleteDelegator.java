package com.wguzgg.raspberry.dao.delegator;

import org.hibernate.criterion.Criterion;

import com.wguzgg.raspberry.data.entity.IEntity;

public interface IDeleteDelegator {

    void delegate(IEntity entity);
    void delegate(IEntity entity, boolean isDeleted);
    Boolean isDeletage(IEntity entity);
    Criterion getDelegate(Class<?> entity, boolean isDeleted);
}
