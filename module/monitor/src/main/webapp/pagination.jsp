<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="float:right">
	<ul class="pagination" style="margin-top: -20px; margin-bottom: 15px;">
		<li><a href="#">&laquo;</a></li>
		<c:forEach items="1,2,3,4,5,6,7,8,9,10" var="index">
			
			<li <c:if test="${index == page % 10}">class="active"</c:if>><a href="#"><c:out value="${index + page - 1}"></c:out></a></li>
		</c:forEach>
	    
	    <li><a href="#">&raquo;</a></li>
	</ul>
</div>