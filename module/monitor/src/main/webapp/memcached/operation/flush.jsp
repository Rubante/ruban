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
//清空数据
flushData = function() {
	//提示框
	layer.confirm('确定要清空缓存么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		var delay = $("#delay").val();
		
		var data = {
			"delay" : delay
		};
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/memcached/opt/flush.do" />',
			data : data,
			dataType : "text",
			success : function(res) {
				layer.msg(res, {time:3000});
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});
	});
};
</script>
<body>
	<div class="page-header">
		<h3>清空memcached</h3>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">清空数据</h3>
		</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label for="name">延迟时间</label> 
					<input type="number" value="" name="delay" id="delay" class="form-control"/>
				</div>
				<button type="button" class="btn btn-primary" onclick="flushData()">清空</button>
			</form>
		</div>
	</div>
</body>
</html>