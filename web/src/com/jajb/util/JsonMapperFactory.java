package com.jajb.util;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonMapperFactory {

	@Autowired
	private GlobalConfigure globalConfig;
	private ObjectMapper mapper;
	
	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
        
        String dateFormat = globalConfig.getString(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_DATE_TIME_FORMAT);
        
        SerializationConfig serConfig = mapper.getSerializationConfig();
        serConfig.setDateFormat(new BaseDateFormat(dateFormat));
        DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
        deserializationConfig.setDateFormat(new BaseDateFormat(dateFormat));
        
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
        //mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
        //mapper.setSerializationInclusion(Inclusion.NON_NULL);
        mapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
        
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public ObjectMapper getMapper() {
        return mapper;
    }

}
