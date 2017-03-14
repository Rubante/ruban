<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'role_checkbox')"></th>
			<th>权限类型</th>
			<th>权限名</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${resultInfo.list}" var="result">
			<tr ondblclick="permission.detail('${result.id}');">
				<td class="grid-checkbox">
					<input type="checkbox" name="permission_checkbox" />
					<input type="hidden" name="permission_id" value="${result.id}"/>
				</td>
				<td>${result.type}</td>
				<td>${result.name}</td>
				<td>${stateMap[result.state].value}</td>
				<td>
					<a href="javascript:permission.detail('${result.id}');">查看</a>
					<a href="javascript:permission.update('${result.id}');">修改</a>
					<a href="javascript:permission.deleteSingle('${result.id}');">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="/WEB-INF/page/jsp/pagination.jsp">
	<jsp:param value="permission.search" name="action"/>
</jsp:include>