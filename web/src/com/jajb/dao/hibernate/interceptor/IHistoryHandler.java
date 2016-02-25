package com.jajb.dao.hibernate.interceptor;

import com.jajb.data.entity.IEntity;


public interface IHistoryHandler {

    void recordHistory(IEntity entity);
}
