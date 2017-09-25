<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<table class="grid">
	<thead>
		<tr>
			<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'resource_checkbox')" style="margin-right:9px;"></th>
			<th style="width: ${width}px;">名称</th>
			<th>类型</th>
			<th>编码</th>
			<th>图标</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${result}" var="result">
			<tr id="TR_${result.id}" pid="TR${result.path}" ondblclick="resource.detail('${result.id}');">
				<td class="grid-checkbox">
					<c:choose>
						<c:when test="${result.state == 0 && result.childrenNum == 0}">
							<input type="checkbox" name="resource_checkbox" />
						</c:when>
						<c:otherwise>
							<input type="checkbox" disabled="disabled" />
							<input type="hidden" name="resource_checkbox" />
						</c:otherwise>
					</c:choose>
					<input type="hidden" name="resource_id" value="${result.id}"/>
				</td>
				<td style="text-indent: ${result.indent}px;">
					<c:choose>
						<c:when test="${result.childrenNum == 0}">
							<img onclick="resource.openFolder(event)" src="/static/css/images/defaultLeaf.gif" />
						</c:when>
						<c:otherwise>
							<img id="open_TR${result.path}" onclick="resource.closeFolder(event)" style="cursor:pointer;" src="/static/css/images/folderOpen.gif" />
							<img id="close_TR${result.path}" onclick="resource.openFolder(event)" style="display:none;cursor:pointer;" src="/static/css/images/folderClose.gif" />
						</c:otherwise>
					</c:choose>
					${result.name}
				</td>
				<td>${typeMap[sex].value}</td>
				<td>${result.code}</td>
				<td>
					<c:if test="${not empty result.icon}">
						<img alt="未上传" src="${result.icon}"/>
					</c:if>
				</td>
				<td>${stateMap[fn:trim(result.state)].value}</td>
				<td>
					<a href="javascript:resource.detail('${result.id}');">查看</a>
					<a href="javascript:resource.update('${result.id}');">修改</a>
					<c:choose>
						<c:when test="${result.state == 1}">
							<a href="javascript:resource.unable('${result.id}');" style="color:red">禁用</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:resource.enable('${result.id}');" style="color:green">启用</a>
						</c:otherwise>
					</c:choose>
					<c:if test="${result.state == 0 && result.childrenNum == 0}">
						<a href="javascript:resource.deleteSingle('${result.id}');">删除</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="footer"></div>