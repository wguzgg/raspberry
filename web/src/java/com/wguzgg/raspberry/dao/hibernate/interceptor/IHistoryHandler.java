package com.wguzgg.raspberry.dao.hibernate.interceptor;

import com.wguzgg.raspberry.data.entity.IEntity;


public interface IHistoryHandler {

    void recordHistory(IEntity entity);
}
