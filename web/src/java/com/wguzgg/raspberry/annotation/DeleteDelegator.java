package com.wguzgg.raspberry.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defined how to delete the entity
 * @author percyw
 *
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
public @interface DeleteDelegator {
    public String value();  //the Delegator's bean name
    public String columnName() default "a";
}
