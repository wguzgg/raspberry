package com.jajb.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jajb.dao.IDao;
import com.jajb.data.entity.IEntity;
import com.jajb.service.IService;
import com.jajb.util.spring.SpringFactory;

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
