<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="add-manage">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					用户名：
				</td>
				<td class="kv-content">
					${result.username}
				</td>
				<td class="kv-label">
					显示名：
				</td>
				<td class="kv-content">
					${result.nick}
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
					拥有的角色：
				</td>
				<td class="kv-content" colspan="3">
					<c:set value="1" var="index" />
					<c:forEach items="${userRoles}" var="item">
						<c:if test="${item.role != null}">
							${index}.<span style="margin-left: 10px;">${item.role.name}</span><br />
							<c:set var="index" value="${index + 1 }"></c:set>
						</c:if>
					</c:forEach>
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