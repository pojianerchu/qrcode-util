package com.winway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Author GuoYongMing
 * @Date 2021/1/15 9:53
 * @Version 1.0
 */
@Configuration
public class DataSourceConfig {

    // 数据库1
    // 这里的 bean 名字要和上面自定义的名字一样
    @Bean(name = "test")
    @ConfigurationProperties(prefix = "spring.datasource.test") // application.properteis中对应属性的前缀
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

    // 数据库2
    @Bean(name = "test2")
    @ConfigurationProperties(prefix = "spring.datasource.test2") // application.properteis中对应属性的前缀
    public DataSource test2DataSource() {
        return DataSourceBuilder.create().build();
    }


}
