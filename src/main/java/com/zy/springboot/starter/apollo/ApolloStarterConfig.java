package com.zy.springboot.starter.apollo;

import com.ctrip.framework.apollo.spring.annotation.ApolloAnnotationProcessor;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.ctrip.framework.apollo.spring.config.PropertySourcesProcessor;
import com.ctrip.framework.apollo.spring.util.BeanRegistrationUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 参考 ApolloConfigRegistrar 这个类进行修改
 * 1. 修改命名空间从application.properties中读取
 * 2. 修改app.id从application.properties中读取，如果没有默认读取server.name
 *
 * @author zhangya
 * @date 2017/10/19
 */

@ConditionalOnClass(EnableApolloConfig.class)
@Configuration
@ConfigurationProperties(prefix = "icx.apollo.space")
public class ApolloStarterConfig implements BeanDefinitionRegistryPostProcessor {

    private final static String Private_Space = "apollo.space.privates";
    private final static String Private_Space_Need_Default = "apollo.space.private.need.default";

    private final static String Common_Space = "apollo.space.commons";

    private final static int Common_Spaces_Order = Ordered.LOWEST_PRECEDENCE;
    private final static int Private_Spaces_Order = 0;

    private final static String Default_Namespace = "application";

    private final static String Server_Name = "spring.application.name";
    private final static String Apollo_App_Id = "app.id";




    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        Environment env = ((DefaultListableBeanFactory) registry).getBean(Environment.class);

        String privates = env.getProperty(Private_Space);
        boolean  needDefault = env.getProperty(Private_Space_Need_Default,Boolean.class,true);
        String commons = env.getProperty(Common_Space);

        String appId = env.getProperty(Apollo_App_Id);
        String serverName = env.getProperty(Server_Name,"default");

        if (StringUtils.isEmpty(appId)) {
            System.setProperty(Apollo_App_Id,serverName);
        }else {
            System.setProperty(Apollo_App_Id, appId);
        }

        List<String> privateList = splitForList(privates);
        List<String> commonList = splitForList(commons);

        if (needDefault && !privateList.contains(Default_Namespace)) {
            privateList.add(0, Default_Namespace);
        }

        PropertySourcesProcessor.addNamespaces(privateList, Private_Spaces_Order);

        if (!CollectionUtils.isEmpty(commonList)) {
            PropertySourcesProcessor.addNamespaces(commonList, Common_Spaces_Order);
        }

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, PropertySourcesPlaceholderConfigurer.class.getName(),
                PropertySourcesPlaceholderConfigurer.class);

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, PropertySourcesProcessor.class.getName(),
                PropertySourcesProcessor.class);

        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, ApolloAnnotationProcessor.class.getName(),
                ApolloAnnotationProcessor.class);
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    private List<String> splitForList(String str) {

        List<String> list ;
        if (StringUtils.isEmpty(str)) {
            list = new ArrayList<>();
        }else {
            list = Lists.newArrayList(str);
        }
        return list;
    }

}


