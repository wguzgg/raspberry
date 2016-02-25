package com.wguzgg.raspberry.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.wguzgg.raspberry.dao.IDao;
import com.wguzgg.raspberry.data.entity.IEntity;
import com.wguzgg.raspberry.service.IService;
import com.wguzgg.raspberry.util.spring.SpringFactory;

@Component
public class ServiceManager {

	private Map<String, String> services = new HashMap<String, String>();
	private Map<String, String> daos = new HashMap<String, String>();
	
	public void setup(String entityName, String serviceName, String daoName) {
		services.put(entityName, serviceName);
		daos.put(entityName, daoName);
	}
	
	public Class<? extends IEntity> getEntity(String entityName) {
		IDao dao = getDao(entityName);
		return dao.getPersistantType();
	}
	
	public IService<? extends IEntity> getService(String entityName) {
		return SpringFactory.getInstance().getBean(services.get(entityName));
	}
	
	public IDao<? extends IEntity> getDao(String entityName) {
		return SpringFactory.getInstance().getBean(daos.get(entityName));
	}
	
}
