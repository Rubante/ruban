package com.ruban.framework.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * springContext定位器
 * 
 * @author ruban
 *
 */
public class SpringContext implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(SpringContext.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 获取spring容器
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        // 未在spring中声明
        if (applicationContext == null) {
            IllegalStateException ex = new IllegalStateException("not initialized!");
            logger.error("springContextLocator not configured in spring!", ex);
        }

        return SpringContext.applicationContext;
    }

    /**
     * 根据beanName获取对象
     * 
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return SpringContext.applicationContext.getBean(beanName);
    }

    /**
     * 根据beanName获取对象
     * 
     * @param beanName
     * @param cls
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> cls) {
        return SpringContext.applicationContext.getBean(beanName, cls);
    }

    /**
     * 根据类型获取对象
     * 
     * @param className
     * @return
     */
    public static <T> T getBean(Class<T> className) {
        return SpringContext.applicationContext.getBean(className);
    }

    /**
     * 根据beanName获取对象的类型
     * 
     * @param beanName
     * @return
     */
    public static Class<?> getType(String beanName) {
        return SpringContext.applicationContext.getType(beanName);
    }

    /**
     * 根据beanName判断对象是否为单例
     * 
     * @param beanName
     * @return
     */
    public static boolean isSingleton(String beanName) {
        return SpringContext.applicationContext.isSingleton(beanName);
    }

    /**
     * 根据beanName是否在容器中存在
     * 
     * @param beanName
     * @return
     */
    public static boolean containsBean(String beanName) {
        return SpringContext.applicationContext.containsBean(beanName);
    }

    /**
     * 获取国际化信息
     * 
     * @param key
     * @return
     */
    public static String getText(String key) {
        return SpringContext.applicationContext.getMessage(key, null, null);
    }

    /**
     * 获取国际化信息
     * 
     * @param key
     * @return
     */
    public static String getText(String key, String... params) {
        return SpringContext.applicationContext.getMessage(key, params, null);
    }
}
