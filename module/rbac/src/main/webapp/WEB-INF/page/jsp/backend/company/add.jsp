<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="addForm" action='<s:url value="/rbac/company/add" />' method="post">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					<i class="ruban-error-hint">名称</i>：
				</td>
				<td class="kv-content">
					<input type="text" name="name" check="notNull">
					<input type="hidden" name="isForm" value="1" />
				</td>
				<td class="kv-label">
					简称：
				</td>
				<td class="kv-content">
					<input type="text" name="simpleName" >
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					<i class="ruban-error-hint">类型</i>：
				</td>
				<td class="kv-content">
					<select name="type" check="notNull" id="relType">
						<option value="">请选择</option>
						<c:forEach items="${types}" var="item">
							<option value="${item.code}">${item.value}</option>
						</c:forEach>
					</select>
				</td>
				<td class="kv-label">
					上级机构：
				</td>
				<td class="kv-content">
	               	<jsp:include page="/WEB-INF/page/jsp/backend/company/select_tpl.jsp"></jsp:include>
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					抬头：
				</td>
				<td class="kv-content">
					<input type="text" name="title" >
				</td>
				<td class="kv-label">
					编码：
				</td>
				<td class="kv-content">
					<input type="text" name="code" check="notNull" maxlength="6">
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					电话：
				</td>
				<td class="kv-content">
					<input type="text" name="tel" maxlength="30">
				</td>
				<td class="kv-label">
					邮箱：
				</td>
				<td class="kv-content">
					<input type="text" name="email" check="email" maxlength="20">
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					地址：
				</td>
				<td class="kv-content">
					<input type="text" name="address" />
				</td>
				<td class="kv-label">
					邮编：
				</td>
				<td class="kv-content">
					<input type="text" name="postCode" check="number,length=6" maxlength="6">
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					备注：
				</td>
				<td class="kv-content" colspan="3">
					<div class="textarea-wrap">
						<textarea name="memo"></textarea>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</form>
