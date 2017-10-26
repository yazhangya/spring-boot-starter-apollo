package com.zy.springboot.starter.apollo.annotation;

import com.ctrip.framework.apollo.core.ConfigConsts;

import java.lang.annotation.*;

/**
 *
 * 监听 apollo中特定的key
 * @author zhangya
 * @date 2017/10/25
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ApolloConfigChangeOnKeyListener {

    /**
     * listen apollo change namespace
     * @return
     */
    String namespace() default ConfigConsts.NAMESPACE_APPLICATION;

    /**
     * listen apollo change key on namespace
     * @return
     */
    String key() ;

}
