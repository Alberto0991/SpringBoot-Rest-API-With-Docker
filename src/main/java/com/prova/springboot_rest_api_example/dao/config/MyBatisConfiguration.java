package com.prova.springboot_rest_api_example.dao.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@MapperScan(basePackages = "com.prova.springboot_rest_api_example.dao", sqlSessionFactoryRef = "SESSION_FACTORY")
@ComponentScan(basePackages = {"com.prova.springboot_rest_api_example.dao"})
public class MyBatisConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        return dataSourceBuilder.build();
    }

    @Bean(name = "SESSION_FACTORY")
    @Primary
    public SqlSessionFactory globalSchemaSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
//        factoryBean.setPlugins(new Interceptor[] { new QueryParametersInterceptor() });
        return factoryBean.getObject();
    }
}
