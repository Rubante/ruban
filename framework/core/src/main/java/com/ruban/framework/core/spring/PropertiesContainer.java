package com.ruban.framework.core.spring;

/**
 * 获取系统中spring容器加载的property值
 * 
 * @author ruban
 *
 */
public class PropertiesContainer {

    private static RubanPropertyPlaceholderConfigurer configurer = null;

    /**
     * 获取properties的值
     * 
     * @param key
     * @return
     */
    public static String getPropertyValue(String key) {
        if (configurer == null) {
            configurer = SpringContextLocator.getApplicationContext().getBean(RubanPropertyPlaceholderConfigurer.class);
        }

        return configurer.getValue(key);
    }
}
