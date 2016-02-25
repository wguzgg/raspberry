package com.jajb.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jajb.dao.IDao;
import com.jajb.data.entity.IEntity;
import com.jajb.service.IService;
import com.jajb.util.ServiceManager;
import com.jajb.util.spring.SpringFactory;

@Service
@Transactional
public abstract class AbstractService<T extends IEntity, E extends IDao<T>> implements IService<T> {

	@Autowired
	protected ServiceManager serviceManager;
	protected E dao;

	@PostConstruct
	public void setup() {
		E dao = SpringFactory.getInstance().getBean(getDaoClass());
		setDao(dao);
		String entityName = dao.getPersistantType().getSimpleName();
		Table table = dao.getPersistantType().getAnnotation(Table.class);
		if (table != null) {
			entityName = table.name();
		}
		Repository repository = dao.getClass().getAnnotation(Repository.class);
		String daoName = repository.value();
		Service service = this.getClass().getAnnotation(Service.class);
		String serviceName = service.value();
		serviceManager.setup(entityName, serviceName, daoName);
	}
	
	public void setDao(E dao) {
		this.dao = dao;
	}
	
	public T save(T entity) {
		return dao.save(entity);
	}
	
	public List<T> findAll() {
		return dao.findAll();
	}
	
	public T getById(Long id) {
		return dao.getById(id);
	}
	
	public void deleteById(Long id) {
		dao.deleteById(id);
	}
	
	public int deleteAll() {
		return dao.deleteAll();
	}
	
}
