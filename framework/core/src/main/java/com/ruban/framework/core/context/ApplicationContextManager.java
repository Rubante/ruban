package com.ruban.framework.core.context;

/**
 * 应用上下文管理器
 * 
 * @author ruban
 *
 */
public class ApplicationContextManager {
    private static ApplicationContext context = new ApplicationContextImpl();

    public static ApplicationContext getContext() {
        return context;
    }
}