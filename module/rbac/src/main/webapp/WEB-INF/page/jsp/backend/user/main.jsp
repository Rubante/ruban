<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html> 
<html lang="en"> 
<head>
	<script type="text/javascript" src='<s:url value="/static/biz/js/user/main.js" />'></script>
</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">用户管理</a>
			</div>
        </div>

        <div id="inner-bd">
        	<jsp:include page="/WEB-INF/page/jsp/backend/tabbar.jsp">
        		<jsp:param name="page" value="user"></jsp:param>
        	</jsp:include>

            <div class="kv-group-outer">
            	<form id="searchForm" action=<s:url value="/rbac/backend/user/search" />'>
			        <div class="kv-group clearfix">
				        <div class="kv-item-wrap" style="max-width: 900px;">
				        	<div class="kv-item kv-col-1">
				                <div class="item-lt">部门内设机构：</div>
				                <div class="item-rt">
				                    <select name="companyId">
				                        <option value="">不限</option>
				                    </select>
				                </div>
				            </div>
				            <div class="kv-item kv-col-2">
				                <div class="item-lt">人员名称：</div>
				                <div class="item-rt">
				                   <input type="text" onClick="WdatePicker({crossFrame:false})" placeholder="人员名称" name="name" />
				                </div>
				            </div>
				            <div class="kv-item kv-col-3">
				                <div class="item-lt">用户名：</div>
				                <div class="item-rt">
				                    <input type="text" placeholder="用户名" name="username" />
				                </div>
				            </div>
				            <div class="kv-item kv-col-1">
				                <div class="item-rt">
								    <div class="button-group">
								        <div class="button current search" onclick="user.search();">
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
		        <div class="button current add" onclick="user.add(event)">
		            <i class="iconfont">&#xe620;</i>
		            <span class="button-label">新增用户</span>
		        </div>
		        <div class="button delete" onclick="user.batchDelete(event)">
		            <i class="iconfont">&#xe609;</i>
		            <span class="button-label">删除</span>
		        </div>
		    </div>
			
			<div id="userListTd">
				<jsp:include page="/WEB-INF/page/jsp/backend/user/list.jsp"></jsp:include>
			</div>
        </div>

        <div id="inner-ft">
            
        </div>
    </div>

    <jsp:include page="/WEB-INF/page/jsp/footer.jsp"></jsp:include>
</body>
</html>
