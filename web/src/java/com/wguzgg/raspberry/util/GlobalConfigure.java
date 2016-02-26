package com.wguzgg.raspberry.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalConfigure {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private Map<String, Map<String, String>> configs = new HashMap<String, Map<String, String>>();
	private boolean initialized = false;

	/**
	 * initialize global configurations from multiple resources, list from highest to lowest priority
	 * 	environment variables
	 *  jajb.properties defined by JAJB_CONFIG_FILE
	 */
	@PostConstruct
	public void init() {
		if (initialized) {
			return;
		}
		//load from environment
		putByEnv(Constant.GLOBAL_CATEGORY.category_default.name(),
				Constant.GLOBAL_DEFAULT_KEY_ENV_CONFIG_FILE);

		putByEnv(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_DATE_TIME_FORMAT);
		
		putByEnv(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_SCAN_PACKAGES_FOR_HIBERNATE);
		
		putByEnv(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_HIBERNATE_INTERCEPTOR_CLASS);
		putByEnv(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_HIBERNATE_SESSION_INTERCEPTOR_CLASS);
		putByEnv(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_HISTORY_HANDLER_CLASS);
		
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.connection.driver_class");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.connection.url");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.connection.username");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.connection.password");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.show_sql");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.use_sql_comments");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.format_sql");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.hbm2ddl.auto");// create-drop, create, update, validate, none
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "connection.autocommit");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.current_session_context_class");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.acquire_increment");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.min_size");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.max_size");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.timeout");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.max_statements");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.idle_test_period");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.dialect");
		putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.cache.use_second_level_cache");
        putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.cache.use_query_cache");
        putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.cache.region.factory_class");
        putByEnv(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.cache.use_structured_entries");

		//load from nvidia.properties
		if (this.existConfig(Constant.GLOBAL_CATEGORY.category_default.name(),
		        Constant.GLOBAL_DEFAULT_KEY_ENV_CONFIG_FILE)) {
			loadPropertiesFromConfigFile(this.getString(
					Constant.GLOBAL_CATEGORY.category_default.name(),
					Constant.GLOBAL_DEFAULT_KEY_ENV_CONFIG_FILE));
		}
		else {
			log.debug("didn't set config file by env " + Constant.GLOBAL_DEFAULT_KEY_ENV_CONFIG_FILE);
		}
		
		//load from globalConfig.nvidia.com
        
        put(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_SCAN_PACKAGES_FOR_HIBERNATE, "");

        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(),
                "hibernate.connection.driver_class", "org.h2.Driver");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.connection.url",
                "jdbc:h2:~/raspberry");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(),
                "hibernate.connection.username", "root");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(),
                "hibernate.connection.password", "root");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.show_sql", "false");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.use_sql_comments", "false");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.format_sql", "false");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.hbm2ddl.auto",
                "none");// create-drop, create, update, validate, none
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "connection.autocommit",
                "false");
        //put(Constant.GLOBAL_CATEGORY.category_hibernate.name(),
        //        "hibernate.current_session_context_class", "thread");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.current_session_context_class", "org.springframework.orm.hibernate4.SpringSessionContext");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(),
                "hibernate.c3p0.acquire_increment", "1");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.min_size", "1");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.max_size", "12");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.c3p0.timeout", "300");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(),
                "hibernate.c3p0.max_statements", "50");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(),
                "hibernate.c3p0.idle_test_period", "100");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.cache.use_second_level_cache", "false");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.cache.use_query_cache", "false");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
        put(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.cache.use_structured_entries", "true");


        initialized = true;
	}

	public void reload() {
		configs.clear();
		initialized = false;
		init();
	}

	private void loadPropertiesFromConfigFile(String configFilePath) {
		try {
			Properties properties = new Properties();
			File f = new File(configFilePath);
			if (f.exists()) {
				log.debug("found configuration file in local filesystem: " + configFilePath);
				FileReader reader;
				reader = new FileReader(configFilePath);
				properties.load(reader);
				reader.close();
			} else {
				InputStream in = this.getClass().getResourceAsStream("/"
						+ configFilePath);
				if (in == null) {
					throw new IOException("config file not found: "
							+ configFilePath);
				}
				log.debug("found configuration file in classpath: " + configFilePath);
				properties.load(in);
				in.close();
			}
			log.info("load global config from " + configFilePath);
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				if (key.startsWith("category_")) {
				    int i = key.indexOf(".");
				    if (i > 0) {
				        String name = key.substring(0, i);
				        String categoryKey = key.substring(i + 1);
				        put(name, categoryKey, value);
				    }
				}
			}
			log.info("end load global config");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void putByEnv(String categoryKey, String key) {
        String value = getEnv(key);
	    put(categoryKey, key, value);
	}
	
	public void put(String categoryKey, String key, String value) {
		Map<String, String> category = configs.get(categoryKey);
		if (category == null) {
			category = new HashMap<String, String>();
			configs.put(categoryKey, category);
		}
		if (value != null) {
		    if (!category.containsKey(key)) {
		        category.put(key, value);
		        log.info("put global config " + categoryKey + ": " + key + ": " + value);
		    }
		}
	}

    public void set(String categoryKey, String key, String value) {
        Map<String, String> category = configs.get(categoryKey);
        if (category == null) {
            category = new HashMap<String, String>();
            configs.put(categoryKey, category);
        }
        category.put(key, value);
        log.info("reset global config " + categoryKey + ": " + key + ": " + value);
    }

    public boolean existConfig(String category, String key) {
		if (configs.containsKey(category)) {
			String v = configs.get(category).get(key);
			if (v != null) {
				return true;
			}
		}
		return false;
	}

	public Map<String, String> getConfigs(String category) {
		return configs.get(category);
	}

	public Integer getInteger(String category, String key) {
		String v = getString(category, key);
		if (v != null) {
			try {
				return Integer.parseInt(v);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	public String getString(String category, String key) {
		if (configs.containsKey(category)) {
			return configs.get(category).get(key);
		}
		return null;
	}

	public Boolean getBoolean(String category, String key) {
		if (configs.containsKey(category)) {
			String result = configs.get(category).get(key);
			if (result != null && "true".equalsIgnoreCase(result)) {
				return true;
			}
		}
		return false;
	}

	public String getEnv(String key) {
		return getEnv(key, null);
	}

	public String getEnv(String key, String defaultValue) {
        String prefix = Constant.GLOBAL_CONFIGURE_PREFIX;
        if (prefix != null && prefix.length() > 0) {
            key = prefix + "_" + key;
        }
		String env = System.getenv(key);
		if (env == null) {
			env = System.getProperty(key);
		}
		if (env == null) {
			env = defaultValue;
		}
		return env;
	}

}
