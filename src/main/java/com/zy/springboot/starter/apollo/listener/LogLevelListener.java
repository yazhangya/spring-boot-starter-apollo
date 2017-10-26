package com.zy.springboot.starter.apollo.listener;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.zy.springboot.starter.apollo.annotation.ApolloConfigChangeOnKeyListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggerConfiguration;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.stereotype.Component;

/**
 * 动态调整日志级别
 * @author zhangya
 * @date 2017/10/19
 */
@Slf4j
@Component
public class LogLevelListener {


    @Autowired
    private LoggingSystem system;

    @ApolloConfigChangeListener
    public void setLevel(ConfigChangeEvent event) {

        LoggerConfiguration root = system.getLoggerConfiguration("root");

        ConfigChange change = event.getChange("log.level");
        if (change != null) {
            String newValue = change.getNewValue().toUpperCase();
            LogLevel logLevel = LogLevel.valueOf(newValue);
            system.setLogLevel("root", logLevel);
            log.info("log level change  from {} to {} ", root.getEffectiveLevel(),logLevel);
        }

    }

    @ApolloConfigChangeOnKeyListener(key = "test.key1")
    public void testKey(ConfigChangeEvent event) {
        ConfigChange change = event.getChange("test.key");
        log.info("test.key is change : "+change.getOldValue()+" to "+change.getNewValue());
    }

}
