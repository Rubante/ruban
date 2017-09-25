<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="table">
	<tr>
		<td>类型</td>
		<td><c:out value="${type}"></c:out></td>
	</tr>
	<tr>
		<td>数据</td>
		<td><c:out value="${data}"></c:out></td>
	</tr>
</table>
