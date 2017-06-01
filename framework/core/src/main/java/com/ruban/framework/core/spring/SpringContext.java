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
public class SpringContextLocator implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(SpringContextLocator.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextLocator.applicationContext = applicationContext;
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

        return SpringContextLocator.applicationContext;
    }

    /**
     * 根据beanName获取对象
     * 
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
        return SpringContextLocator.applicationContext.getBean(beanName);
    }

    /**
     * 根据类型获取对象
     * 
     * @param className
     * @return
     */
    public static <T> T getBean(Class<T> className) {
        return SpringContextLocator.applicationContext.getBean(className);
    }

    /**
     * 根据beanName获取对象的类型
     * 
     * @param beanName
     * @return
     */
    public Class<?> getType(String beanName) {
        return SpringContextLocator.applicationContext.getType(beanName);
    }

    /**
     * 根据beanName判断对象是否为单例
     * 
     * @param beanName
     * @return
     */
    public boolean isSingleton(String beanName) {
        return SpringContextLocator.applicationContext.isSingleton(beanName);
    }

    /**
     * 根据beanName是否在容器中存在
     * 
     * @param beanName
     * @return
     */
    public boolean containsBean(String beanName) {
        return SpringContextLocator.applicationContext.containsBean(beanName);
    }
}
