<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-读取数据</title>
</head>
<script type="text/javascript">
//获取数据
getData = function() {
	var key = $("#key").val();
	
	// 输入校验
	if(key.trim() == '') {
		layer.tips('必须填写', '#key', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var data = {
		"key" : key
	};

	$.ajax({
		type : 'POST',
		url : '<s:url value="/memcached/opt/read.do" />',
		data : data,
		dataType : "text",
		success : function(data) {
			$("#dataDetail").html(data);
		},
		error : function(ex){
			layer.msg('获取失败！', {time:3000, icon:5});
		}
	});
};
$(document).ready(function(){
});
</script>
<body>
	<div class="page-header">
		<h3>memcached数据读取</h3>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">数据读取</h3>
		</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label for="name">key值</label> 
					<input type="text" value="" name="key" id="key" class="form-control"/>
				</div>
				<button type="button" class="btn btn-primary" onclick="getData()">读取</button>
			</form>
			<hr />
			<div class="container-fluid" style="margin-top: 15px; margin-bottom: 15px;">
				<div id="dataDetail" class="col-md-12 col-xs-12 col-xs-12">
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>