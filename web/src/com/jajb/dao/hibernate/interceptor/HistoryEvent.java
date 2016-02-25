package com.jajb.dao.hibernate.interceptor;

import org.springframework.context.ApplicationEvent;

import com.jajb.data.entity.IEntity;

public class HistoryEvent extends ApplicationEvent {
    
	private static final long serialVersionUID = 1L;
	private IEntity history;
    
    public HistoryEvent(Object source, IEntity history) {
        super(source);
        this.history = history;
    }

    public IEntity getHistory() {
        return history;
    }

    public void setHistory(IEntity history) {
        this.history = history;
    }

}
