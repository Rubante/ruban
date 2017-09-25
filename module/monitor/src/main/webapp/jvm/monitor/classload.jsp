<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-类加载监控</title>
<style type="text/css">
.panel-group{
	max-height: 100%;
}
.memory_container {
  width : 95%;
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
			url : '<s:url value="/jvm/monitor/classload.do" />',
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
		<c:forEach items="${classloadResult}" var="st" varStatus="status">
			<div class="col-md-12 col-xs-12">
				<div id="classload${status.index}" class="memory_container"></div>
			</div>
		</c:forEach>
				
		<script type="text/javascript">
		//画图
		drawMulti = function(datax, totaly, loadedy, unloady, title, yTitle, id, scale){
		    var container = document.getElementById(id);

		    var myChart = echarts.init(container);
		    
			option = {
				    tooltip: {
				        trigger: 'axis',
				        position: function (pt) {
				            return [pt[0], '10%'];
				        }
				    },
				    title: {
				        text: title,
				    },
				    legend: {
				        data:['总加载数','当前加载数','已卸载数']
				    },
				    toolbox: {
				        feature: {
				            dataZoom: {
				                yAxisIndex: 'none'
				            },
				            restore: {},
				            saveAsImage: {}
				        }
				    },
				    xAxis: {
				        type: 'category',
				        axisLine: {onZero: true},
				        boundaryGap: false,
				        data: datax
				    },
				    yAxis: {
				        type: 'value',
				        boundaryGap: [0, '100%'],
				        axisLabel : {
				        	formatter : '{value}' + yTitle
				        }
				    },
				    dataZoom: [{
				        type: 'inside',
				        start: 0,
				        end: 300
				    }, {
				        start: 0,
				        end: 300,
				        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
				        handleSize: '80%',
				        handleStyle: {
				            color: '#fff',
				            shadowBlur: 3,
				            shadowColor: 'rgba(0, 0, 0, 0.6)',
				            shadowOffsetX: 2,
				            shadowOffsetY: 2
				        }
				    }],
				    series: [
				        {
				            name:'总加载数',
				            type:'line',
				            stack: '总量',
				            areaStyle: {normal: {}},
				            data:totaly
				        },
				        {
				            name:'当前加载数',
				            type:'line',
				            stack: '总量',
				            areaStyle: {normal: {}},
				            data:loadedy
				        },
				        {
				            name:'已卸载数',
				            type:'line',
				            stack: '总量',
				            areaStyle: {normal: {}},
				            data:unloady
				        }
				    ]
				};
			myChart.setOption(option);
		};
		
		    $(document).ready(function(){
		    	<jsp:useBean id="date" class="java.util.Date"/>
				// 类加载信息
		    	<c:forEach items="${classloadResult}" var="item" varStatus="status">
			    	var datax = [], totaly = [];
			    	var loadedy = [];
			    	var unloady = [];
		    		<c:if test="${item.value != null}">
						<c:forEach items="${item.value}" var="bean">
							<c:set target="${date}" property="time" value="${bean.id}" />
			    			datax.push('<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${date}" type="both"/>');
			    			
			    			totaly.push(<fmt:formatNumber value="${bean.totalLoadedClassCount}" type="number" pattern="#" />);
			    			loadedy.push(<fmt:formatNumber value="${bean.loadedClassCount}" type="number" pattern="#" />);
			    			unloady.push(<fmt:formatNumber value="${bean.unloadedClassCount}" type="number" pattern="#" />);
						</c:forEach>
					</c:if>

					drawMulti(datax, totaly, loadedy, unloady, '类加载统计','','classload${status.index}', 0);
				</c:forEach>
		    });
		</script>
		<!-- Today status ends -->

	</div>
</body>
</html>