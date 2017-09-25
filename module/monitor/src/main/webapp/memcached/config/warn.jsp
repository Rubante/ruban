<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-报警设置</title>
<script type="text/javascript">
	warnSave = function(item) {
		var value = $("#" + item + "Value").val();
		var rate = $("#" + item + "Rate").val();
		var data = {
			"value" : value,
			"rate" : rate,
			"item" : item
		};

		//保存数据
		layer.confirm('确定要修改监控值么？', {
			btn : [ '确定', '取消' ]
		//按钮
		}, function(index) {
			$.ajax({
				type : 'POST',
				data : data,
				url : '<s:url value="/memcached/config/warnSave.do" />',
				dataType : "json",
				success : function(data) {
					if (data.flag == 0) {
						layer.msg(data.msg, {
							time : 3000
						});
					} else {
						layer.msg(data.msg, {
							time : 3000,
							icon : 5
						});
					}
				},
				error : function(ex) {
					layer.msg(ex, {
						time : 3000,
						icon : 5
					});
				}
			});
		});
	};
</script>
</head>
<body>
	<div class="page-header">
		<h3>报警配置</h3>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">报警设置</h3>
		</div>
		<div class="panel-body">
			<table class="table">
				<thead>
					<tr>
						<th>监控项</th>
						<th>阈值</th>
						<th>报警频率</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>CPU是使用率</td>
						<td><input class="form-control" id="cpuValue" type="text" value="${cpu.value}" placeholder="%(百分比)" /></td>
						<td><input class="form-control" id="cpuRate" type="text" value="${cpu.rate}" placeholder="分钟" /></td>
						<td><input class="btn btn-default" type="button" value="保存" onclick="warnSave('cpu');" /></td>
					</tr>
					<tr>
						<td>命中率</td>
						<td><input class="form-control" id="hitValue" type="text" value="${hit.value}" placeholder="%(百分比)" /></td>
						<td><input class="form-control" id="hitRate" type="text" value="${hit.rate}" placeholder="分钟" /></td>
						<td><input class="btn btn-default" type="button" value="保存" onclick="warnSave('hit');" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>