package com.ruban.framework.web.context;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.ruban.framework.core.context.ThreadContext;

/**
 * 线程上下文过滤器
 * 
 * @author ruban
 *
 */
public class ThreadContextCleanerFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        try {
            chain.doFilter(request, response);
        } finally {
            ThreadContext.getContext().clear();
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
    }
}