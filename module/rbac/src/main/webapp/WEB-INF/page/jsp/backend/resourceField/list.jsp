<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<input type="hidden" value="${resourceId}" id="searchResourceId" />
<div class="button-group">
	<div class="button" onclick="resourceField.add('${resourceId}');">
	    <i class="iconfont">&#xe620;</i>
	    <span class="button-label">新增</span>
	</div>
</div>
<table class="grid">
	<thead>
		<tr>
			<th>字段值</th>
			<th>字段名</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${list}" var="result">
			<tr ondblclick="resource.detail('${result.id}');">
				<td>${result.code}</td>
				<td>${result.name}</td>
				<td>
					<a href="javascript:resourceField.update('${result.id}');">修改</a>
					<a href="javascript:resourceField.deleteSingle('${result.id}');">删除</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>