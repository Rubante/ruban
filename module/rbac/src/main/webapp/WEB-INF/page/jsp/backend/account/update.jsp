<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="account_update_form" action='<s:url value="/rbac/person/updateForm" />' method="post" enctype="multipart/form-data">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						账号：
					</td>
					<td class="kv-content">
						<input type="text" name="accountNo" value="${result.accountNo}" maxlength="10">
						<input type="hidden" name="id" value="${result.id}">
						<input type="hidden" name="holdLock" value="${result.updateLock}">
						<input type="hidden" name="isForm" value="1" />
					</td>
					<td class="kv-label">
						昵称：
					</td>
					<td class="kv-content">
						<input type="text" name="name" value="${result.name}" maxlength="10">
					</td>
				</tr>
	
				<tr>
					<td class="kv-label">
						密码：
					</td>
					<td class="kv-content">
						<input type="password" name="password" value="${result.password}" maxlength="15">
					</td>
					<td class="kv-label">
						所属人员：
					</td>
					<td class="kv-content">
						
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						备注：
					</td>
					<td class="kv-content" colspan="3">
						<div class="textarea-wrap">
							<textarea name="memo" maxlength="200">${result.memo}</textarea>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>