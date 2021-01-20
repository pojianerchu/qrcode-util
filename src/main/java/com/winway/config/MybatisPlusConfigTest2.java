package com.winway.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @Author GuoYongMing
 * @Date 2021/1/14 10:54
 * @Version 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.winway.qrcodeMP2.mapper"},sqlSessionFactoryRef = "sqlSessionFactoryTest2")
public class MybatisPlusConfigTest2 {
    @Autowired
    @Qualifier("test2")
    private DataSource test2DataSource;

    @Bean(name = "sqlSessionFactoryTest2")
    public SqlSessionFactory sqlSessionFactoryTest2() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean=new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(test2DataSource); // 连接 test 库

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/qrcodeMP2/*Mapper.xml"));

        //手动设置session工厂时，需要手动添加分页插件
        Interceptor[] plugins = new Interceptor[1];
        plugins[0] = paginationInterceptorTest2();
        factoryBean.setPlugins(plugins);
        return factoryBean.getObject();
    }

    @Bean(name = "paginationInterceptorTest2")
    public PaginationInterceptor paginationInterceptorTest2() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(300);
        paginationInterceptor.setDialectType(DbType.MYSQL.getDb());
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

}
