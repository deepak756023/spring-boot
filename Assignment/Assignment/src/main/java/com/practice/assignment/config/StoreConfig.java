package com.practice.assignment.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.practice.Assignment.repo.store", entityManagerFactoryRef = "storeEntityManagerFactory", transactionManagerRef = "storeTransactionManager")
public class StoreConfig {

    private final Environment env;

    public StoreConfig(Environment env) {
        this.env = env;
    }

    @Bean(name = "storeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean storeEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(storeDataSource());
        em.setPackagesToScan("com.practice.Assignment.entities.store");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect",
                env.getProperty("spring.jpa.properties.hibernate.dialect"));

        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public DataSource storeDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Optional.ofNullable(env.getProperty("second.datasource.driver-class-name"))
                .filter(name -> !name.isEmpty())
                .ifPresentOrElse(
                        dataSource::setDriverClassName,
                        () -> { throw new IllegalStateException("Second datasource driver class name property is not configured"); }
                );
        dataSource.setUrl(env.getProperty("second.datasource.jdbc-url"));
        dataSource.setUsername(env.getProperty("second.datasource.username"));
        dataSource.setPassword(env.getProperty("second.datasource.password"));
        return dataSource;
    }

    @Bean(name = "storeTransactionManager")
    public PlatformTransactionManager storeTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(storeEntityManagerFactory().getObject());
        return transactionManager;
    }
}
