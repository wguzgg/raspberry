package com.jajb.dao.hibernate.interceptor;

import org.springframework.stereotype.Component;

import com.jajb.data.entity.IEntity;
import com.jajb.util.spring.SpringFactory;

@Component
public class SpringEventHistoryHandler implements IHistoryHandler {

    @Override
    public void recordHistory(IEntity entity) {
        HistoryEvent historyEvent = new HistoryEvent(this, entity);
        SpringFactory.getInstance().getCtx().publishEvent(historyEvent);
    }

}
