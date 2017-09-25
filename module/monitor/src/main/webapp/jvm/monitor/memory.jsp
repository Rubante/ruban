<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-内存监控</title>
<style type="text/css">
.panel-group{
	max-height: 100%;
}
.memory_container {
  width : 100%;
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
			url : '<s:url value="/jvm/monitor/memory.do" />',
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
		<h3>jvm图标监控</h3>
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
	
		<!-- 内存 -->
		<c:forEach items="${heapResult}" var="st" varStatus="status">
			<div class="col-md-12 col-xs-12">
				<div id="heap${status.index}" class="memory_container"></div>
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="nonHeap${status.index}" class="memory_container"></div>
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="codeCache${status.index}" class="memory_container"></div>
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="eden${status.index}" class="memory_container"></div>
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="oldGen${status.index}" class="memory_container"></div>
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="survivorSpace${status.index}" class="memory_container"></div>
			</div>
			<div class="col-md-12 col-xs-12">
				<div id="permGen${status.index}" class="memory_container"></div>
			</div>
		</c:forEach>
				
		<script type="text/javascript">
		    $(document).ready(function(){
		    	<jsp:useBean id="myDate" class="java.util.Date"/>

		    	var heapx = [];
	    		var heapy = [];
		    	// 堆内存
		    	<c:forEach items="${heapResult}" var="item" varStatus="status">
		    		<c:if test="${item.value != null}">
						<c:forEach items="${item.value}" var="bean">
				    		<c:set target="${myDate}" property="time" value="${bean.id}" />
				    		heapx.push('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${myDate}" type="both" />');
				    		heapy.push(<fmt:formatNumber value="${bean.used/(1024*1024)}" type="number" pattern="#.##" />);
						</c:forEach>
					</c:if>
					draw(heapx, heapy, '堆内存','M','heap${status.index}', 0);
				</c:forEach>
				
		    	var nonheapx = [];
	    		var nonheapy = [];
				// 非堆大小
		    	<c:forEach items="${nonHeapResult}" var="item" varStatus="status">
		    		data = [];
		    		<c:if test="${item.value != null}">
						<c:forEach items="${item.value}" var="bean">
			    			<c:set target="${myDate}" property="time" value="${bean.id}"/> 
			    			nonheapx.push('<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${myDate}" type="both"/>');
							nonheapy.push(<fmt:formatNumber value="${bean.used/(1024*1024)}" type="number" pattern="#"/>);
						</c:forEach>
					</c:if>
					draw(nonheapx, nonheapy, '非堆大小','M','nonHeap${status.index}', 0);
				</c:forEach>
				
		    	var codecachex = [];
	    		var codecachey = [];
	    		
				// 代码缓存区
		    	<c:forEach items="${codeCacheResult}" var="item" varStatus="status">
		    		data = [];
		    		<c:if test="${item.value != null}">
						<c:forEach items="${item.value}" var="bean">
		    				<c:set target="${myDate}" property="time" value="${bean.id}"/> 
		    				codecachex.push('<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${myDate}" type="both"/>');
							codecachey.push(<fmt:formatNumber value="${bean.used/(1024*1024)}" type="number" pattern="#"/>);
						</c:forEach>
					</c:if>
					draw(codecachex, codecachey, '代码缓存区','M','codeCache${status.index}', 1);
				</c:forEach>
				
		    	var edenx = [];
	    		var edeny = [];
				// 新生代
		    	<c:forEach items="${edenResult}" var="item" varStatus="status">
		    		data = [];
		    		<c:if test="${item.value != null}">
						<c:forEach items="${item.value}" var="bean">
		    				<c:set target="${myDate}" property="time" value="${bean.id}"/> 
		    				edenx.push('<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${myDate}" type="both"/>');
							edeny.push(<fmt:formatNumber value="${bean.used/(1024*1024)}" type="number" pattern="#"/>);
						</c:forEach>
					</c:if>
					draw(edenx, edeny, '新生代','M','eden${status.index}', 0);
				</c:forEach>
				
		    	var oldGenx = [];
	    		var oldGeny = [];
				// 老年代
		    	<c:forEach items="${oldGenResult}" var="item" varStatus="status">
		    		data = [];
		    		<c:if test="${item.value != null}">
						<c:forEach items="${item.value}" var="bean">
	    					<c:set target="${myDate}" property="time" value="${bean.id}"/> 
	    					oldGenx.push('<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${myDate}" type="both"/>');
							oldGeny.push(<fmt:formatNumber value="${bean.used/(1024*1024)}" type="number" pattern="#"/>);
						</c:forEach>
					</c:if>
					draw(oldGenx, oldGeny, '','M','oldGen${status.index}', 0);
				</c:forEach>
				
		    	var survivorSpacex = [];
	    		var survivorSpacey = [];
				// 新生代交换区
		    	<c:forEach items="${survivorSpaceResult}" var="item" varStatus="status">
		    		data = [];
		    		<c:if test="${item.value != null}">
						<c:forEach items="${item.value}" var="bean">
	    					<c:set target="${myDate}" property="time" value="${bean.id}"/> 
	    					survivorSpacex.push('<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${myDate}" type="both"/>');
							survivorSpacey.push(<fmt:formatNumber value="${bean.used/(1024*1024)}" type="number" pattern="#"/>);
						</c:forEach>
					</c:if>
					draw(survivorSpacex, survivorSpacey, '新生代交换区','M','survivorSpace${status.index}', 0);
				</c:forEach>
				
		    	var permGenx = [];
	    		var permGeny = [];
				// 永久代
		    	<c:forEach items="${permGenResult}" var="item" varStatus="status">
		    		data = [];
		    		<c:if test="${item.value != null}">
						<c:forEach items="${item.value}" var="bean">
		    				<c:set target="${myDate}" property="time" value="${bean.id}"/> 
		    				permGenx.push('<fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" value="${myDate}" type="both"/>');
							permGeny.push(<fmt:formatNumber value="${bean.used/(1024*1024)}" type="number" pattern="#"/>);
						</c:forEach>
					</c:if>
					draw(permGenx, permGeny, '永久代','M','permGen${status.index}', 0);
				</c:forEach>

		    });
		</script>
		<!-- Today status ends -->

	</div>
</body>
</html>