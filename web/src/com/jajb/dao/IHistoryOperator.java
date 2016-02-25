package com.jajb.dao;

import com.jajb.data.entity.IEntity;

public interface IHistoryOperator<T extends IEntity> {

    IEntity buildHistory(T entity, T previousEntity);
}
