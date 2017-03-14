<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

     <div class="button-group">
        <div class="button" onclick="department.sortByFlag(0)">
            <i class="iconfont">&#xe617;</i>
            <span class="button-label">上移</span>
        </div>
        <div class="button" onclick="department.sortByFlag(1)">
            <i class="iconfont">&#xe629;</i>
            <span class="button-label">下移</span>
        </div>
    </div>
	<table class="grid">
		<thead>
			<tr>
				<th class="grid-checkbox"></th>
				<th>序号</th>
				<th>部门名称</th>
				<th>上级部门</th>
				<th>所属组织机构</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="result">
				<tr onclick="department.selectCurrent(event);">
					<td class="grid-checkbox">
						<input type="radio" name="department_sort_radio" />
						<input type="hidden" name="department_sort_id" value="${result.id}"/>
					</td>
					<td>${result.orderCode}</td>
					<td>${result.name}</td>
					<td>${result.departmentName}</td>
					<td>${result.companyName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>