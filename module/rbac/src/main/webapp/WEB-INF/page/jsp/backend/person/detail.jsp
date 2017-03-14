<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="add-manage">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					姓名：
				</td>
				<td class="kv-content">
					${result.name}
				</td>
				<td class="kv-label">
					编号：
				</td>
				<td class="kv-content">
					${result.code}
				</td>
				<td class="kv-label">
					所属机构：
				</td>
				<td class="kv-content">
					${result.companyName}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					所属部门：
				</td>
				<td class="kv-content">
					${result.departmentName}
				</td>
				<td class="kv-label">
					职务：
				</td>
				<td class="kv-content">
					${result.titleName}
				</td>
				<td class="kv-label">
					岗位：
				</td>
				<td class="kv-content">
					${result.jobName}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					入职时间：
				</td>
				<td class="kv-content">
					${result.entryDate}
				</td>
				<td class="kv-label">
					离职时间：
				</td>
				<td class="kv-content">
					${result.departureDate}
				</td>
				<td class="kv-label">
					薪资：
				</td>
				<td class="kv-content">
					${result.salary}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					身份证号：
				</td>
				<td class="kv-content">
					${result.cardid}
				</td>
				<td class="kv-label">
					住址：
				</td>
				<td class="kv-content" colspan="3">
					${result.address}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					邮箱：
				</td>
				<td class="kv-content">
					${result.email}
				</td>
				<td class="kv-label">
					出生日期：
				</td>
				<td class="kv-content">
					${result.birthday}
				</td>
				<td class="kv-label">
					性别：
				</td>
				<td class="kv-content">
					${result.sex}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					备注：
				</td>
				<td class="kv-content" colspan="3">
					<pre>${result.memo}</pre>
				</td>
				<td class="kv-label">
					照片：
				</td>
				<td class="kv-content" style="vertical-align:text-top;">
					<img id="person_update_form_photo_img" style="max-width: 200px;max-height: 90px;" src="data:image/png;base64,${result.photo}" />
				</td>
			</tr>
		</tbody>
	</table>
</div>