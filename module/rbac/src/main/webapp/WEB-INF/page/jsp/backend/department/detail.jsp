<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="kv-table">
	<tbody>
		<tr>
			<td class="kv-label">
				<i class="ruban-error-hint">名称</i>：
			</td>
			<td class="kv-content">
				${result.name}
			</td>
			<td class="kv-label">
				简称：
			</td>
			<td class="kv-content">
				${result.simpleName}
			</td>
		</tr>
		<tr>
			<td class="kv-label">
				所属组织机构：
			</td>
			<td class="kv-content" style="height: 33px;white-space: nowrap;">
               	${result.companyName}
			</td>
			<td class="kv-label">
				上级部门：
			</td>
			<td class="kv-content" style="height: 33px;white-space: nowrap;">
               	${result.departmentName}
			</td>
		</tr>
		<tr>
			<td class="kv-label">
				部门编码：
			</td>
			<td class="kv-content">
				${result.code}
			</td>
			<td class="kv-label">
				负责人：
			</td>
			<td class="kv-content">
				${result.personName}
			</td>
		</tr>
		<tr>
			<td class="kv-label">
				备注：
			</td>
			<td class="kv-content" colspan="3">
				<div class="textarea-wrap">
					<pre>${result.memo}</pre>
				</div>
			</td>
		</tr>
	</tbody>
</table>