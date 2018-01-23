package com.dingding.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties文件内容运行时获取
 * applicationContext.xml中的properties Bean继承自此类
 */
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {
    private static Map<String, Object> ctxPropertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
                                     Properties props)throws BeansException {

        super.processProperties(beanFactory, props);
        //load properties to ctxPropertiesMap
        ctxPropertiesMap = new HashMap<String, Object>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }

    //static method for accessing context properties
    public static Object getContextProperty(String name) {
        return ctxPropertiesMap.get(name);
    }
}
