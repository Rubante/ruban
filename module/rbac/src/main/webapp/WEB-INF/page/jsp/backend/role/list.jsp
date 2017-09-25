<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'role_checkbox')" style="margin-right:9px;"></th>
			<th>名称</th>
			<th>是否可委托</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${resultInfo.list}" var="result">
			<tr>
				<td class="grid-checkbox">
					<input type="checkbox" name="role_checkbox" />
					<input type="hidden" name="role_id" value="${result.id}"/>
				</td>
				<td>${result.name}</td>
				<td>${yesnoMap[fn:trim(result.delegated)].value}</td>
				<td>${stateMap[fn:trim(result.state)].value}</td>
				<td>
					<a href="javascript:rubanRole.detail('${result.id}');">查看</a>
					<a href="javascript:rubanRole.update('${result.id}');">修改</a>
					<c:choose>
						<c:when test="${result.state == 1}">
							<a href="javascript:rubanRole.unable('${result.id}');" style="color:red">禁用</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:rubanRole.enable('${result.id}');" style="color:green">启用</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${result.state == 0}">
						<a href="javascript:rubanRole.deleteSingle('${result.id}');">删除</a>
					</c:if>
					
					<a href="javascript:rubanRole.grantPage('${result.id}');">授权</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="/WEB-INF/page/jsp/pagination.jsp">
	<jsp:param value="rubanRole.search" name="action"/>
</jsp:include>