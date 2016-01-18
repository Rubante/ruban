package com.ruban.framework.core.context;

/**
 * 线程上下文
 * 
 * @author ruban
 *
 */
public class ThreadContext extends CommonContext {
    private static ThreadLocal<ThreadContext> context = new ThreadLocal<ThreadContext>() {
        protected synchronized ThreadContext initialValue() {
            // TODO 参数是否有？
            return new ThreadContext();
        }
    };

    public static ThreadContext getContext() {
        return (ThreadContext) context.get();
    }
}