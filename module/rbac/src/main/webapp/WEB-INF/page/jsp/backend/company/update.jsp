<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<form id="updateForm" action='<s:url value="/rbac/company/update" />' method="post">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					<i class="ruban-error-hint">名称</i>：
				</td>
				<td class="kv-content" style="padding: 10px; width:auto;">
					<input type="text" name="name" value="${result.name}" check="notNull">
					<input type="hidden" name="id" value="${result.id}"/>
					<input type="hidden" name="holdLock" value="${result.updateLock}"/>
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
					<i class="ruban-error-hint">类型</i>：
				</td>
				<td class="kv-content" style="padding: 10px; width:auto;">
					${typeMap[fn:trim(result.type)].value}
					<input type="hidden" value="${result.type}"  id="relType">
				</td>
				<td class="kv-label">
					上级机构：
				</td>
				<td class="kv-content" style="padding: 10px;width:auto;">
	               	<jsp:include page="/WEB-INF/page/jsp/backend/company/select_tpl.jsp"></jsp:include>
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					抬头：
				</td>
				<td class="kv-content">
					<input type="text" name="title" value="${result.title}">
				</td>
				<td class="kv-label">
					编码：
				</td>
				<td class="kv-content">
					<input type="text" name="code" value="${result.code}" check="notNull" maxlength="6">
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					电话：
				</td>
				<td class="kv-content">
					<input type="text" name="tel" value="${result.tel}" maxlength="30">
				</td>
				<td class="kv-label">
					邮箱：
				</td>
				<td class="kv-content">
					<input type="text" name="email" value="${result.email}" check="email" maxlength="20">
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					地址：
				</td>
				<td class="kv-content">
					<input type="text" name="address" value="${result.address} "/>
				</td>
				<td class="kv-label">
					邮编：
				</td>
				<td class="kv-content">
					<input type="text" name="postCode" value="${result.postCode}" check="number,length=6" maxlength="6">
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