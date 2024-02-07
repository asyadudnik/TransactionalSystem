package com.optum.payment.system.config;

import jakarta.persistence.EntityManagerFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static com.optum.payment.system.global.InstallConstants.*;

@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(JpaProperties.class)
public class JpaConfigurationConfig {


    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(JDBC_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername( USER );
        dataSource.setPassword( PASS );
        dataSource.setConnectionProperties("useUnicode=true;characterEncoding=utf8;characterSetResults=UTF-8;");
        return dataSource;
    }


    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }



    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(true);
        jpaVendorAdapter.setDatabasePlatform(MySQLDialect.class.getName());
        jpaVendorAdapter.setPrepareConnection(true);
        return jpaVendorAdapter;
    }


    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean objEntityManager = new LocalContainerEntityManagerFactoryBean();
        objEntityManager.setDataSource(dataSource());
        objEntityManager.setJpaVendorAdapter(jpaVendorAdapter());
        objEntityManager.setPackagesToScan("com.optum.payment.system.entities");
        objEntityManager.setJpaProperties(getHibernateProperties());
        return(objEntityManager);
    }
    public Properties getHibernateProperties()
    {
        Properties objHibernateProperties = new Properties();

        objHibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        objHibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        objHibernateProperties.put("hibernate.connection.CharSet", "utf-8");
        objHibernateProperties.put("hibernate.connection.useUnicode", true);
        objHibernateProperties.put("hibernate.connection.characterEncoding", "utf-8");
        return(objHibernateProperties);
    }
}