<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table class="grid">
	<thead>
		<tr>
			<th>字段名</th>
			<th>字段值</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${list}" var="result">
			<tr>
				<td>${result.code}</td>
				<td>${result.name}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>