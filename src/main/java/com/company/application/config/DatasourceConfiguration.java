package com.company.application.config;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-${spring.profiles.active}.properties")
public class DatasourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public HikariDataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setBaselineOnMigrate(true);
        flyway.setDataSource(dataSource());
        return flyway;
    }

}
