<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="add-manage">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					名称：
				</td>
				<td class="kv-content">
					${result.name}
				</td>
				<td class="kv-label">
					是否可委托：
				</td>
				<td class="kv-content">
					${result.delegated}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					所属机构：
				</td>
				<td class="kv-content">
					${result.companyName}
				</td>
				<td class="kv-label">
					更新时间：
				</td>
				<td class="kv-content">
					<fmt:formatDate value="${result.modTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
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