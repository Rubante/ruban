<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-连接信息配置</title>
</head>
<body>
	<div class="page-header">
		<h3>监控项配置</h3>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">stats参数</h3>
		</div>
		<div class="panel-body">
			<table class="table">
				<thead>
					<tr>
						<th><input type="checkbox" />全选</th>
						<th>监控项</th>
						<th>描述</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="checkbox" /></td>
						<td>curr_connections</td>
						<td>Lists the number of clients presently connected. Monitor that this number doesn't come too close to your max connection setting (-c).</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td>
						<td>listen_disabled_num</td>
						<td>An obscure named statistic counting the number of times memcached has hit its connection limit. When memcached hits the max connections setting, it disables its listener and new connections will wait in a queue. When someone disconnects, memcached wakes up
							the listener and starts accepting again. Each time this counter goes up, you're entering a situation where new connections will lag. Make sure it stays at or close to zero.</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td>
						<td>accepting_conns</td>
						<td>Related to the above listen_disabled_num, if you're already connected to memcached you can see if it's hit max connections or not by checking if this value is 1 or 0.</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td>
						<td>limit_maxbytes</td>
						<td>Sometimes it's nice to ensure that how you think memcached starts and how it actually starts are in line. By checking limit_maxbytes you can verify that the -m argument took. Occasionally people using init scripts can be misconfigured and memcached will
							start with default values.</td>
					</tr>
					<tr>
						<td><input type="checkbox" /></td>
						<td>cmd_flush</td>
						<td>While not exactly server health per-se, this is a good one to generically monitor. Every time someone issues a flush_all command, all items inside the cache are invalidated, and this counter is incremented. Sometimes debug code or misinformed people can
							leave scripts or callbacks running to "invalidate" the entire cache. Watch this value in production and sound the alarms if it starts moving, unless you really intended it to.</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>