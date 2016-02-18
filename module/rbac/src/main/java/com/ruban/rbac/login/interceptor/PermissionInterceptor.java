package com.ruban.rbac.login.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		//TODO 需要进行补充
		HttpSession session = request.getSession();

		if ((boolean) session.getAttribute("login_flag") == true) {
			return true;
		}
		return false;
	}
}
