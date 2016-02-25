package com.jajb.dao;

import com.jajb.data.entity.IEntity;

public interface IPersistentType<T extends IEntity> {

    Class<T> getPersistantType();
}
