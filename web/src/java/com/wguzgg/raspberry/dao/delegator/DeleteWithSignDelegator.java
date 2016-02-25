package com.wguzgg.raspberry.dao.delegator;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.wguzgg.raspberry.annotation.DeleteDelegator;
import com.wguzgg.raspberry.data.entity.IDeleteSignAble;
import com.wguzgg.raspberry.data.entity.IEntity;

@Component
public class DeleteWithSignDelegator implements IDeleteDelegator {

    @Override
    public void delegate(IEntity entity) {
    	delegate(entity, true);
    }

    @Override
    public void delegate(IEntity entity, boolean isDeleted) {
        if (entity != null && entity instanceof IDeleteSignAble) {
            ((IDeleteSignAble) entity).setIsDeleted(isDeleted);
        }
    }

    @Override
	public Criterion getDelegate(Class<?> clazz, boolean isDeleted) {
		DeleteDelegator delegator = clazz.getAnnotation(DeleteDelegator.class);
		return Restrictions.eq(delegator.columnName(), isDeleted);
	}

	@Override
	public Boolean isDeletage(IEntity entity) {
        if (entity != null && entity instanceof IDeleteSignAble) {
            return ((IDeleteSignAble) entity).getIsDeleted();
        }
        else {
        	return null;
        }
	}

}
