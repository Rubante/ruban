<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="resourceField_update_form" action='<s:url value="/rbac/resourceField/updateSave" />' method="post" enctype="multipart/form-data">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						字段值：
					</td>
					<td class="kv-content">
						<input type="text" name="code" maxlength="10" value="${result.code}">
						<input type="hidden" name="id" value="${result.id}">
						<input type="hidden" name="resourceId" id="resourceId" value=""/>
						<input type="hidden" name="holdLock" value="${result.updateLock}">
					</td>
					<td class="kv-label">
						字段名：
					</td>
					<td class="kv-content">
						<input type="text" name="name" maxlength="10" value="${result.name}">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						是否授权：
					</td>
					<td class="kv-content">
						<select name="type">
							<c:forEach items="${yesnos}" var="yesno">
								<option value="${yesno.code}" <c:if test="${yesno.code == fn:trim(result.flag) }">selected</c:if>>${yesno.value}</option>
							</c:forEach>
						</select>
					</td>
					<td class="kv-label">
						备注：
					</td>
					<td class="kv-content">
						<textarea rows="7" cols="25" maxlength="200" name="memo">${result.memo}</textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>