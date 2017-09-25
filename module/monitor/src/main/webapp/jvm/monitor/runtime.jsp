<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
	jvmRumtime = function(){
		var host = $("#server").val();
		
		var data = {
			"host" : host
		};
			
		$.ajax({
			type : 'POST',
			url : '<s:url value="/jvm/monitor/runtime.do" />',
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
				<select name="server" class="form-control" style="width:200px;display:inline;margin-right:15px;margin-left:15px;">
					<option>请选择IP</option>
					<c:forEach var="item" items="${servers}">
						<option value="${item.value.host}" <c:if test="${host eq item.value.host}">selected</c:if>>
							${item.value.host}
						</option>
					</c:forEach>
				</select>
				<button type="button" class="btn btn-primary" onclick="jvmRumtime()">查询</button>
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
										<td >name</td>
										<td>
											${os.value.name}
										</td>
									</tr>
									<tr>
										<td >vmName</td>
										<td>
											${os.value.vmName}
										</td>
									</tr>
									<tr>
										<td >VmVendor</td>
										<td>
											${os.value.vmVendor}
										</td>
									</tr>
									<tr>
										<td >VmVersion</td>
										<td>
											${os.value.vmVersion}
										</td>
									</tr>
									<tr>
										<td >SpecName</td>
										<td>
											${os.value.specName}
										</td>
									</tr>
									<tr>
										<td >SpecVendor</td>
										<td>
											${os.value.specVendor}
										</td>
									</tr>
									<tr>
										<td >SpecVersion</td>
										<td>
											${os.value.specVersion}
										</td>
									</tr>
									<tr>
										<td >ManagementSpecVersion</td>
										<td>
											${os.value.managementSpecVersion}
										</td>
									</tr>
									<tr>
										<td >ClassPath</td>
										<td>
											<c:set value="${fn:split(os.value.classPath, ';') }" var="classPath" />
											
											<c:forEach items="${classPath}" var="path">
												${path}<br/>
											</c:forEach>
										</td>
									</tr>							
									<tr>
										<td >LibraryPath</td>
										<td>
											<c:set value="${fn:split(os.value.libraryPath, ';') }" var="libraryPath" />
											
											<c:forEach items="${libraryPath}" var="path">
												${path}<br/>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<td >BootClassPathSupported</td>
										<td>
											${os.value.bootClassPathSupported}
										</td>
									</tr>
									<tr>
										<td >BootClassPath</td>
										<td>
											<c:set value="${fn:split(os.value.bootClassPath, ';') }" var="bootClassPath" />
											
											<c:forEach items="${bootClassPath}" var="path">
												${path}<br/>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<td >InputArguments</td>
										<td>
											<c:forEach items="${os.value.inputArguments}" var="path">
												${path}<br/>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<td >Uptime</td>
										<td>
											${os.value.uptime}
										</td>
									</tr>
									<tr>
										<td >StartTime</td>
										<td>
											${os.value.startTime}
										</td>
									</tr>
									<tr>
										<td >SystemProperties</td>
										<td>
											<table class="table">
												<tr>
													<th>键</th>
													<th>值</th>
												</tr>
												<c:forEach items="${os.value.systemProperties}" var="prop">
													<tr>
														<c:choose>
															<c:when test="${prop.key eq 'java.class.path'}">
																
															</c:when>
															<c:when test="${prop.key eq 'sun.boot.class.path'}">
																
															</c:when>
															<c:when test="${prop.key eq 'java.library.path'}">
															
															</c:when>
															<c:otherwise>
																<td>${prop.key}</td>
																<td>
																	${prop.value}
																</td>
															</c:otherwise>
														</c:choose>
													</tr>
												</c:forEach>
											</table>
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