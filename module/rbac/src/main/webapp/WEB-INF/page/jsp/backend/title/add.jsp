<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="person_add_form" action='<s:url value="/rbac/person/add" />' method="post" enctype="multipart/form-data">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						姓名：
					</td>
					<td class="kv-content">
						<input type="text" name="name" maxlength="10">
						<input type="hidden" name="isForm" value="1" />
					</td>
					<td class="kv-label">
						编号：
					</td>
					<td class="kv-content">
						<input type="text" name="code" maxlength="10">
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
						所属部门：
					</td>
					<td class="kv-content">
						<jsp:include page="/WEB-INF/page/jsp/backend/department/select_tpl.jsp"></jsp:include>
					</td>
					<td class="kv-label">
						职务：
					</td>
					<td class="kv-content">
						<select name="titleId">
							<option value="0">经理</option>
							<option value="1">主管</option>
						</select>
					</td>
					<td class="kv-label">
						岗位：
					</td>
					<td class="kv-content">
						<select name="jobId" class="btn-select">
							<option value="0">java开发者</option>
							<option value="1">linux工程师</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						入职时间：
					</td>
					<td class="kv-content">
						<input type="text" name="entryDate" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						离职时间：
					</td>
					<td class="kv-content">
						<input type="text" name="departureDate" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						薪资：
					</td>
					<td class="kv-content">
						<input type="text" name="salary">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						身份证号：
					</td>
					<td class="kv-content">
						<input type="text" name="cardid" maxlength="18">
					</td>
					<td class="kv-label">
						住址：
					</td>
					<td class="kv-content" colspan="3">
						<input type="text" name="address" style="width:95%;" maxlength="60">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						邮箱：
					</td>
					<td class="kv-content">
						<input type="text" name="email" maxlength="20">
					</td>
					<td class="kv-label">
						出生日期：
					</td>
					<td class="kv-content">
						<input type="text" name="birthday" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						性别：
					</td>
					<td class="kv-content">
						<select name="sex">
							<c:forEach items="${dicts}" var="result">
								<option value="${result.code}">${result.value}</option>
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
							<textarea name="memo" check="maxlength=200"></textarea>
						</div>
					</td>
					<td class="kv-label">
						照片：
					</td>
					<td class="kv-content">
						<a href="javascript:" class="file">
							<span><em>+</em>添加图片</span>
							<input id="person_add_form_photo" type="file" name="photo">
						</a>
						<img id="person_add_form_photo_img" height="90px" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>