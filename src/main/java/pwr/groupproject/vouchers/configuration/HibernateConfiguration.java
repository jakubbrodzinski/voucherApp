package pwr.groupproject.vouchers.configuration;


import org.hibernate.cfg.beanvalidation.BeanValidationIntegrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.ValidationMode;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableAspectJAutoProxy
@PropertySource(value = "classpath:app_properties/jdbc.properties")
@EnableTransactionManagement
public class HibernateConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"pwr.groupproject.vouchers.bean"});

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        em.getJpaPropertyMap().put(BeanValidationIntegrator.MODE_PROPERTY, ValidationMode.NONE);

        return em;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.ddl-auto"));
        hibernateProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
//        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", environment.getProperty("hibernate.cache.use_second_level_cache"));
//        hibernateProperties.setProperty("hibernate.cache.use_query_cache", environment.getProperty("hibernate.cache.use_query_cache"));
        hibernateProperties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        return hibernateProperties;
    }
}
