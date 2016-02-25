package com.wguzgg.raspberry.util.spring;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate3.AbstractSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.wguzgg.raspberry.util.Constant;
import com.wguzgg.raspberry.util.GlobalConfigure;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    protected GlobalConfigure globalConfig;

    @Bean
    public PlatformTransactionManager transactionManager() {
        HibernateTransactionManager tm = new HibernateTransactionManager(sessionFactory());
        return tm;
    }

    @Bean
    public AbstractSessionFactoryBean sessionFactoryBean(){
        BaseLocalSessionFactoryBean sessionFactoryBean = new BaseLocalSessionFactoryBean();
        return sessionFactoryBean;
    }

    @Bean
    public Properties hibernateProperties() {
        Properties properties = new Properties();
        Map<String, String> hibernateConfigs = globalConfig
                .getConfigs(Constant.GLOBAL_CATEGORY.category_hibernate.name());
        for (Entry<String, String> entry : hibernateConfigs.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(globalConfig.getString(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.connection.driver_class"));
        dataSource.setUrl(globalConfig.getString(Constant.GLOBAL_CATEGORY.category_hibernate.name(), "hibernate.connection.url"));
        dataSource.setConnectionProperties(hibernateProperties());
        return dataSource;
    }
    
    public SessionFactory sessionFactory() {
        return sessionFactoryBean().getObject();
    }

}
