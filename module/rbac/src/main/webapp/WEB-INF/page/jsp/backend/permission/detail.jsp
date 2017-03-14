<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="add-manage">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					权限类型：
				</td>
				<td class="kv-content">
					${result.type}
				</td>
				<td class="kv-label">
					权限名：
				</td>
				<td class="kv-content">
					${result.name}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					说明：
				</td>
				<td class="kv-content">
					${result.memo}
				</td>
			</tr>
		</tbody>
	</table>
</div>