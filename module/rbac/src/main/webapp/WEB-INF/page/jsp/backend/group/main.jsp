<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html> 
<html lang="en"> 
<head>

</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">用户组管理</a>
			</div>
        </div>

        <div id="inner-bd">
        	<jsp:include page="/WEB-INF/page/jsp/backend/tabbar.jsp">
        		<jsp:param name="page" value="group"></jsp:param>
        	</jsp:include>

            <div class="kv-group-outer">
            	<form id="searchForm" action=<s:url value="/rbac/backend/person/search" />'>
			        <div class="kv-group clearfix">
				        <div class="kv-item-wrap" style="max-width: 900px;">
				        	<div class="kv-item kv-col-1">
				                <div class="item-lt">组名：</div>
				                <div class="item-rt">
				                	<input type="text" name="name" />
				                </div>
				            </div>
				            <div class="kv-item kv-col-2">
				                <div class="item-rt">
								    <div class="button-group">
								        <div class="button current search">
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
		        <div class="button current add">
		            <i class="iconfont">&#xe620;</i>
		            <span class="button-label">新增帐号</span>
		        </div>
		        <div class="button delete">
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
