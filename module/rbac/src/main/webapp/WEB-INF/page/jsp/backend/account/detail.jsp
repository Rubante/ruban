<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="add-manage">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					账号：
				</td>
				<td class="kv-content">
					${result.accountNo}
				</td>
				<td class="kv-label">
					昵称：
				</td>
				<td class="kv-content">
					${result.name}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					最后登录时间：
				</td>
				<td class="kv-content">
					${result.lastLoginTime}
				</td>
				<td class="kv-label">
					所属人员：
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
					<pre>${result.memo}</pre>
				</td>
			</tr>
		</tbody>
	</table>
</div>