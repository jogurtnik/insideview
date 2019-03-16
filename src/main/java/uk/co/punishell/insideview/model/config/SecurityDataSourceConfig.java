package uk.co.punishell.insideview.model.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Slf4j
@Configuration
@PropertySource("classpath:datasources.properties")
public class SecurityDataSourceConfig {

    @Autowired
    Environment env;

    @Bean(name = "customDataSourceSecurity")
    @ConfigurationProperties(prefix="datasource.security")
    public DataSourceProperties securityDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource securityDataSource(@Qualifier("customDataSourceSecurity") DataSourceProperties dataSourceProperties) {

        // create connection pool
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();

        // set the jdbc driver
        try {
            securityDataSource.setDriverClass(securityDataSourceProperties().getDriverClassName());
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        // set database connection props
        securityDataSource.setJdbcUrl(dataSourceProperties.getUrl());
        securityDataSource.setUser(dataSourceProperties.getUsername());
        securityDataSource.setPassword(dataSourceProperties.getPassword());

        // set connection pool props
        securityDataSource.setInitialPoolSize(Integer.parseInt(env.getProperty("connection.pool.initialPoolSize")));
        securityDataSource.setMinPoolSize(Integer.parseInt(env.getProperty("connection.pool.minPoolSize")));
        securityDataSource.setMaxPoolSize(Integer.parseInt(env.getProperty("connection.pool.maxPoolSize")));
        securityDataSource.setMaxIdleTime(Integer.parseInt(env.getProperty("connection.pool.maxIdleTime")));

        return securityDataSource;
    }
}
