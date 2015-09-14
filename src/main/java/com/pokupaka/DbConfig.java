package com.pokupaka;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * By gekoreed on 9/12/15.
 */
@Configuration
@PropertySource("file:conf/app.properties")
public class DbConfig {

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String password;


    @Bean
    DSLContext getDslContext(){
        return DSL.using(getDriverManagerDataSource(), SQLDialect.MYSQL);
    }


    @Bean
    DataSource getDriverManagerDataSource() {
        return new DriverManagerDataSource(url, user, password);
    }
}
