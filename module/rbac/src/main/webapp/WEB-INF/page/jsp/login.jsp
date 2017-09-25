<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html> 
<html lang="zh-CN"> 
<head> 
    <jsp:include page="/WEB-INF/page/jsp/include.jsp"></jsp:include>
	<link href='<s:url value="/static/css/platform/login.css" />' rel="stylesheet">
</head> 
<body>
	<div class="left-bg"></div>
    <div class="right-bg"></div>
    <div class="login-logo"></div>
    <div class="login-center">
    	<div class="login-content">
    		<div class="notice">
    			<div class="notice-title">
    				<i class="notice-icon"></i>
    				<span>通知公告</span>
    			</div>
    			<ul class="notice-list">
    				<li class="list-item ellipsis">
    					<a href="notice.html" target="_blank">
    						<i class="rt-icon"></i>
    						系统版本更新
    					</a>
    				</li>
    			</ul>
    		</div>
    		<div class="login-input">
    			<script type="text/javascript">
    				submit=function(){
    					var username = $("#username").val();
    					var password = $("#password").val();
    					if(username.trim() == ''){
    						layer.alert("请填写用户名", {
    							closeBtn: 0
    						}, function(index){
    							$("#username").focus();
    							layer.close(index);
    						});
    						return false;
    					}
    					
    					if(password.trim() == '') {
    						layer.alert("请填写密码", {
    							closeBtn: 0
    						}, function(index){
    							$("#password").focus();
    							layer.close(index);
    						});
    						
    						return false;
    					}
    					document.getElementById("form").submit();
    				}
    			</script>
    			<form id="form" action="/rbac/login" method="post" onsubmit="return submit();">
	    			<div class="login-label">用户登录</div>
	    			<div class="username">
	    				<i class="username-icon"></i>
	    				<input type="text" id="username" name="username" value="${username}" placeholder="用户名">
	    			</div>
	    			<div class="password">
	    				<i class="password-icon"></i>
	    				<input type="password" id="password" name="password" placeholder="密码">
	    			</div>
	    			<div class="login-btn" onclick="submit();">马上登录</div>
    			</form>
    		</div>
    	</div>
    </div>
</body>
</html>