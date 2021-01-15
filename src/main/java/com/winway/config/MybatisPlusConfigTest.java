package com.winway.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author GuoYongMing
 * @Date 2021/1/14 10:54
 * @Version 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.winway.qrcodeMP.mapper"},sqlSessionTemplateRef = "sqlSessionTemplateTest")//sqlSessionFactoryRef = "sqlSessionFactoryTest"
public class MybatisPlusConfigTest {
    @Autowired
    @Qualifier("test")
    private DataSource testDataSource;

    @Bean(name = "sqlSessionFactoryTest")
    public SqlSessionFactory sqlSessionFactoryTest() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean=new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(testDataSource); // 连接 test 库
        //factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath:mapper.qrcodeMP/*.xml"));

        //手动设置session工厂时，需要手动添加分页插件
        Interceptor[] plugins = new Interceptor[1];
        plugins[0] = paginationInterceptor();
        factoryBean.setPlugins(plugins);

        return factoryBean.getObject();
    }
    @Bean(name = "testTransactionManager")
    public DataSourceTransactionManager testTransactionManager(){

        return new DataSourceTransactionManager(testDataSource);
    }

    @Bean(name = "sqlSessionTemplateTest")
    public SqlSessionTemplate sqlSessionTemplateTest() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryTest()); // 使用上面配置的Factory
        return template;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setLimit(300);
        paginationInterceptor.setDialectType(DbType.MYSQL.getDb());
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

     /*@Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }*/


}
