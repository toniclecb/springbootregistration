package com.toniclecb.springbootregistration.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

// Tags the class as a source of bean definitions for the application context.
@Configuration
// Annotation to enable JPA repositories. Will scan the package of the annotated configuration class for Spring Data repositories by default.
@EnableJpaRepositories(entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTransactionManager", basePackages = {
        "com.toniclecb.springbootregistration.domain.mysql.repo" })
public class MySqlConfig {
    // @Bean - specify that it returns a bean to be managed by Spring context,
    // we named it because we are defining two DataSource and to recover the correct we need explicitly named it.
    @Bean(name = "mysqlDataSource")
    // ConfigurationProperties will load the file properties based on this prefix. The method build() will load only the properties he needs to create datasource
    @ConfigurationProperties(prefix = "second.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            // @Qualifier works along with @Bean (and name), is used to avoid confusion which occurs when you create more than one bean of the same type
            @Qualifier("mysqlDataSource") DataSource dataSource) {
                LocalContainerEntityManagerFactoryBean em =  builder
                .dataSource(dataSource)
                .packages("com.toniclecb.springbootregistration.domain.mysql.domain")
                .persistenceUnit("mysql")
                .build();

        Properties properties = new Properties();
        // A hibernate dialect gives information to the framework of how to convert hibernate queries(HQL) into native SQL queries.
        // we have two different databases then we need configure here the correct dialect to this "LocalContainerEntityManagerFactoryBean"
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        // This property takes an enum that controls the schema generation in a more controlled way
        // update: Updates the schema only if necessary. For example, If a new field was added in an entity, then it will simply alter the table for a new column without destroying the data.
        // This will help us, because in this moment we don't need to worry about creating or editing the tables in the database, just worry about our java classes.
        // source: https://springhow.com/spring-boot-database-initialization/
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        em.setJpaProperties(properties);
        return em;
    }

    @Bean(name = "mysqlTransactionManager")
    public PlatformTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {
        return new JpaTransactionManager(mysqlEntityManagerFactory);
    }
}
