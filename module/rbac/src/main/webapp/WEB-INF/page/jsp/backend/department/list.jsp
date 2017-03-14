<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'department_checkbox')"></th>
			<th>序号</th>
			<th>部门名称</th>
			<th>上级部门</th>
			<th>所属组织机构</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="result">
			<tr ondblclick="department.detail('${result.id}');">
				<td class="grid-checkbox">
					<input type="checkbox" name="department_checkbox" />
					<input type="hidden" name="department_id" value="${result.id}"/>
				</td>
				<td>${result.orderCode}</td>
				<td>${result.name}</td>
				<td>${result.departmentName}</td>
				<td>${result.companyName}</td>
				<td>
					<a href="javascript:department.detail('${result.id}');">查看</a>
					<a href="javascript:department.update('${result.id}');">修改</a>
					<a href="javascript:department.deleteSingle('${result.id}');">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>