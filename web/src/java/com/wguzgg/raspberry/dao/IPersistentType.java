package com.wguzgg.raspberry.dao;

import com.wguzgg.raspberry.data.entity.IEntity;

public interface IPersistentType<T extends IEntity> {

    Class<T> getPersistantType();
}
