<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html> 
<html lang="zh_CN"> 
<head>
</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">资源管理->资源排序</a>
			</div>
        </div>
        <div id="inner-bd">
        	<jsp:include page="/WEB-INF/page/jsp/backend/tabbar.jsp">
        		<jsp:param name="page" value="resource"></jsp:param>
        	</jsp:include>
        	
        	<div class="kv-group-outer">
	        	<div class="kv-group clearfix">
					<div style="margin: 15px; ">
				        <div class="button current save" onclick="resource.sortSave();">
				            <i class="iconfont">&#xe62e;</i>
				            <span class="button-label">保存</span>
				        </div>
				        <div class="button current return" onclick="loadContent('<s:url value="/rbac/backend/resource/main" />')">
				            <i class="iconfont">&#xe60e;</i>
				            <span class="button-label">返回</span>
				        </div>
					</div>
				</div>
        	</div>
			
			<div class="button-group">
			   <div class="button" onclick="sortByFlag(0, 'resource_sort_radio')">
			       <i class="iconfont">&#xe617;</i>
			       <span class="button-label">上移</span>
			   </div>
			   <div class="button" onclick="sortByFlag(1, 'resource_sort_radio')">
			       <i class="iconfont">&#xe629;</i>
			       <span class="button-label">下移</span>
			    </div>
			</div>
			<table class="grid">
				<thead>
					<tr>
						<th class="grid-checkbox"></th>
						<th style="width: ${width}px;">名称</th>
						<th>类型</th>
						<th>编码</th>
						<th>图标</th>
						<th>状态</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${result}" var="result">
						<tr id="SORT_TR_${result.id}" pathId="SORT_TR${result.path}" level="${result.level}" sid="${result.id}" ondblclick="resource.detail('${result.id}');">
							<td class="grid-checkbox">
								<input type="radio" name="resource_sort_radio" />
								<input type="hidden" name="resouce_sort_level" value="${result.level}" />
								<input type="hidden" name="resource_sort_id" value="${result.id}"/>
								<input type="hidden" name="resource_path_id" id="PATHID_${result.id}" value=""/>
								<input type="hidden" name="resource_parent_id" id="PARENTID_${result.id}" value="0"/>
							</td>
							<td style="text-indent: ${result.indent}px;">
								<c:choose>
									<c:when test="${result.childrenNum == 0}">
										<img id="default_SORT_TR${result.path}" onclick="resource.openFolder(event)" src="/static/css/images/defaultLeaf.gif" />
										<img id="open_SORT_TR${result.path}" onclick="resource.closeFolder(event)" style="display:none;cursor:pointer;" src="/static/css/images/folderOpen.gif" />
										<img id="close_SORT_TR${result.path}" onclick="resource.openFolder(event)" style="display:none;cursor:pointer;" src="/static/css/images/folderClose.gif" />
									</c:when>
									<c:otherwise>
										<img onclick="resource.openFolder(event)" src="/static/css/images/defaultLeaf.gif" style="display:none;" />
										<img id="open_SORT_TR${result.path}" onclick="resource.closeFolder(event)" style="cursor:pointer;" src="/static/css/images/folderOpen.gif" />
										<img id="close_SORT_TR${result.path}" onclick="resource.openFolder(event)" style="display:none;cursor:pointer;" src="/static/css/images/folderClose.gif" />
									</c:otherwise>
								</c:choose>
								${result.name}
							</td>
							<td>${types[fn:trim(result.type)].value}</td>
							<td>${result.code}</td>
							<td>
								<c:if test="${not empty result.icon}">
									<img alt="未上传" src="${result.icon}"/>
								</c:if>
							</td>
							<td>${states[fn:trim(result.state)].value}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="footer"></div>
	</div>
</body>
</html>