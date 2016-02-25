package com.wguzgg.raspberry.dao;

import com.wguzgg.raspberry.data.entity.IEntity;

public interface IHistoryOperator<T extends IEntity> {

    IEntity buildHistory(T entity, T previousEntity);
}
