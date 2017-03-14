<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="permission_add_form" action='<s:url value="/rbac/permission/add" />' method="post">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						权限类型：
					</td>
					<td class="kv-content">
						<input type="text" name="type" maxlength="20">
					</td>
					<td class="kv-label">
						权限名称：
					</td>
					<td class="kv-content">
						<input type="text" name="name" maxlength="20">
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
						<textarea name="memo" check="maxlength=200"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>