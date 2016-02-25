package com.wguzgg.raspberry.dao.hibernate.interceptor;

import org.springframework.stereotype.Component;

import com.wguzgg.raspberry.data.entity.IEntity;
import com.wguzgg.raspberry.util.spring.SpringFactory;

@Component
public class SpringEventHistoryHandler implements IHistoryHandler {

    @Override
    public void recordHistory(IEntity entity) {
        HistoryEvent historyEvent = new HistoryEvent(this, entity);
        SpringFactory.getInstance().getCtx().publishEvent(historyEvent);
    }

}
