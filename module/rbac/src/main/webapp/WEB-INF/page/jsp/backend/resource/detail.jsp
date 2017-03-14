<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
					类型：
				</td>
				<td class="kv-content">
					${typeMap[fn:trim(result.type)].value}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					访问路径：
				</td>
				<td class="kv-content">
					${result.link}
				</td>
				<td class="kv-label">
					图标：
				</td>
				<td class="kv-content">
					${result.icon}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					是否授权：
				</td>
				<td class="kv-content">
					${yesnoMap[fn:trim(result.flag)].value}
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