<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-延时数据</title>
</head>
<script type="text/javascript">
//延时数据
touchData = function() {
	var key = $("#key").val();
	
	// 输入校验
	if(key.trim() == '') {
		layer.tips('必须填写', '#key', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var time = $("#time").val();
	
	// 输入校验
	if(time.trim() == '') {
		layer.tips('必须填写', '#time', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var data = {
		"key" : key,
		"time" : time
	};

	$.ajax({
		type : 'POST',
		url : '<s:url value="/memcached/opt/touch.do" />',
		data : data,
		dataType : "text",
		success : function(res) {
			layer.msg(res, {time:3000});
		},
		error : function(ex){
			layer.msg('延时失败！', {time:3000, icon:5});
		}
	});
};
</script>
<body>
	<div class="page-header">
		<h3>memcached数据延时</h3>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">数据延时</h3>
		</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label for="name">key值</label> 
					<input type="text" value="" name="key" id="key" class="form-control"/>
				</div>
				<div class="form-group">
					<label for="name">时间</label> 
					<input type="number" value="" name="time" id="time" class="form-control"/>
				</div>
				<button type="button" class="btn btn-primary" onclick="touchData()">延时</button>
			</form>
		</div>
	</div>
</body>
</html>