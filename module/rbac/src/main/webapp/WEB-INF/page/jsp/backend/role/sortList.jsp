<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="button-group">
   <div class="button" onclick="sortByFlag('role_sort_radio', 0)">
       <i class="iconfont">&#xe617;</i>
       <span class="button-label">上移</span>
   </div>
   <div class="button" onclick="sortByFlag('role_sort_radio', 1)">
       <i class="iconfont">&#xe629;</i>
       <span class="button-label">下移</span>
    </div>
</div>
<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox"></th>
			<th>帐号</th>
			<th>所属人员</th>
			<th>最后登录时间</th>
			<th>状态</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${resultInfo.list}" var="result">
			<tr ondblclick="rubanRole.detail('${result.id}');">
				<td class="grid-checkbox">
					<input type="checkbox" name="role_checkbox" />
					<input type="hidden" name="role_id" value="${result.id}"/>
				</td>
				<td>${result.accountNo}</td>
				<td>${result.personName}</td>
				<td>${result.lastLoginTime}</td>
				<td>${result.state}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<jsp:include page="/WEB-INF/page/jsp/pagination.jsp">
	<jsp:param value="rubanRole.search" name="action"/>
</jsp:include>