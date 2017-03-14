<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'company_checkbox')"></th>
			<th>序号</th>
			<th>名称</th>
			<th>类型</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="result">
			<tr ondblclick="company.detail('${result.id}');">
				<td class="grid-checkbox">
					<input type="checkbox" name="company_checkbox" />
					<input type="hidden" name="company_id" value="${result.id}"/>
				</td>
				<td>${result.orderCode}</td>
				<td>${result.name}</td>
				<td>${dictMap[result.type].value}</td>
				<td>
					<a href="javascript:company.detail('${result.id}');">查看</a>
					<a href="javascript:company.update('${result.id}');">修改</a>
					<a href="javascript:company.deleteSingle('${result.id}');">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>