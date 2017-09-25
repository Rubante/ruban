<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-操作系统信息</title>
<style type="text/css">
.panel-group{
	max-height: 100%;
}
</style>
</head>
<script type="text/javascript">
	jvmOs = function(){
		var host = $("#server").val();
		
		var data = {
			"host" : host
		};
			
		$.ajax({
			type : 'POST',
			url : '<s:url value="/jvm/monitor/os.do" />',
			data : data,
			dataType : "html",
			success : function(html) {
				$("#content").html(html);
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});
	};
</script>
<body>
	<div class="page-header">
		<h3>操作系统信息</h3>
	</div>
	<div class="container-fluid">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">查询条件</h3>
			</div>
			<div class="panel-body">
				<label>监控服务器</label>
				<select id="server" name="server" class="form-control" style="width:200px;display:inline;margin-right:15px;margin-left:15px;">
					<option>请选择IP</option>
					<c:forEach var="item" items="${servers}">
						<option value="${item.value.host}" <c:if test="${host eq item.value.host}">selected</c:if>>
							${item.value.host}
						</option>
					</c:forEach>
				</select>
				<button type="button" class="btn btn-primary" onclick="jvmOs()">查询</button>
			</div>
		</div>

		<c:forEach items="${result}" var="os" varStatus="osStatus">
			<div class="panel-group" id="accordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#server${osStatus.index}">
							<span class="text-primary">
							${os.key}
							</span>
						</a>
						</h3>
					</div>
					<div id="server${osStatus.index}" class="panel-collapse collapse in">
						<div class="panel-body">
							<table class="table">
								<thead>
									<tr>
										<th>属性</th>
										<th>数值</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td style="word-break:break-all;">系统名称</td>
										<td>
											${os.value.name}
										</td>
									</tr>
									<tr>
										<td style="word-break:break-all;">架构</td>
										<td>
											${os.value.arch}
										</td>
									</tr>
									<tr>
										<td style="word-break:break-all;">版本</td>
										<td>
											${os.value.version}
										</td>
									</tr>
									<tr>
										<td style="word-break:break-all;">可用CPU个数</td>
										<td>
											${os.value.availableProcessors}
										</td>
									</tr>
									<tr>
										<td style="word-break:break-all;">系统平均负载</td>
										<td>
											${os.value.systemLoadAverage}
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>