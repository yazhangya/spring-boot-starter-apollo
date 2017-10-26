package com.zy.springboot.starter.apollo;

import com.zy.springboot.starter.apollo.annotation.ApolloConfigChangeOnKeyProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 *
 * 参考 ApolloConfigRegistrar 这个类进行修改
 * 1. 修改命名空间从application.properties中读取
 * 2. 修改app.id从application.properties中读取，如果没有默认读取server.name
 *
 * @author zhangya
 * @date 2017/10/19
 */
@Configuration
@ComponentScan
public class ApolloStarterConfig {

    @Bean
    @ConditionalOnMissingBean(PropertySourcesPlaceholderConfigurer.class)
    public PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ApolloConfigChangeOnKeyProcessor createApolloAnnotationProcessor() {
        return new ApolloConfigChangeOnKeyProcessor();
    }

}


