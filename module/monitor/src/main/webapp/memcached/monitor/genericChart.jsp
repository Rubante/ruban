<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-图标监控</title>
<style type="text/css">
.panel-group{
	max-height: 100%;
}
.access_container {

  height: 384px;
  margin: 8px auto;
}
.memory_title {
	text-align: center;
	font-weight: bold;
}
</style>
</head>
<script type="text/javascript">
	refresh = function(){
		
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var data = {
			"startTime" : startTime,
			"endTime" : endTime
		};
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/memcached/monitor/chart.do" />',
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
		<h3>memcached图标监控</h3>
	</div>
	<div class="container-fluid">

		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">查询条件</h3>
			</div>
			<div class="panel-body">
				<label>开始时间</label>
				<input type="text" name="startTime" value="${startTime}" id="startTime" readonly="readonly" onclick="selectTime(this)"/>
				<label>结束时间</label>
				<input type="text" name="endTime" value="${endTime}" id="endTime" readonly="readonly" onclick="selectTime(this)"/>
				<button type="button" class="btn btn-primary" onclick="refresh()">查询</button>
			</div>
		</div>
	</div>
	
	<div class="container-fluid">

		<c:forEach items="${result}" var="st" varStatus="status">
			<div class="col-md-12 col-xs-12 memory_title text-success">
				CPU使用率
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="cpu${status.index}" class="access_container"></div>
			</div>
			<div class="col-md-12 col-xs-12 memory_title text-success">
				命中率
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="hit${status.index}" class="access_container"></div>
			</div>
			<div class="col-md-12 col-xs-12 memory_title text-success">
				读取吞吐量
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="read${status.index}" class="access_container"></div>
			</div>
			<div class="col-md-12 col-xs-12 memory_title text-success">
				写入吞吐量
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="written${status.index}" class="access_container"></div>
			</div>
		</c:forEach>
				
		<script type="text/javascript">
			<jsp:useBean id="myDate" class="java.util.Date"/>
		
		    $(document).ready(function(){
				// CPU使用率
		    	<c:forEach items="${result}" var="item" varStatus="status">
					var datax = [];
					var datay = [];
					<c:forEach items="${item.value}" var="score">
						<c:if test="${score.value['cpuRate'] !=null}">
				    		<c:set target="${myDate}" property="time" value="${score.key}" />
				    		datax.push('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${myDate}" type="both" />');
				    		datay.push("${score.value['cpuRate']}");
						</c:if>
					</c:forEach>
					
					draw(datax, datay, 'CPU使用率','%','cpu${status.index}', 2);
				</c:forEach>
				
				// 命中率
				<c:forEach items="${result}" var="item" varStatus="status">
					var datax = [];
					var datay = [];
					<c:forEach items="${item.value}" var="score">
			    		<c:set target="${myDate}" property="time" value="${score.key}" />
				    	datax.push('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${myDate}" type="both" />');
				    	datay.push("${score.value['hitRate']}");
					</c:forEach>
					
					draw(datax, datay, '命中率','%','hit${status.index}', 2);
				</c:forEach>
				
				// 吞吐量
				<c:forEach items="${result}" var="item" varStatus="status">
					var datax = [];
					var datay = [];
					<c:forEach items="${item.value}" var="score">
			    		<c:set target="${myDate}" property="time" value="${score.key}" />
					    datax.push('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${myDate}" type="both" />');
					    datay.push("${score.value['readRate']}");
					</c:forEach>
					
					draw(datax, datay, '读取速率','B/s','read${status.index}', 0);
				</c:forEach>
				
				// 吞吐量
				<c:forEach items="${result}" var="item" varStatus="status">
					var datax = [];
					var datay = [];
					<c:forEach items="${item.value}" var="score">
			    		<c:set target="${myDate}" property="time" value="${score.key}" />
						datax.push('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${myDate}" type="both" />');
						datay.push("${score.value['writtenRate']}");
					</c:forEach>
					
					draw(datax, datay, '写入速率','B/s','written${status.index}', 0);
				</c:forEach>
		    });
		</script>
		<!-- Today status ends -->

	</div>
</body>
</html>