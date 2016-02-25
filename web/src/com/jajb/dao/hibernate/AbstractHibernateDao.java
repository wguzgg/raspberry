package com.jajb.dao.hibernate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jajb.annotation.DeleteDelegator;
import com.jajb.dao.IDao;
import com.jajb.dao.delegator.IDeleteDelegator;
import com.jajb.data.Pagination;
import com.jajb.data.entity.HospitalEntity;
import com.jajb.data.entity.IEntity;
import com.jajb.data.entity.IIDEntity;
import com.jajb.util.GlobalConfigure;
import com.jajb.util.spring.SpringFactory;

@Repository
public abstract class AbstractHibernateDao<T extends IEntity> implements IDao<T> {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    protected SessionFactory sessionFactory;
    @Autowired
    private GlobalConfigure globalConfig;

    protected Session getSession() {
    	return sessionFactory.getCurrentSession();
    }
    
    protected abstract void merge(T entity, T obj);
    
    @SuppressWarnings("unchecked")
	public List<T> search(Criterion crit, Pagination page) {
		Session session = this.getSession();
		Criteria root = session.createCriteria(HospitalEntity.class);
		root.add(crit);
		if (isDeleteDelegated()) {
			IDeleteDelegator delegator = getDeleteDelegator();
			root.add(delegator.getDelegate(this.getPersistantType(), false));
		}
		if (page != null) {
			if (page.getStart() != null) {
				root.setFirstResult(page.getStart());
			}
			if (page.getLimit() != null) {
				root.setMaxResults(page.getLimit());
			}
		}
		if (page.getOrder() != null && page.getSort() != null) {
			if (page.getSort().equalsIgnoreCase("asc")) {
				root.addOrder(Order.asc(page.getOrder()));
			}
			else if (page.getSort().equalsIgnoreCase("desc")) {
				root.addOrder(Order.desc(page.getOrder()));
			}
		}
		return root.list();
    }
    
    public Long count(Criterion crit) {
		Session session = this.getSession();
		Criteria root = session.createCriteria(HospitalEntity.class);
		root.add(crit);
		if (isDeleteDelegated()) {
			IDeleteDelegator delegator = getDeleteDelegator();
			root.add(delegator.getDelegate(this.getPersistantType(), false));
		}
    	return (Long) root.setProjection(Projections.rowCount()).uniqueResult();
    }
    
	@Override
	public T save(T obj) {
		Session session = getSession();
		T entity = obj;
		if (obj instanceof IIDEntity) {
			if (((IIDEntity) obj).getId() != null) {
				entity = this.getById(((IIDEntity) obj).getId());
				merge(entity, obj);
			}
		}
		if (isDeleteDelegated()) {
			IDeleteDelegator delegator = getDeleteDelegator();
			if (delegator.isDeletage(entity) == null) {
				delegator.delegate(entity, false);
			}
		}

        session.saveOrUpdate(entity);
        return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
        if (params != null) {
            for (Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof Collection) {
                    query.setParameterList(entry.getKey(),
                            (Collection<?>) entry.getValue());
                } else if (entry.getValue().getClass().isArray()) {
                    query.setParameterList(entry.getKey(),
                            (Object[]) entry.getValue());
                } else {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
        }
        return query.list();
	}

	@Override
	public List<T> findAll() {
		Session session = getSession();
        Criteria crit = session.createCriteria(this.getPersistantType());
        @SuppressWarnings("unchecked")
		List<T> result = crit.list();
        return result;
	}

	@Override
	public T getById(Long id) {
		Session session = getSession();
        @SuppressWarnings("unchecked")
		T t = (T) session.get(this.getPersistantType(), id);
        return t;
	}

	@Override
	public void deleteById(Long id) {
        if (id == null) {
        	log.debug("can't delete entity by id is null for "
                    + this.getPersistantType().getName());
            return;
        }
        T entity = this.getById(id);
        if (entity != null) {
            delete(entity);
        } else {
            log.info("can't find data to delete by id: " + id);
        }
	}

	@Override
	public void delete(T entity) {
		if (entity == null) {
            log.debug("can't delete null entity for "
                    + this.getPersistantType().getName());
            return;
        }
        if (isDeleteDelegated()) {
            deleteByDelegator(entity);
        } else {
            Session session = getSession();
            session.delete(entity);
        }
	}

	protected void deleteByDelegator(T entity) {
		IDeleteDelegator delegator = getDeleteDelegator();
        delegator.delegate(entity);
        this.save(entity);
    }

	@Override
	public int deleteAll() {
		List<T> list = findAll();
        for (T t : list) {
            delete(t);
        }
        return list.size();
	}

	protected boolean isDeleteDelegated() {
		return this.getPersistantType().isAnnotationPresent(DeleteDelegator.class);
	}
	
	protected IDeleteDelegator getDeleteDelegator() {
		if (isDeleteDelegated()) {
	        String delegatorBeanName = this.getPersistantType()
	                .getAnnotation(DeleteDelegator.class).value();
	        if (delegatorBeanName != null && delegatorBeanName.length() > 0) {
                return SpringFactory.getInstance().getBean(delegatorBeanName);
	        }
	        else {
	        	return null;
	        }
		}
		else {
			return null;
		}
	}
}
