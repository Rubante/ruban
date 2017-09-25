<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-写入数据</title>
</head>
<script type="text/javascript">
//写入数据
setData = function() {
	var key = $("#key").val();
	
	// 输入校验
	if(key.trim() == '') {
		layer.tips('必须填写', '#key', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var type = $("input[name='type']:checked").val();

	if(type == ''){
		layer.tips('必须填写', '#type1', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}

	
	var value = $("#value").val();
	
	// 输入校验
	if(value.trim() == '') {
		layer.tips('必须填写', '#value', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var time = $("#time").val();
	
	var data = {
		"key" : key,
		"type" : type,
		"time" : time,
		"value" : value
	};

	$.ajax({
		type : 'POST',
		url : '<s:url value="/memcached/opt/write.do" />',
		data : data,
		dataType : "text",
		success : function(res) {
			layer.msg(res, {time:3000});
		},
		error : function(ex){
			layer.msg('写入失败！', {time:3000, icon:5});
		}
	});
};
</script>
<body>
	<div class="page-header">
		<h3>memcached数据写入</h3>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">数据写入</h3>
		</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label for="name">key值</label> 
					<input type="text" value="" name="key" id="key" class="form-control"/>
				</div>
				<div class="form-group">
					<label for="name">类型</label> 
					<div>
					    <label class="checkbox-inline">
					        <input name="type" type="checkbox" id="type1" value="string" checked="checked"> plain text
					    </label>
					    <label class="checkbox-inline">
					        <input name="type" type="checkbox" id="type2" value="hex"> base64
					    </label>
					</div>
				</div>
				<div class="form-group">
					<label for="name">value值(字符串或16进制政)</label> 
					<input type="text" value="" name="value" id="value" class="form-control"/>
				</div>
				<div class="form-group">
					<label for="name">过期时间(0代表不失效)</label> 
					<input type="number" value="0" name="time" id="time" class="form-control"/>
				</div>
				<button type="button" class="btn btn-primary" onclick="setData()">写入</button>
			</form>
		</div>
	</div>
</body>
</html>