package com.jajb.util.spring;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringFactory implements ApplicationContextAware {

    private static SpringFactory instance;
    private ApplicationContext ctx;
    
    private SpringFactory() {
        
    }
    
    public static void init(List<String> configFiles) {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext();
        acac.scan(configFiles.toArray(new String[0]));
        acac.refresh();
    }
    
    @SuppressWarnings("unchecked")
	public <T> T getBean(String beanName) {
        return (T) ctx.getBean(beanName);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public <T> T getBean(Class clazz) {
        return (T) ctx.getBean(clazz);
    }
    
    public ApplicationContext getCtx() {
        return ctx;
    }

    public void setCtx(AnnotationConfigApplicationContext ctx) {
        this.ctx = ctx;
    }

    public static SpringFactory getInstance() {
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ctx = applicationContext;
        instance = ctx.getBean(SpringFactory.class);
    }
    
}
