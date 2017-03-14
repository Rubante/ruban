<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<div class="tab-wraper">
	<ul class="tab">
    	<li <c:if test="${param.page=='person'}">class="current"</c:if> ><a href='<s:url value="/rbac/backend/person/main" />'>人员管理</a></li>
		<li <c:if test="${param.page=='account'}">class="current"</c:if> ><a href='<s:url value="/rbac/backend/account/main" />'>帐号管理</a></li>
		<li <c:if test="${param.page=='group'}">class="current"</c:if> ><a href='<s:url value="/rbac/backend/group/main" />'>用户组管理</a></li>
		<li <c:if test="${param.page=='role'}">class="current"</c:if> ><a href='<s:url value="/rbac/backend/role/main" />'>角色管理</a></li>
		<li <c:if test="${param.page=='permission'}">class="current"</c:if> ><a href='<s:url value="/rbac/backend/permission/main" />'>权限管理</a></li>
		<li <c:if test="${param.page=='resource'}">class="current"</c:if> ><a href='<s:url value="/rbac/backend/resource/main" />'>资源管理</a></li>
		<li <c:if test="${param.page=='grant'}">class="current"</c:if> ><a href='<s:url value="/rbac/backend/grant/main" />'>授权管理</a></li>
    </ul>
</div>