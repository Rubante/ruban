<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-key值列表</title>
<style type="text/css">
.panel-group{
	max-height: 100%;
}
</style>
</head>
<script type="text/javascript">
getList = function(index){
	var data = {
		"page" : index
	};
	
	$.ajax({
		type : 'POST',
		url : '<s:url value="/memcached/opt/data/list.do" />',
		data : data,
		dataType : "html",
		success : function(html) {
			$("#content").html(html);
		},
		error : function(ex){
			layer.msg(ex, {time:3000, icon:5});
		}
	});
}
</script>
<body>
	<div class="page-header">
		<h3>memcached键列表</h3>
	</div>
	<div class="panel panel-default">	
		<div class="panel-body">
			<table class="table pc">
				<thead>
					<tr>
						<th>键</th>
						<th>有效期(0代表不过期)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${items}" var="item">
						<tr>
							<td>${item.key}</td>
							<td>${item.exp}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div>
				<div>共${total}条记录</div>
				<div style="float:right">
					<ul class="pagination" style="margin-top: -20px; margin-bottom: 15px;">
						<li><a href="#">&laquo;</a></li>
						<c:forEach items="${pagination.list}" var="index">
							
							<li <c:if test="${index.active=='1'}">class="active"</c:if>>
								<a href="#" onclick="getList(${index.value})"><c:out value="${index.value}"></c:out></a>
							</li>
						</c:forEach>
					    
					    <li><a href="#">&raquo;</a></li>
					</ul>
				</div>
			</div>

		</div>
	</div>
</body>
</html>