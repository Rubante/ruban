<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
					${types[fn:trim(result.type)].value}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					编码：
				</td>
				<td class="kv-content">
					${result.code}
				</td>
				<td class="kv-label">
					图标：
				</td>
				<td class="kv-content">
					<c:if test="${not empty result.icon}">
						<img alt="未上传" src="${result.icon}"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					访问路径：
				</td>
				<td colspan="3" class="kv-content">
					${result.link}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					状态：
				</td>
				<td class="kv-content">
					${states[fn:trim(result.state)].value}
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