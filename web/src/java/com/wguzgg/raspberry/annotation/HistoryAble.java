package com.wguzgg.raspberry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wguzgg.raspberry.data.entity.IEntity;

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
public @interface HistoryAble {
    Class<? extends IEntity> value();   //defined the entity to record the change history for current entity
}
