<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" href='<c:url value='/css/bootstrap.min.css' />'>
<title>Insert title here</title>

</head>
<body style="background-color: #f8f8f8">
<div class="container">
<form class="form-horizontal" method="post">
	<div class="form-group center-align" style="width:500px;">
		<label>请输入您的评论：</label>
		<div>
			<textarea id="post" name="post" class="form-control" rows="5"></textarea>
		</div>
	</div>
	<div>
		选择文章所属的标签：
		<input type="checkbox" />JAVA&nbsp;&nbsp;&nbsp;
		<input type="checkbox" />Spring&nbsp;&nbsp;&nbsp;
		<input type="checkbox" />Struts2&nbsp;&nbsp;&nbsp;
		<input type="checkbox" />Hibernate3&nbsp;&nbsp;&nbsp;
		<input type="text" >
	</div>
	<div><button class="btn-success" onclick="alert(1);">评论</button></div>
</form>

	<div>
		<div>我的测试..........</div>
		<div>我的测试..........</div>
		<div>我的测试..........</div>
		<div>我的测试..........</div>
		<div>我的测试..........</div>
		<div>我的测试..........</div>
		<div>我的测试..........</div>
	</div>
</div>

<input id="userno" type="hidden" />
</body>
</html>