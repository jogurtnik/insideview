package uk.co.punishell.insideview.model.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class TestConfig {

    @Value("${SESAPPDB_DEV_USERNAME}")
    private String username;

    @Value("${SESAPPDB_DEV_PASSWORD}")
    private String password;

    @Bean
    public DataSource getTestDataSource() {

        log.error("USERNAME = " + username);
        log.error("PASSWORD = " + password);

        return DataSourceBuilder.create().build();
    }
}
