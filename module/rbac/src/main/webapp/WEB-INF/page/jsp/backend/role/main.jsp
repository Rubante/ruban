<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html> 
<html lang="en"> 
<head>
	<link href='<s:url value="/static/js/lib/ztree/css/metroStyle/metroStyle.css" />' rel="stylesheet">
	
	<script type="text/javascript" src='<s:url value="/static/biz/js/role/main.js" />'></script>
	<script type="text/javascript" src='<s:url value="/static/biz/js/company/select.js" />'></script>
</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">角色管理</a>
			</div>
        </div>

        <div id="inner-bd">
        	<jsp:include page="/WEB-INF/page/jsp/backend/tabbar.jsp">
        		<jsp:param name="page" value="role"></jsp:param>
        	</jsp:include>

            <div class="kv-group-outer">
            	<form id="searchForm" action=<s:url value="/rbac/backend/role/search" />'>
			        <div class="kv-group clearfix">
				        <div class="kv-item-wrap" style="max-width: 900px;">
				        	<div class="kv-item kv-col-1">
				                <div class="item-lt">角色名：</div>
				                <div class="item-rt">
				                	<input type="text" name="name" />
				                </div>
				            </div>
				            <div class="kv-item kv-col-2">
				                <div class="item-lt">是否可委托：</div>
				                <div class="item-rt">
				                	<input type="text" name="delegated" />
				                </div>
				            </div>
				            <div class="kv-item kv-col-1">
				                <div class="item-rt">
								    <div class="button-group">
								        <div class="button current search" onclick="rubanRole.search();">
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
		        <div class="button current add" onclick="rubanRole.add(event)">
		            <i class="iconfont">&#xe620;</i>
		            <span class="button-label">新增角色</span>
		        </div>
		        <div class="button delete"  onclick="rubanRole.delete(event)">
		            <i class="iconfont">&#xe609;</i>
		            <span class="button-label">删除</span>
		        </div>
		    </div>
			
			<div id="roleListTd">
				<jsp:include page="/WEB-INF/page/jsp/backend/role/list.jsp"></jsp:include>
			</div>
        </div>

        <div id="inner-ft">
            
        </div>
    </div>
    <jsp:include page="/WEB-INF/page/jsp/footer.jsp"></jsp:include>
</body>
</html>
