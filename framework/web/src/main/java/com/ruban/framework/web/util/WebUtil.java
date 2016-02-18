package com.ruban.framework.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web工具类，一些常用的在web的方法
 * 
 * @author ruban
 *
 */
public class WebUtil {

    /**
     * 获取basePath
     * 
     * @param request
     * @return
     */
    public static String getUrlBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/";
        return basePath;
    }

    /**
     * 页面跳转到url
     * 
     * @param response
     * @param url
     * @throws java.io.IOException
     */
    public static void forward(HttpServletResponse response, String url) throws IOException {
        response = forbidCache(response);
        response.sendRedirect(url);
    }

    /**
     * 禁止response缓存
     * 
     * @param response
     */
    public static HttpServletResponse forbidCache(HttpServletResponse response) {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        return response;
    }

    /**
     * 防止中文文件名在下载的时候出现乱码
     * 
     * @param chineseFilename
     * @return
     */
    public static String toUtf8InFilename(String chineseFilename) {
        int ZERO = 0;
        StringBuffer buffer = new StringBuffer();
        for (int i = ZERO; i < chineseFilename.length(); i++) {
            char c = chineseFilename.charAt(i);
            if (c >= ZERO && c <= 255) {
                buffer.append(c);
            } else {
                byte[] b;
                try {
                    Character C = new Character(c);
                    b = C.toString().getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[ZERO];
                }
                for (int j = ZERO; j < b.length; j++) {
                    int k = b[j];
                    if (k < ZERO)
                        k += 256;
                    buffer.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return buffer.toString();
    }

    /**
     * 获取客户端ip地址(可以穿透代理)
     * 
     * @param request
     * @return
     */
    public static String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取相对地址的绝对地址
     * 
     * @param request
     * @param relativePath
     * @return
     */
    public static String getRealPath(HttpServletRequest request, String relativePath) {
        return request.getSession().getServletContext().getRealPath(relativePath);
    }
}
