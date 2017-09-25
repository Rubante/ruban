<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'person_checkbox')" style="margin-right:9px;"></th>
			<th>姓名</th>
			<th>姓别</th>
			<th>职务</th>
			<th>岗位</th>
			<th>所属组织机构</th>
			<th>所属部门</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${resultInfo.list}" var="result">
			<tr ondblclick="person.detail('${result.id}');">
				<td class="grid-checkbox">
					<input type="checkbox" name="person_checkbox" />
					<input type="hidden" name="person_id" value="${result.id}"/>
				</td>
				<td>${result.name}</td>
				<td>${sexMap[fn:trim(result.sex)].value}</td>
				<td>${result.titleName}</td>
				<td>${result.jobName}</td>
				<td>${result.companyName}</td>
				<td>${result.departmentName}</td>
				<td>
					<a href="javascript:person.detail('${result.id}');">查看</a>
					<a href="javascript:person.update('${result.id}');">修改</a>
					<a href="javascript:person.deleteSingle('${result.id}');">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="/WEB-INF/page/jsp/pagination.jsp">
	<jsp:param value="person.search" name="action"/>
</jsp:include>