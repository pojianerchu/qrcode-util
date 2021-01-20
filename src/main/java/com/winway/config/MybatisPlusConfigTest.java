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
@MapperScan(basePackages = {"com.winway.qrcodeMP.mapper"},sqlSessionFactoryRef = "sqlSessionFactoryTest")//
public class MybatisPlusConfigTest {
    @Autowired
    @Qualifier("test")
    private DataSource testDataSource;

    @Bean(name = "sqlSessionFactoryTest")
    public SqlSessionFactory sqlSessionFactoryTest() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean=new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(testDataSource); // 连接 test 库
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/qrcodeMP/*Mapper.xml"));

        //手动设置session工厂时，需要手动添加分页插件
        Interceptor[] plugins = new Interceptor[1];
        plugins[0] = paginationInterceptor();
        factoryBean.setPlugins(plugins);

        return factoryBean.getObject();
    }
    @Bean(name = "paginationInterceptorTest")
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(300);
        paginationInterceptor.setDialectType(DbType.MYSQL.getDb());
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
