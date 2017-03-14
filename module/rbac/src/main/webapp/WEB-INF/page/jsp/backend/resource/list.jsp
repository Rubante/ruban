<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox"></th>
			<th>名称</th>
			<th>类型</th>
			<th>路径</th>
			<th>图标</th>
			<th>是否可授权</th>
			<th>属性</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${resultInfo.list}" var="result">
			<tr ondblclick="resource.detail('${result.id}');">
				<td class="grid-checkbox">
					<input type="checkbox" name="resource_checkbox" />
					<input type="hidden" name="resource_id" value="${result.id}"/>
				</td>
				<td>${result.name}</td>
				<td>${typeMap[fn:trim(result.type)].value}</td>
				<td>${result.link}</td>
				<td>${result.icon}</td>
				<td>${yesnoMap[fn:trim(result.flag)].value}</td>
				<td>
					<a href="javascript:resource.fieldDetail('${result.id}');">查看</a>
					<a href="javascript:resource.fieldList('${result.id}');">管理</a>
				</td>
				<td>
					<a href="javascript:resource.detail('${result.id}');">查看</a>
					<a href="javascript:resource.update('${result.id}');">修改</a>
					<a href="javascript:resource.deleteSingle('${result.id}');">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="/WEB-INF/page/jsp/pagination.jsp">
	<jsp:param value="resource.search" name="action"/>
</jsp:include>