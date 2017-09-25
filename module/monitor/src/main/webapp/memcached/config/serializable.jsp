<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-反序列化配置</title>
</head>
<script type="text/javascript">
//配置序列化
setSerializable = function() {
	var className = $("#className").val();
	
	var data = {
		"className" : className
	};

	$.ajax({
		type : 'POST',
		url : '<s:url value="/memcached/config/serializable.do" />',
		data : data,
		dataType : "json",
		success : function(data) {
			if(data.flag == 0) {
				layer.msg(data.msg, {time:3000});
			} else {
				layer.msg(data.msg, {time:3000, icon:5});
			}
		},
		error : function(ex){
			layer.msg(ex, {time:3000, icon:5});
		}
	});
};
</script>
<body>
	<div class="page-header">
		<h3>memcached反序列化配置</h3>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">反序列化配置</h3>
		</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label for="name">反序列化类</label> 
					<input type="text" value="${className}" name="className" id="className" class="form-control"/>
				</div>
				<button type="button" class="btn btn-primary" onclick="setSerializable()">保存</button>
			</form>
		</div>
	</div>
</body>
</html>