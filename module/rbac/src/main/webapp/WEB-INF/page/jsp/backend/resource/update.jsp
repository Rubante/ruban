<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="resource_update_form" action='<s:url value="/rbac/resource/updateSave" />' method="post" enctype="multipart/form-data">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						名称：
					</td>
					<td class="kv-content">
						<input type="text" name="name" value="${result.name}" maxlength="10">
						<input type="hidden" name="id" value="${result.id}">
						<input type="hidden" name="holdLock" value="${result.updateLock}">
					</td>
					<td class="kv-label">
						类型：
					</td>
					<td class="kv-content">
						<select name="type">
							<c:forEach items="${types}" var="type">
								<option value="${type.code}" <c:if test="${type.code == fn:trim(result.type) }">selected</c:if>>${type.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						访问路径：
					</td>
					<td class="kv-content">
						<input type="text" name="link" value="${result.link}" maxlength="100">
					</td>
					<td class="kv-label">
						图标：
					</td>
					<td class="kv-content">
						<input type="text" name="icon" value="${result.icon}">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						是否授权：
					</td>
					<td class="kv-content">
						<select name="flag">
							<c:forEach items="${yesnos}" var="yesno">
								<option value="${yesno.code}" <c:if test="${yesno.code == fn:trim(result.flag) }">selected</c:if>>${yesno.value}</option>
							</c:forEach>
						</select>
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