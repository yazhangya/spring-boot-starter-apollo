package com.zy.springboot.starter.apollo.listener;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.config.ConfigPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.zy.springboot.starter.apollo.util.StringUtil.splitForList;

/**
 *
 * 让 apollo的配置在 初始化 env 之后进行加载，
 * 这样 可以在 conditional 等中使用 apollo的配置
 *
 *
 * @author zhangya
 * @date 2017/10/26
 */
public class ApolloSpringApplicationRunListener implements SpringApplicationRunListener{

    private final static String Private_Space = "apollo.space.privates";
    private final static String Private_Space_Need_Default = "apollo.space.private.need.default";

    private final static String Common_Space = "apollo.space.commons";

    private final static String Default_Namespace = "application";

    private final static String Server_Name = "spring.application.name";
    private final static String Apollo_App_Id = "app.id";



    private final SpringApplication application;

    private final String[] args;


    public ApolloSpringApplicationRunListener(SpringApplication application,String [] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment env) {
        String privates = env.getProperty(Private_Space);
        boolean  needDefault = env.getProperty(Private_Space_Need_Default,Boolean.class,true);
        String commons = env.getProperty(Common_Space);

        String appId = System.getProperty(Apollo_App_Id);
        if (StringUtils.isEmpty(appId)) {
            appId = env.getProperty(Apollo_App_Id);
        }

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

        addApolloConfigToEnvLast(privateList, env);
        addApolloConfigToEnvLast(commonList, env);



    }

    /**
     *
     * @param namespaces
     * @param env
     */
    private void addApolloConfigToEnvLast(List<String> namespaces,ConfigurableEnvironment env) {
        namespaces.stream().forEach((key)->{
            Config config = ConfigService.getConfig(key);
            env.getPropertySources().addLast(new ConfigPropertySource(key, config));
        });
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {

    }
}
