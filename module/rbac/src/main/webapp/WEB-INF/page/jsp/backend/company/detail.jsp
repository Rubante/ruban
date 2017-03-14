<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table class="kv-table">
	<tbody>
		<tr>
			<td class="kv-label">
				<i>名称</i>：
			</td>
			<td class="kv-content">
				${company.name}
			</td>
			<td class="kv-label">
				简称：
			</td>
			<td class="kv-content" colspan="3">
				${company.simpleName}
			</td>
		</tr>
		<tr>
			<td class="kv-label">
				<i>类型</i>：
			</td>
			<td class="kv-content">
				${dictMap[company.type].value}
			</td>
			<td class="kv-label">
				上级机构：
			</td>
			<td class="kv-content">
               	${company.companyName}
			</td>
		</tr>
		<tr>
			<td class="kv-label">
				抬头：
			</td>
			<td class="kv-content">
				${company.title}
			</td>
			<td class="kv-label">
				编码：
			</td>
			<td class="kv-content">
				${company.code}
			</td>
		</tr>
		<tr>
			<td class="kv-label">
				地址：
			</td>
			<td class="kv-content">
				${company.address}
			</td>
			<td class="kv-label">
				邮编：
			</td>
			<td class="kv-content">
				${company.postCode}
			</td>
		</tr>
		<tr>
			<td class="kv-label">
				电话：
			</td>
			<td class="kv-content">
				${company.tel}
			</td>
			<td class="kv-label">
				邮箱：
			</td>
			<td class="kv-content">
				${company.email}
			</td>
		</tr>
		<tr>
			<td class="kv-label">
				备注：
			</td>
			<td class="kv-content" colspan="3">
				<pre>${company.memo}</pre>
			</td>
		</tr>
		<tr>
		</tr>
	</tbody>
</table>