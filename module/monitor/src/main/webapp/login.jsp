<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<title>用户登录</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="pragram" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 
<script type="text/javascript" src='<s:url value="/static/js/jquery-3.2.1.min.js" />' ></script>
<script type="text/javascript" src='<s:url value="/static/layer/layer.js" />' ></script>

<link rel="stylesheet" href='<s:url value="/static/css/reset.css" />' />
<link rel="stylesheet" href='<s:url value="/static/bootstrap/css/bootstrap.min.css" />' />

<style type="text/css">
body {
	background-color: #f8f9fb;
}
@media(min-width:1200px){  
	.mycenter {
		margin-top: 10%;
		text-align: center;
		width: 500px;
		margin-left: 35%;
	}
}
@media(max-width:480px){  
	.mycenter {
		margin-top: 100px;
		text-align: center;
	}
} 

</style>
<script type="text/javascript">
	login = function() {
		var username = $("#username").val();
		var password = $("#password").val();

		// 输入校验：用户名
		if (username.trim() == '') {
			layer.tips('必须填写', '#username', {
				time : 2000,
				tips : [ 1, "red" ]
			});
			return true;
		}

		// 输入校验：密码
		if (password.trim() == '') {
			layer.tips('必须填写', '#password', {
				time : 2000,
				tips : [ 1, "red" ]
			});
			return true;
		}

		var data = {
			"username" : username,
			"password" : password
		};

		var index = layer.load(2, {
			time : 10 * 1000,
			shade : [ 0.3, '#393D49' ]
		});

		$.ajax({
			type : 'POST',
			url : '<s:url value="/login.do" />',
			dataType : "json",
			data : data,
			success : function(data) {
				//关闭
				layer.close(index);

				if (data.flag == 0) {
					layer.msg(data.msg, {
						time : 2000
					});
					window.location.href = '<s:url value="/main.do" />';
				} else {
					layer.msg(data.msg, {
						time : 2000,
						icon : 5
					});
				}
			},
			error : function(ex) {
				//关闭
				layer.close(index);

				layer.msg(ex, {
					time : 2000,
					icon : 5
				});
			}
		});
	};
	$(document).ready(function(e) {
		$(this).keydown(function(e) {
			if (e.which == "13") {
				login();//触发该事件
			}
		});
		
		if($("#loginForm").parent().attr("id") == "content"){
			window.location.href='<s:url value="/login.do" />';
		}
	});
</script>
</head>
<body>
	<form id="loginForm" method="post">
		<div class="cotainer-fluid mycenter">
			<div class="col-sm-10">
				<div class="col-lg-4 text-center text-info">
					<h2>请登录</h2>
				</div>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名" required autofocus />
				</div>
				<div class="col-sm-10" style="margin-top: 8px;margin-bottom:10px;">
					<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码" required autofocus />
				</div>
				<div class="col-sm-10"></div>
				<div class="col-sm-10 mycheckbox checkbox">
				</div>
				<div class="col-sm-10"></div>
				<div class="col-sm-10">
					<button type="button" style="width:100%;" class="btn btn-success" onclick="login()">登录</button>
				</div>
			</div>
		</div>
	</form>
</body>
</html>