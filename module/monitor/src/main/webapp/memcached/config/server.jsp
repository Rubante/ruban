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
//启动监控
startMonitor = function() {
	//提示框
	layer.confirm('确定要开启监控么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){

		$.ajax({
			type : 'POST',
			url : '<s:url value="/memcached/monitor/start.do" />',
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}

				$("#start").removeClass("btn-success");
				$("#start").addClass("btn-disabled");
				$("#stop").removeClass("btn-disabled");
				$("#stop").addClass("btn-warning");
				
				$("#start").unbind("click");
				$("#stop").bind("click", stopMonitor);
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});
	});
};
//停止监控
stopMonitor = function() {
	//提示框
	layer.confirm('确定要停止监控么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){

		$.ajax({
			type : 'POST',
			url : '<s:url value="/memcached/monitor/stop.do" />',
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
				
				$("#stop").removeClass("btn-warning");
				$("#stop").addClass("btn-disabled");
				$("#start").removeClass("btn-disabled");
				$("#start").addClass("btn-success");
				
				$("#stop").unbind("click");
				$("#start").bind("click", startMonitor);
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});
	});
};
// 添加服务器
addServer=function(){
	var host = $("#host").val();
	var port = $("#port").val();
	
	var data = {
		"host" : host,
		"port" : port
	};
	
	// 输入校验：IP地址
	if(host.trim() == '') {
		layer.tips('必须填写', '#host', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：端口
	if(port.trim() == '') {
		layer.tips('必须填写', '#port', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	$.ajax({
		type : 'POST',
		url : '<s:url value="/memcached/config/addServer.do" />',
		data : data,
		dataType : "json",
		success : function(data) {
			if(data.flag == 0) {
				layer.msg(data.msg, {time:3000});
				var html = Mustache.to_html($("#template").html(), data.value);
				
				$("#serverList").append(html);
			} else {
				layer.msg(data.msg, {time:3000, icon:5});
			}
			$("#serverAddress").modal('hide');
		},
		error : function(ex){
			layer.msg('添加失败！', {time:3000, icon:5});
		}
	});	
};
//修改服务器页面
updateServerPage=function(id){
	$("#hostUpdate").val($("#host_"+id).val());
	$("#portUpdate").val($("#port_"+id).val());
	$("#idUpdate").val(id);
	
	$("#updateAddress").modal("show");
};
//修改服务器
updateServer=function(){
	var id = $("#idUpdate").val();
	var host = $("#hostUpdate").val();
	var port = $("#portUpdate").val()
	var data = {
		"id" : id,
		"host" : host,
		"port" : port
	};
	
	
	// 输入校验：IP地址
	if(host.trim() == '') {
		layer.tips('必须填写', '#hostUpdate', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：端口
	if(port.trim() == '') {
		layer.tips('必须填写', '#portUpdate', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	//提示框
	layer.confirm('确定要修么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/memcached/config/updateServer.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
					
					$("#host_span_" + id).text(host);
					$("#host_" + id).val(host);
					$("#port_span_" + id).text(port);
					$("#port_" + id).val(port);
					
					$("#updateAddress").modal('hide');
					
					$("#serverList").append();
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
//删除服务器
deleteServer=function(id){
	
	var data = {
		"id" : id
	};
	//提示框
	layer.confirm('确定要删除么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/memcached/config/deleteServer.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
					
					$("#data_"+id).remove();
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
	<c:if test='${monitorState=="2"||monitorState==null}'>
		$("#start").bind("click", startMonitor);
	</c:if>
	<c:if test='${monitorState=="1"}'>
		$("#stop").bind("click", stopMonitor);
	</c:if>
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
			<h3 class="panel-title">memcached服务器地址</h3>
		</div>
		<div class="panel-body">
			<div style="margin-bottom: 10px;">
				<div style="display: inline-block;"><button type="button" class="btn btn-info" data-toggle="modal" data-target="#serverAddress">添加服务器地址</button></div>
				<div style="display: inline-block;"><button type="button" id="start" class="btn <c:if test='${monitorState=="2"||monitorState==null}'>btn-success</c:if> <c:if test='${monitorState=="1"}'>btn-disabled</c:if>">启动</button></div>
				<div style="display: inline-block;"><button type="button" id="stop" class="btn <c:if test='${monitorState=="1"}'>btn-warning</c:if> <c:if test='${monitorState=="2"||monitorState==null}'>btn-disabled</c:if>">停止</button></div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>IP</th>
						<th>端口</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="serverList">
					<c:forEach items="${servers}" var="item">
						<tr id="data_${item.id}">
							<td><span id="host_span_${item.id}">${item.host}</span><input type="hidden" value="${item.host}" id="host_${item.id}" /></td>
							<td><span id="port_span_${item.id}">${item.port}</span><input type="hidden" value="${item.port}" id="port_${item.id}" /></td>
							<td><button type="button" class="btn btn-default" onclick="updateServerPage('${item.id}')">修改</button>
								<button type="button" class="btn btn-warning" onclick="deleteServer('${item.id}')">删除</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<!-- 服务器添加 -->
	<div class="modal fade" id="serverAddress" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加memcached服务器</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for="name">IP地址</label> <input type="text" class="form-control" id="host" placeholder="请输入IP地址">
						</div>
						<div class="form-group">
							<label for="name">端口</label> <input type="text" class="form-control" id="port" placeholder="请输入端口号" value="11211">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="addServer()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 服务器修改 -->
	<div class="modal fade" id="updateAddress" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="updateModalLabel">修改memcached服务器</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<input type="hidden" id="idUpdate" value="" />
							<label for="name">IP地址</label> <input type="text" class="form-control" id="hostUpdate" placeholder="请输入IP地址">
						</div>
						<div class="form-group">
							<label for="name">端口</label> <input type="text" class="form-control" id="portUpdate" placeholder="请输入端口号">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="updateServer()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>