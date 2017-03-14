<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<form id="role_grant_form" action='<s:url value="/rbac/role/grantSave" />' method="post">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						名称：
					</td>
					<td class="kv-content">
						${result.name}
						<input type="hidden" name="id" value="${result.id}">
						<input type="hidden" name="holdLock" value="${result.updateLock}">
					</td>
					<td class="kv-label">
						拥有该角色的用户数：
					</td>
					<td class="kv-content">
						${result.count}
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						可授权权限：
					</td>
					<td class="kv-content" colspan="3">
						<div class="textarea-wrap">
							<div id="permissionTree" class="ztree"></div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>