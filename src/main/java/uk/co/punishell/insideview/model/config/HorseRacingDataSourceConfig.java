package uk.co.punishell.insideview.model.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "uk.co.punishell.insideview.model.database.repositories",
        entityManagerFactoryRef = "horseRacingEntityManagerFactory",
        transactionManagerRef = "horseRacingTransactionManager"
)
@PropertySource("classpath:datasources.properties")
public class HorseRacingDataSourceConfig {

    @Bean(name = "horseRacingDataSource")
    @ConfigurationProperties(prefix = "datasource.horse-racing")
    public DataSource horseRacingDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "horseRacingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean horseRacingEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("horseRacingDataSource") DataSource dataSource) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", org.hibernate.dialect.H2Dialect.class);


        return
                builder
                        .dataSource(dataSource)
                        .packages("uk.co.punishell.insideview.model.database.entities")
                        .persistenceUnit("horse_racing")
                        .properties(properties)
                        .build();

    }

    @Bean(name = "horseRacingTransactionManager")
    public PlatformTransactionManager horseRacingTransactionManager(
            @Qualifier("horseRacingEntityManagerFactory") EntityManagerFactory horseRacingEntityManagerFactory) {

        return new JpaTransactionManager(horseRacingEntityManagerFactory);
    }
}
