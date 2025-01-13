package com.pefonseca.library.api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    @Bean
    public DataSource hikariDataSource() {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        // Liberando quantidade máxima de conexão do pool.
        config.setMaximumPoolSize(10);
        // Liberando quantidade inicial de conexão do pool.
        config.setMinimumIdle(1);
        // Nome do pool
        config.setPoolName("library-db-pool");
        // 600 mil ms (que vai durar 10 minutos a conexão)
        config.setMaxLifetime(600000);
        // Timeout para conseguir se conectar.
        config.setConnectionTimeout(100000);
        // Query teste para validar funcionamento do banco.
        config.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(config);
    }

}
