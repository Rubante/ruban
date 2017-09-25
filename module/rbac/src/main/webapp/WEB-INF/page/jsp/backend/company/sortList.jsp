<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="button-group">
   <div class="button" onclick="sortByFlag(0, 'company_sort_radio')">
       <i class="iconfont">&#xe617;</i>
       <span class="button-label">上移</span>
   </div>
   <div class="button" onclick="sortByFlag(1, 'company_sort_radio')">
       <i class="iconfont">&#xe629;</i>
       <span class="button-label">下移</span>
    </div>
</div>
<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'company_sort_checkbox')"></th>
			<th>序号</th>
			<th>名称</th>
			<th>类型</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="result">
			<tr onclick="company.selectCurrent(event);">
				<td class="grid-checkbox">
					<input type="radio" name="company_sort_radio" />
					<input type="hidden" name="company_sort_id" value="${result.id}"/>
				</td>
				<td>${result.orderCode}</td>
				<td>${result.name}</td>
				<td>${dictMap[result.type].value}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>