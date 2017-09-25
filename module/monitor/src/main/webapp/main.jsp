<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/include.jsp"></jsp:include>
<style type="text/css">
.dashboard ul>li {
	width: 23%;
    height: 200px;
    display: block;
    margin-right: 15px;
    border-radius: 5px;
    border: 1px solid #dddddd;
    float: left;
    text-align: center;
    margin-top: 12px;
    padding-top: 12px;
}
.monitor-icon{
	height: 96px;
}
.monitor-info{
	width: 100%;
}
.monitor-item {
	font-weight: bold;
}
@media(max-width:480px){  
	.dashboard ul>li {
		width: 100%;
		height: 100px
	}
	.monitor-icon{
		margin-left: 15px;
		height: 64px;
		float: left;
	}
	.monitor-info{
		float: right;
		width: 40%;
	}
</style>
</head>
<body>
	<div class="container-fluid" style="border-bottom: 1px solid #dddddd;">
		<div class="col-lg-10 col-xs-10">
			<div style="font-size:1.4em;margin-top:16px;display: inline-block; margin-left: -5px;cursor:pointer;" onclick="home();">
				<span class="glyphicon glyphicon-home"></span>
			</div>
			<div style="font-size:1.4em;line-height:50px;display: inline-block;">鲁班监控平台</div>
		</div>
		<div class="col-lg-2 col-xs-2 phone">
			<div style="font-size:1.4em;margin-top:15px;display: inline-block;float: right;" onclick="showMenu();">
				<span class="glyphicon glyphicon-align-justify"></span>
			</div>
		</div>
		<div class="col-lg-2 col-xs-2 pc">
			<div title="退出" style="font-size:1.4em;margin-top:15px;display:inline-block;float: right; cursor: pointer;" onclick="logout();">
				<span class="glyphicon glyphicon-off text-danger"></span>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div id="menu" class="col-md-2 col-xs-12 col-xs-12 pc">
				<div class="panel-group" role="tablist">
					<div class="panel leftMenu">
						<div class="panel-heading" id="monitorGroupHeading" data-toggle="collapse" data-target="#baseGroup" role="tab">
							<h4 class="panel-title">
								基础配置 <span class="glyphicon glyphicon-chevron-down right"></span>
							</h4>
						</div>
						<div id="baseGroup" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="monitorGroupHeading">
							<ul class="list-group">
								<li class="list-group-item">
									<div class="menu-item-list">
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/server/index.do" />')" class="list-group-item">存储设置</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/user/main.do" />')" class="list-group-item">用户管理</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/warn/index.do" />')" class="list-group-item">报警设置</a>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="panel leftMenu">
						<!-- 利用data-target指定要折叠的分组列表 -->
						<div class="panel-heading" id="memcachedGroupHeading" data-toggle="collapse" data-target="#memcachedGroup" role="tab">
							<h4 class="panel-title">
								memcached监控 <span class="glyphicon glyphicon-chevron-up right"></span>
							</h4>
						</div>
						<!-- .panel-collapse和.collapse标明折叠元素 .in表示要显示出来 -->
						<div id="memcachedGroup" class="panel-collapse collapse out" role="tabpanel" aria-labelledby="memcachedGroupHeading">
							<ul class="list-group">
								<li class="list-group-item">
									<!-- 利用data-target指定URL -->
									<div class="menu-item-left" data-target="#memcachedSetting" data-toggle="collapse" >
										<span class="glyphicon glyphicon-triangle-right"></span>参数设置
									</div>
									<div id="memcachedSetting" class="panel-collapse collapse out menu-item-list">
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/config/base.do" />')" class="list-group-item">基础配置</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/config/warn.do" />')" class="list-group-item">报警设置</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/config/serializable/page.do" />')" class="list-group-item">序列化配置</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/config/server.do" />')" class="list-group-item">监控目标</a>
									</div>
								</li>
								<li class="list-group-item">
									<div class="menu-item-left" data-target="#memcachedStats" data-toggle="collapse" >
										<span class="glyphicon glyphicon-triangle-right"></span>监控信息
									</div>
									<div id="memcachedStats" class="panel-collapse collapse out menu-item-list">
										<a href="javascript:void(0)" class="list-group-item" onclick="loadContent('<s:url value="/memcached/monitor/stats.do" />')" >状态信息</a>
										<a href="javascript:void(0)" class="list-group-item" onclick="loadContent('<s:url value="/memcached/monitor/setting.do" />')" >配置信息</a>
										<a href="javascript:void(0)" class="list-group-item" onclick="loadContent('<s:url value="/memcached/monitor/size.do" />')"><span class="text-warning">数量统计</span></a>
										<a href="javascript:void(0)" class="list-group-item" onclick="loadContent('<s:url value="/memcached/monitor/items.do" />')">分布统计</a>
										<a href="javascript:void(0)" class="list-group-item" onclick="loadContent('<s:url value="/memcached/monitor/chart.do" />')">监控图标</a>
									</div>
								</li>
								<li class="list-group-item">
									<div class="menu-item-left" data-target="#memcachedOperate" data-toggle="collapse" >
										<span class="glyphicon glyphicon-triangle-right"></span>数据操作
									</div>
									<div id="memcachedOperate" class="panel-collapse collapse out menu-item-list">
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/opt/data/list.do" />')" class="list-group-item">对象列表</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/opt/read/page.do" />')" class="list-group-item">读取数据</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/opt/touch/page.do" />')" class="list-group-item">延长失效</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/opt/write/page.do" />')" class="list-group-item">写入数据</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/memcached/opt/flush/page.do" />')" class="list-group-item">重置缓存</a>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="panel leftMenu">
						<div class="panel-heading" id="jvmGroupHeading" data-toggle="collapse" data-target="#jvmGroup" role="tab">
							<h4 class="panel-title">
								JVM监控 <span class="glyphicon glyphicon-chevron-down right"></span>
							</h4>
						</div>
						<div id="jvmGroup" class="panel-collapse collapse" role="tabpanel" aria-labelledby="jvmGroupHeading">
							<ul class="list-group">
								<li class="list-group-item" onclick="loadContent('<s:url value="/jvm/config/base.do" />')" >
									<div class="menu-item-list">
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/jvm/config/base.do" />')" class="list-group-item">监控目标</a>
									</div>
								</li>
								<li class="list-group-item">
									<div class="menu-item-left" data-target="#jvmMonitor" data-toggle="collapse" >
										<span class="glyphicon glyphicon-triangle-right"></span>监控信息
									</div>
									<div id="jvmMonitor" class="panel-collapse collapse out menu-item-list">
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/jvm/monitor/os.do" />')" class="list-group-item">系统信息</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/jvm/monitor/runtime.do" />')" class="list-group-item">运行环境</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/jvm/monitor/memory.do" />')" class="list-group-item">JVM内存</a>
										<a href="javascript:void(0)" onclick="loadContent('<s:url value="/jvm/monitor/classload.do" />')" class="list-group-item">类加载信息</a>
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="panel leftMenu">
						<div class="panel-heading" id="collapseListGroupHeading2" data-toggle="collapse" data-target="#redisGroup" role="tab">
							<h4 class="panel-title">
								Redis监控 <span class="glyphicon glyphicon-chevron-down right"></span>
							</h4>
						</div>
						<div id="redisGroup" class="panel-collapse collapse" role="tabpanel" aria-labelledby="redisGroupHeading">
							<ul class="list-group">
								<li class="list-group-item">
									<div class="menu-item-left">
										<span class="glyphicon glyphicon-triangle-right"></span>分组项2-1
									</div>
								</li>
								<li class="list-group-item">
									<div class="menu-item-left">
										<span class="glyphicon glyphicon-triangle-right"></span>分组项2-2
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="panel leftMenu phone">
						<div class="panel-heading" onclick="logout();" style="background-color: red; color:white;">
							<h4 class="panel-title">
								 退出<span class="glyphicon glyphicon-off"></span>
							</h4>
						</div>
					</div>
				</div>
			
			</div>
			<div class="content col-md-10 col-sm-12 col-xs-12" id="content">
				<div class="dashboard">
					<ul class="container-fluid">
						<li>
							<img class="monitor-icon" src='<s:url value="/static/images/cache.png" />' />
							<div class="monitor-info">
								<div class="monitor-item" style="color:gray;font-weight:bold;">总数量：16</div>
								<div class="monitor-item" style="color:green;font-weight:bold;">正常数量：11</div>
								<div class="monitor-item" style="color:red;font-weight:bold;">错误：1</div>
							</div>
						</li>
						<li>
							<img class="monitor-icon" src='<s:url value="/static/images/java.png" />' />
							<div class="monitor-info">
								<div class="monitor-item" style="color:gray;">总数量：12</div>
								<div class="monitor-item" style="color:green;">正常数量：11</div>
								<div class="monitor-item" style="color:red;">错误：1</div>
							</div>
						</li>
						<li>
							<img class="monitor-icon" src='<s:url value="/static/images/server.png" />' />
							<div class="monitor-info">
								<div class="monitor-item" style="color:gray;">总数量：12</div>
								<div class="monitor-item" style="color:green;">正常数量：11</div>
								<div class="monitor-item" style="color:red;">错误：1</div>
							</div>
						</li>
						<li>
							<img class="monitor-icon" src='<s:url value="/static/images/redis.png" />' />
							<div class="monitor-info">
								<div class="monitor-item" style="color:gray;">总数量：12</div>
								<div class="monitor-item" style="color:green;">正常数量：11</div>
								<div class="monitor-item" style="color:red;">错误：1</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
		<script>
			$(function() {
				$(".panel-heading").click(function(e) {
					/*切换折叠指示图标*/
					$(this).find("span").toggleClass("glyphicon-chevron-down");
					$(this).find("span").toggleClass("glyphicon-chevron-up");
				});
			});
			
			loadContent = function(url) {
				var index = layer.load(2, {time: 10*1000,shade: [0.3, '#393D49']}); //又换了种风格，并且设定最长等待10秒 
				$("#content").load(url, function(){
					//关闭
					layer.close(index);
				});
			};
			// 主页
			home = function() {
				window.location.href='<s:url value="/main.do" />';
			};
			
			// 退出系统
			logout = function() {
				window.location.href='<s:url value="/logout.do" />';
			};
			
			showMenu = function(){
				$("#menu").toggle();
			};
			// 选择日期
			selectDate = function(elem){
			    $.jeDate(elem,{
			    	skinCell:"jedategreen",
			        insTrigger:false,
			        zIndex: 200010000,
			        format: 'YYYY-MM-DD'
			    });
			};
			//选择日期
			selectTime = function(elem){
			    $.jeDate(elem,{
			    	skinCell:"jedategreen",
			    	insTrigger:false,
			        zIndex: 200010000,
			        format: 'YYYY-MM-DD hh:mm:ss'
			    });
			};
		</script>
	</body>
</html>