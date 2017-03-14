<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'role_checkbox')"></th>
			<th>名称</th>
			<th>角色类型</th>
			<th>是否可授权</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${resultInfo.list}" var="result">
			<tr ondblclick="role.detail('${result.id}');">
				<td class="grid-checkbox">
					<input type="checkbox" name="role_checkbox" />
					<input type="hidden" name="role_id" value="${result.id}"/>
				</td>
				<td>${result.name}</td>
				<td>${result.type}</td>
				<td>${result.delegated}</td>
				<td>${stateMap[result.state].value}</td>
				<td>
					<a href="javascript:rubanRole.detail('${result.id}');">查看</a>
					<a href="javascript:rubanRole.update('${result.id}');">修改</a>
					<a href="javascript:rubanRole.deleteSingle('${result.id}');">删除</a>
					<a href="javascript:rubanRole.deleteSingle('${result.id}');">停用</a>
					<a href="javascript:rubanRole.deleteSingle('${result.id}');">禁用</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="/WEB-INF/page/jsp/pagination.jsp">
	<jsp:param value="rubanRole.search" name="action"/>
</jsp:include>