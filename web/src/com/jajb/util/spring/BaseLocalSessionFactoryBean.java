package com.jajb.util.spring;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

import org.hibernate.HibernateException;
import org.hibernate.Interceptor;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.orm.hibernate3.AbstractSessionFactoryBean;
import org.springframework.util.ClassUtils;

import com.jajb.util.Constant;
import com.jajb.util.GlobalConfigure;

public class BaseLocalSessionFactoryBean extends AbstractSessionFactoryBean {

    private static final String RESOURCE_PATTERN = "/**/*.class";

    private static final TypeFilter[] ENTITY_TYPE_FILTERS = new TypeFilter[] {
            new AnnotationTypeFilter(Entity.class, false),
            new AnnotationTypeFilter(Embeddable.class, false),
            new AnnotationTypeFilter(MappedSuperclass.class, false)};

    private ResourcePatternResolver resourcePatternResolver;
    
    @Autowired
    private GlobalConfigure globalConfig;
    private Configuration configuration;
    private Map<String, String> hibernateConfigs;
    private String[] packagesToScan;

    public BaseLocalSessionFactoryBean() {
        
    }
    
    @PostConstruct
    public void init() {
        String scanPackages = globalConfig.getString(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_SCAN_PACKAGES_FOR_HIBERNATE);
        this.packagesToScan = scanPackages.split(",\\s*");
        
        this.hibernateConfigs = globalConfig.getConfigs(Constant.GLOBAL_CATEGORY.category_hibernate.name());

    }
    
    protected SessionFactory buildSessionFactory() {
        configuration = new Configuration();
        for (Entry<String, String> entry : hibernateConfigs.entrySet()) {
            configuration.setProperty(entry.getKey(), entry.getValue());
        }
        String hibernateInterceptorClass = globalConfig.getString(Constant.GLOBAL_CATEGORY.category_default.name(), Constant.GLOBAL_DEFAULT_KEY_ENV_HIBERNATE_INTERCEPTOR_CLASS);
        if (hibernateInterceptorClass != null && hibernateInterceptorClass.length() > 0) {
            try {
                logger.debug("set interceptor for hibernate " + hibernateInterceptorClass);
                configuration.setInterceptor((Interceptor) Class.forName(hibernateInterceptorClass).newInstance());
            }
            catch(Exception e) {
                logger.error("failed to set interceptor for hibernate, class: " + hibernateInterceptorClass, e);
            }
        }

        resourcePatternResolver = new PathMatchingResourcePatternResolver();
        scanPackages(packagesToScan);
        
        ServiceRegistryBuilder srb = new ServiceRegistryBuilder();
        ServiceRegistry serviceRegistry = srb
                .applySettings(configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void scanPackages(String... packagesToScan) throws HibernateException {
        try {
            for (String pkg : packagesToScan) {
                String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                        ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
                Resource[] resources = this.resourcePatternResolver.getResources(pattern);
                MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
                for (Resource resource : resources) {
                    if (resource.isReadable()) {
                        MetadataReader reader = readerFactory.getMetadataReader(resource);
                        String className = reader.getClassMetadata().getClassName();
                        if (matchesFilter(reader, readerFactory)) {
                            addAnnotatedClasses(this.resourcePatternResolver.getClassLoader().loadClass(className));
                        }
                    }
                }
            }
        }
        catch (IOException ex) {
            throw new MappingException("Failed to scan classpath for unlisted classes", ex);
        }
        catch (ClassNotFoundException ex) {
            throw new MappingException("Failed to load annotated classes from classpath", ex);
        }
    }
    
    public void addAnnotatedClasses(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            configuration.addAnnotatedClass(annotatedClass);
        }
    }
    
    private boolean matchesFilter(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
        for (TypeFilter filter : ENTITY_TYPE_FILTERS) {
            if (filter.match(reader, readerFactory)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, String> getHibernateConfigs() {
        return hibernateConfigs;
    }

    public void setHibernateConfigs(Map<String, String> hibernateConfigs) {
        this.hibernateConfigs = hibernateConfigs;
    }
    
}
