<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="department_update_form" action='<s:url value="/rbac/department/update" />' method="post">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					<i class="ruban-error-hint">名称</i>：
				</td>
				<td class="kv-content">
					<input type="text" name="name" check="notNull" value="${result.name}">
					<input type="hidden" name="id" value="${result.id}">
					<input type="hidden" name="holdLock" value="${result.updateLock}">
					<input type="hidden" name="isForm" value="1" />
				</td>
				<td class="kv-label">
					简称：
				</td>
				<td class="kv-content">
					<input type="text" name="simpleName" value="${result.simpleName}">
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					所属组织机构：
				</td>
				<td class="kv-content">
	               	<jsp:include page="/WEB-INF/page/jsp/backend/company/select_tpl.jsp"></jsp:include>
				</td>
				<td class="kv-label">
					上级部门：
				</td>
				<td class="kv-content">
	               	<jsp:include page="/WEB-INF/page/jsp/backend/department/select_tpl.jsp"></jsp:include>
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					部门编码：
				</td>
				<td class="kv-content">
					<input type="text" name="code" check="notNull" maxlength="4" value="${result.code}">
				</td>
				<td class="kv-label">
					负责人：
				</td>
				<td class="kv-content">
					<input type="text" name="personName" check="notNull" maxlength="6" value="${result.personName}">
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					备注：
				</td>
				<td class="kv-content" colspan="3">
					<div class="textarea-wrap">
						<textarea name="memo">${result.memo}</textarea>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</form>