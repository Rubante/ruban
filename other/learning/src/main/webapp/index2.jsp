<%@page import="org.slf4j.LoggerFactory"%>
<%@page import="org.slf4j.Logger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hello world</title>
</head>
<body>
<%
	Logger logger = LoggerFactory.getLogger(this.getClass());
	String ip = request.getHeader("x-forwarded-for");  
	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	    ip = request.getHeader("PRoxy-Client-IP");  
	}  
	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	    ip = request.getHeader("WL-Proxy-Client-IP");  
	}  
	if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	    ip = request.getRemoteAddr();  
	}

	logger.error(":ip:"+ip);
	
	String userAgent = request.getHeader("user-agent");
	
	logger.error(":userAgent:"+userAgent);
	
%>
<h1>hello world</h1>
</body>
</html>