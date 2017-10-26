package com.zy.springboot.starter.apollo.util;

import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangya
 * @date 2017/10/26
 */
public class StringUtil {

    public static List<String> splitForList(String str) {

        List<String> list ;
        if (StringUtils.isEmpty(str)) {
            list = new ArrayList<>();
        }else {
            list = Lists.newArrayList(str);
        }
        return list;
    }
}
