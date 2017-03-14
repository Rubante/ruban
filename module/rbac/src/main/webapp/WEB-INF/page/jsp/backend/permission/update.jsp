<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="permission_update_form" action='<s:url value="/rbac/permission/update" />' method="post">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						权限类型：
					</td>
					<td class="kv-content">
						<input type="text" name="type" value="${result.type}" maxlength="10">
						<input type="hidden" name="id" value="${result.id}">
						<input type="hidden" name="holdLock" value="${result.updateLock}">
					</td>
					<td class="kv-label">
						权限名：
					</td>
					<td class="kv-content">
						<input type="text" name="name" value="${result.name}" maxlength="20">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						拥有的资源：
					</td>
					<td class="kv-content">
						<div id="resourceTree" class="ztree"></div>
					</td>
					<td class="kv-label">
						说明：
					</td>
					<td class="kv-content">
						<textarea name="memo" maxlength="200">${result.memo}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>