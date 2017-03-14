<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="role_add_form" action='<s:url value="/rbac/role/add" />' method="post">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						名称：
					</td>
					<td class="kv-content">
						<input type="text" name="name" maxlength="20">
						<input type="hidden" name="isForm" value="1" />
					</td>
					<td class="kv-label">
						类型：
					</td>
					<td class="kv-content">
						<input type="text" name="type" maxlength="10">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						是否可委托：
					</td>
					<td class="kv-content">
						<input type="text" name="delegated" maxlength="15">
					</td>
					<td class="kv-label">
						所属机构：
					</td>
					<td class="kv-content">
						<jsp:include page="/WEB-INF/page/jsp/backend/company/select_tpl.jsp"></jsp:include>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						备注：
					</td>
					<td class="kv-content" colspan="3">
						<div class="textarea-wrap">
							<textarea name="memo" check="maxlength=200"></textarea>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>