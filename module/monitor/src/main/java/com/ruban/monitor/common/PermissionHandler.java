package com.ruban.monitor.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ruban.monitor.server.bean.User;

/**
 * 权限拦截
 * 
 * @author yjwang
 *
 */
public class PermissionHandler extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        User user = (User) request.getSession().getAttribute(MonitorConstant.LOGINED);
        if (user == null) {
            response.sendRedirect("/login.do");
            return false;
        } else {
            return super.preHandle(request, response, handler);
        }

    }

}
