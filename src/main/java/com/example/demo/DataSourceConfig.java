package com.example.demo;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DataSourceConfig {

    private final Dotenv env = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
        HikariDataSource ds = new HikariDataSource();
        String url = env.get("PRIMARY_DB_URL", env.get("DB_URL"));
        String user = env.get("PRIMARY_DB_USER", env.get("DB_USER"));
        String pass = env.get("PRIMARY_DB_PASS", env.get("DB_PASS"));

        ds.setJdbcUrl(url);
        ds.setUsername(user);
        ds.setPassword(pass);
        ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return ds;
    }

    // --- NOVO BEAN PARA O SQL SERVER ---
    @Primary
    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("primaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource() {
        HikariDataSource ds = new HikariDataSource();
        String url = env.get("POSTGRES_DB_URL", "jdbc:postgresql://db:5432/isoja");
        // String url = env.get("POSTGRES_DB_URL", "jdbc:postgresql://localhost:5432/isoja");
        String user = env.get("POSTGRES_USER", "isoja");
        String pass = env.get("POSTGRES_PASS", "isoja");

        ds.setJdbcUrl(url);
        ds.setUsername(user);
        ds.setPassword(pass);
        ds.setDriverClassName("org.postgresql.Driver");
        return ds;
    }

    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryNamedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate secondaryNamedParameterJdbcTemplate(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(
            @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        return new DataSourceTransactionManager(secondaryDataSource);
    }
}