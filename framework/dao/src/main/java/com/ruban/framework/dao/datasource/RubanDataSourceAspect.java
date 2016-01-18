package com.ruban.framework.dao.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多数据源切换
 * 
 * @author ruban
 *
 */
public class RubanDataSourceAspect {

    Logger logger = LoggerFactory.getLogger(RubanDataSourceAspect.class);

    public void before(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(RunbanDataSource.class)) {
                RunbanDataSource data = m.getAnnotation(RunbanDataSource.class);
                RunbanDataSourceHolder.putDataSource(data.value());
                logger.debug("current datasource:" + data.value());
            }

        } catch (Exception ex) {
            logger.error("DataSourceAspect.before", ex);
        }
    }
}
