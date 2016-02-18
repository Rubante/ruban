package com.ruban.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.ruban.framework.core.spring.PropertiesContainer;

/**
 * 对请求进行防止xss的处理 1.比对referer是否相同
 * 
 * @author ruban
 *
 */
public class XSSRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        // 请求上送的referer
        String refererHeader = req.getHeader("referer");
        // 配置中规定的referer
        String referer = PropertiesContainer.getPropertyValue("referer");

        // 判断是否相称
        if (refererHeader != null && referer != null && !refererHeader.contains(referer)) {
            throw new RuntimeException("referer.error");
        }

        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }

}
