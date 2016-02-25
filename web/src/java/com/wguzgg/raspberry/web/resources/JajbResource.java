package com.wguzgg.raspberry.web.resources;

import java.util.List;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wguzgg.raspberry.annotation.SearchWrapper;
import com.wguzgg.raspberry.data.Pagination;
import com.wguzgg.raspberry.data.dto.ResultWrapper;
import com.wguzgg.raspberry.data.entity.IEntity;
import com.wguzgg.raspberry.data.entity.IIDEntity;
import com.wguzgg.raspberry.service.ISearch;
import com.wguzgg.raspberry.service.IService;
import com.wguzgg.raspberry.util.JsonMapperFactory;
import com.wguzgg.raspberry.util.ServiceManager;
import com.wguzgg.raspberry.util.spring.SpringFactory;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/")
public class JajbResource {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    
    private ServiceManager serviceManager;
    private JsonMapperFactory mapperFactory;

    public JajbResource() {
    	serviceManager = SpringFactory.getInstance().getBean(ServiceManager.class);
    	mapperFactory = SpringFactory.getInstance().getBean(JsonMapperFactory.class);
    }
    
    @GET
	@Path("/echo")
	public Response echo() {
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON_TYPE)
				.entity("JAJB echo").build();
	}
    
    /**
     * count entities
     * @param entityName
     * @param json
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @POST
    @Path("{entityName}s/count")
    public Response count(@PathParam("entityName") String entityName, String json) throws Exception {
    	log.debug("count " + entityName);
    	Class<? extends IEntity> clazz = serviceManager.getEntity(entityName);
    	if (clazz.isAnnotationPresent(SearchWrapper.class)) {
    		SearchWrapper wrapper = clazz.getAnnotation(SearchWrapper.class);
    		clazz = wrapper.value();
    	}
    	IEntity wrapper = mapperFactory.getMapper().readValue(json, clazz);
    	IService service = serviceManager.getService(entityName);
    	if (service instanceof ISearch) {
    		ISearch search = (ISearch) service;
    		Long count = search.count(wrapper);
        	return Response.status(Status.OK).type(MediaType.APPLICATION_JSON_TYPE).entity(count).build();
    	}
    	else {
    		throw new Exception("can't search for " + entityName);
    	}
    }
    
    /**
     * search entities
     * @param entityName
     * @param json
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
    @Path("{entityName}s/search")
    public Response search(@PathParam("entityName") String entityName, @QueryParam("start") Integer start, @QueryParam("limit") Integer limit, 
    		@QueryParam("order") String order, @QueryParam("sort") String sort, String json) throws Exception {
    	log.debug("search " + entityName + ", start: " + start + ", limit: " + limit);
    	Class<? extends IEntity> clazz = serviceManager.getEntity(entityName);
    	if (clazz.isAnnotationPresent(SearchWrapper.class)) {
    		SearchWrapper wrapper = clazz.getAnnotation(SearchWrapper.class);
    		clazz = wrapper.value();
    	}
    	IEntity wrapper = mapperFactory.getMapper().readValue(json, clazz);
    	IService service = serviceManager.getService(entityName);
    	if (service instanceof ISearch) {
    		ISearch search = (ISearch) service;
    		List<?> result = search.search(wrapper, new Pagination(start, limit, order, sort));
    		Long count = search.count(wrapper);
    		ResultWrapper rw = new ResultWrapper();
    		rw.setResult(result);
    		rw.setCount(count);
        	return Response.status(Status.OK).type(MediaType.APPLICATION_JSON_TYPE).entity(rw).build();
    	}
    	else {
    		throw new Exception("can't search for " + entityName);
    	}
    }
    
    /**
     * create a new entity
     * @param entityName
     * @param json
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@POST
    @Path("{entityName}s")
    public Response create(@PathParam("entityName") String entityName, String json) throws Exception {
    	Class clazz = serviceManager.getEntity(entityName);
   		IEntity obj = (IEntity) mapperFactory.getMapper().readValue(json, clazz);
   		IService service = serviceManager.getService(entityName);
   		obj = service.save(obj);
   		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON_TYPE).entity(obj).build();
    }
    
    /**
     * update an entity
     * @param entityName
     * @param id
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @PUT
    @Path("{entityName}s/{id}")
    public Response modify(@PathParam("entityName") String entityName, @PathParam("id") Long id, String json) throws Exception {
    	if (id == null) {
    		throw new Exception("can't update " + entityName + " without id");
    	}
    	Class clazz = serviceManager.getEntity(entityName);
   		IEntity obj = (IEntity) mapperFactory.getMapper().readValue(json, clazz);
   		if (obj instanceof IIDEntity) {
   			Long entityId = ((IIDEntity) obj).getId();
   			if (entityId == null) {
   				((IIDEntity) obj).setId(id);
   			}
   			else if (!entityId.equals(id)) {
   				throw new Exception("can't update " + entityName + " since id is not as same as in the json data");
   			}
   		}
   		else {
   			throw new Exception("can't find id");
   		}
   		IService service = serviceManager.getService(entityName);
   		obj = service.save(obj);
   		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON_TYPE).entity(obj).build();
    }
    
    @SuppressWarnings({ "rawtypes" })
	@DELETE
    @Path("{entityName}s/{id}")
    public Response delete(@PathParam("entityName") String entityName, @PathParam("id") Long id) throws Exception {
    	if (id == null) {
    		throw new Exception("can't delete " + entityName + " without id");
    	}
   		IService service = serviceManager.getService(entityName);
   		service.deleteById(id);
   		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON_TYPE).entity(1).build();
    }
    
    @SuppressWarnings("rawtypes")
	@DELETE
    @Path("{entityName}s")
    public Response delets(@PathParam("entityName") String entityName, @QueryParam("ids") Set<Long> ids) throws Exception {
    	if (ids == null) {
    		throw new Exception("can't delete " + entityName + " without ids");
    	}
   		IService service = serviceManager.getService(entityName);
   		for(Long id: ids) {
   			service.deleteById(id);
   		}
   		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON_TYPE).entity(ids.size()).build();
    }
}
