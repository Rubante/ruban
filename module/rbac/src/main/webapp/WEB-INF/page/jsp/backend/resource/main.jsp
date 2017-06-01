<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html> 
<html lang="zh_CN"> 
<head>
	<link href='<s:url value="/static/js/lib/ztree/css/metroStyle/metroStyle.css" />' rel="stylesheet">

	<script type="text/javascript" src='<s:url value="/static/js/lib/ztree/js/jquery.ztree.core.min.js" />'></script>
	
	<script type="text/javascript" src='<s:url value="/static/biz/js/resource/main.js" />'></script>
	<script type="text/javascript" src='<s:url value="/static/biz/js/resource/select.js" />'></script>
</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">资源管理</a>
			</div>
        </div>

        <div id="inner-bd">
        	<jsp:include page="/WEB-INF/page/jsp/backend/tabbar.jsp">
        		<jsp:param name="page" value="resource"></jsp:param>
        	</jsp:include>

            <div class="kv-group-outer">
            	<form id="searchForm" action=<s:url value="/rbac/backend/resource/search" />'>
			        <div class="kv-group clearfix">
				        <div class="kv-item-wrap" style="max-width: 900px;">
				        	<div class="kv-item">
				                <div class="item-lt">类型：</div>
				                <div class="item-rt">
									<select name="type">
										<option value="">请选择</option>
										<c:forEach items="${types}" var="type">
											<option value="${type.code}">${type.value}</option>
										</c:forEach>
									</select>
				                </div>
				            </div>
				            <div class="kv-item kv-col-2">
				                <div class="item-lt">名称：</div>
				                <div class="item-rt">
				                	<input type="text" name="name" />
				                </div>
				            </div>
				            <div class="kv-item kv-col-4">
				                <div class="item-rt">
								    <div class="button-group">
								        <div class="button current search" onclick="resource.search();">
								            <i class="iconfont">&#xe625;</i>
								            <span class="button-label">查询</span>
								        </div>
								    </div>
				                </div>
				            </div>
				        </div>
			        </div>
		    	</form>
		    </div>

            <div class="button-group">
		        <div class="button" onclick="resource.add();">
		            <i class="iconfont">&#xe620;</i>
		            <span class="button-label">新增</span>
		        </div>
		        <div class="button delete" onclick="resource.batchDelete();">
		            <i class="iconfont">&#xe609;</i>
		            <span class="button-label">删除</span>
		        </div>
				<div class="button sort" onclick="resource.sort();">
		            <i class="iconfont">&#xe61a;</i>
		            <span class="button-label">排序</span>
		        </div>
		    </div>

			<div id="resourceListTd">
				<jsp:include page="/WEB-INF/page/jsp/backend/resource/list.jsp"></jsp:include>
			</div>

        </div>

        <div id="inner-ft">
            
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/page/jsp/footer.jsp"></jsp:include>
</body>
</html>
