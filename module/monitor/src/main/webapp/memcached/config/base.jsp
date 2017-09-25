<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-连接信息配置</title>
</head>
<script type="text/javascript">
//memcached配置
submitDbData = function() {
	//提示框
	layer.confirm('确定要修改么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		var locator = $("#locator").val();
		var hash = $("#hash").val();
		var statsRate = $("#statsRate").val();
		
		// 输入校验
		if(statsRate.trim() == '') {
			layer.tips('必须填写', '#statsRate', {
				time: 2000,
				tips: [1,"red"]
			});
			layer.close(index);
			return true;
		}
		
		var data = {
			"locator" : locator,
			"hash" : hash,
			"statsRate" : statsRate
		};
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/memcached/config/baseSave.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					$("#redisPanel").addClass("panel-success");
					$("#dbPanel").removeClass("panel-success");
					layer.msg(data.msg, {time:3000});
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});
	});
};
$(document).ready(function(){
	$("#locator").val('${locator}');
	$("#hash").val('${hash}');
	$("#statsRate").val('${statsRate}');
});
</script>
<script id="template" type="text/template">
	<tr id="data_{{id}}">
		<td><span id="host_span_{{id}}">{{host}}</span><input type="hidden" value="{{host}}" id="host_{{id}}" /></td>
		<td><span id="port_span_{{id}}">{{port}}</span><input type="hidden" value="{{port}}" id="port_{{id}}" /></td>
		<td><button type="button" class="btn btn-default" onclick="updateServerPage('{{id}}')">修改</button>
			<button type="button" class="btn btn-warning" onclick="deleteServer('{{id}}')">删除</button>
		</td>
	</tr>
</script>
<body>
	<div class="page-header">
		<h3>memcached连接信息</h3>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">基本参数</h3>
		</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label for="name">负载算法</label> 
					<select name="locator" class="form-control" id="locator">
						<option value="ARRAY_MOD">ARRAY_MOD(数组取余,默认)</option>
						<option value="CONSISTENT">CONSISTENT(一致性hash算法)</option>
					</select>
				</div>
				<div class="form-group">
					<label for="name">hash算法</label> 
					<select name="hash" class="form-control" id="hash">
						<option value="NATIVE_HASH">NATIVE_HASH(hashcode方法,默认)</option>
						<option value="CRC_HASH">CRC_HASH</option>
						<option value="FNV1_64_HASH">FNV1_64_HASH</option>
						<option value="FNV1A_64_HASH">FNV1A_64_HASH</option>
						<option value="FNV1_32_HASH">FNV1_32_HASH</option>
						<option value="FNV1A_32_HASH">FNV1A_32_HASH</option>
						<option value="KETAMA_HASH">KETAMA_HASH(基于md5的hash算法)</option>
					</select>
				</div>
				<div class="form-group">
					<label for="name">stats采集频率(秒)<span class="text-danger">*</span></label> 
					<input type="number" value="" name="statsRate" id="statsRate" class="form-control"/>
				</div>
				<button type="button" class="btn btn-primary" onclick="submitDbData()">提交</button>
			</form>
		</div>
	</div>
</body>
</html>