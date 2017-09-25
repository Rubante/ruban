<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="resource_add_form" action='<s:url value="/rbac/resource/addSave" />' method="post" enctype="multipart/form-data">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						名称：
					</td>
					<td class="kv-content">
						<input type="text" name="name" maxlength="10">
					</td>
					<td class="kv-label">
						类型：
					</td>
					<td class="kv-content">
						<select name="type">
							<c:forEach items="${types}" var="type">
								<option value="${type.code}">${type.value}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						编码：
					</td>
					<td class="kv-content">
						<input type="text" name="code" value="" maxlength="30">
					</td>
					<td class="kv-label">
						图标：
					</td>
					<td class="kv-content">
						<input type="text" name="icon" value="">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						访问路径：
					</td>
					<td colspan="3" class="kv-content">
						<input type="text" name="link" value="" maxlength="200" style="width:95%;">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						父节点：
					</td>
					<td class="kv-content" colspan="3">
						<jsp:include page="/WEB-INF/page/jsp/backend/resource/select_tpl.jsp"></jsp:include>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						备注：
					</td>
					<td class="kv-content" colspan="3">
						<div class="textarea-wrap">
							<textarea name="memo" maxlength="200"></textarea>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>