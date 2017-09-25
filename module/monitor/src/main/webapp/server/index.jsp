<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-基础配置</title>
</head>
<script type="text/javascript">
	// 提交redis参数配置
	submitRedisData = function() {
		//提示框
		layer.confirm('确定要修改么？', {
		  btn: ['确定','取消'] //按钮
		}, function(index){
			var host = $("#host").val();
			var port = $("#port").val();
			var database = $("#database").val();
			var prefix = $("#prefix").val();
			
			var data = {
				"host" : host,
				"port" : port,
				"password" : $("#password").val(),
				"database" : database,
				"prefix" : prefix
			};

			// 输入校验
			if(host.trim() == '') {
				layer.tips('必须填写', '#host', {
					time: 2000,
					tips: [1,"red"]
				});
				layer.close(index);
				return true;
			}
			
			// 输入校验
			if(port.trim() == '') {
				layer.tips('必须填写', '#port', {
					time: 2000,
					tips: [1,"red"]
				});
				layer.close(index);
				return true;
			}
			
			// 输入校验
			if(database.trim() == '') {
				layer.tips('必须填写', '#database', {
					time: 2000,
					tips: [1,"red"]
				});
				layer.close(index);
				return true;
			}
			
			// 输入校验
			if(prefix.trim() == '') {
				layer.tips('必须填写', '#prefix', {
					time: 2000,
					tips: [1,"red"]
				});
				layer.close(index);
				return true;
			}
			
			$.ajax({
				type : 'POST',
				url : '<s:url value="/server/redisSave.do" />',
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
				error : function(err){
					layer.msg(err, {time:3000, icon:5});
				}
			});
		});
	};
	
	//提交默认db配置
	submitDbData = function() {
		//提示框
		layer.confirm('确定要修改本地DB存储么？', {
		  btn: ['确定','取消'] //按钮
		}, function(index){
			var filePath = $("#filePath").val();
			var data = {
					"filePath" : filePath
			};
			
			// 输入校验
			if(filePath.trim() == '') {
				layer.tips('必须填写', '#filePath', {
					time: 2000,
					tips: [1,"red"]
				});
				layer.close(index);
				return true;
			}
			
			$.ajax({
				type : 'POST',
				url : '<s:url value="/server/dbSave.do" />',
				data : data,
				dataType : "json",
				success : function(data) {
					if(data.flag == 0) {
						$("#dbPanel").addClass("panel-success");
						$("#redisPanel").removeClass("panel-success");
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
</script>
<body>
	<div class="page-header">
		<h3>监控服务器配置信息</h3>
	</div>
	
	<div id="dbPanel" class="panel panel-default <c:if test='${select==1}'>panel-success</c:if>">
		<div class="panel-heading">
			<h3 class="panel-title">本机文件存储（默认）</h3>
		</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label for="name">数据库路径</label> <input type="text" value="${filePath}" class="form-control" id="filePath" placeholder="数据库路径">
				</div>
				<button type="button" class="btn btn-default" onclick="submitDbData();">提交</button>
			</form>
		</div>
	</div>
	
	<div id="redisPanel" class="panel panel-default <c:if test='${select==2}'>panel-success</c:if>">
		<div class="panel-heading">
			<h3 class="panel-title">使用redis</h3>
		</div>
		<div class="panel-body">
			<form role="form">
				<div class="form-group">
					<label for="name">IP地址</label> <input type="text" value="${host}" class="form-control" id="host" placeholder="redis ip">
				</div>
				<div class="form-group">
					<label for="name">端口</label> <input type="number" value="${port}" maxlength="5" class="form-control" id="port" placeholder="redis 端口">
				</div>
				<div class="form-group">
					<label for="name">密码(未设置可不填写)</label> <input type="text" value="${password}" class="form-control" id="password" placeholder="redis 密码">
				</div>
				<div class="form-group">
					<label for="name">数据库索引</label> <input type="text" value="${database}" class="form-control" id="database" placeholder="redis 数据库索引">
				</div>
				<div class="form-group">
					<label for="name">数据库名</label> <input type="text" value="${prefix}" class="form-control" id="prefix" placeholder="redis 数据库名">
				</div>
				<button type="button" class="btn btn-default" onclick="submitRedisData();">提交</button>
			</form>
		</div>
	</div>
</body>
</html>