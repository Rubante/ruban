<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html> 
<html lang="zh-CN"> 
<head>
	<link href='<s:url value="/static/js/lib/ztree/css/metroStyle/metroStyle.css" />' rel="stylesheet">
	
	<script type="text/javascript" src='<s:url value="/static/js/lib/ztree/js/jquery.ztree.core.min.js" />'></script>
	<script type="text/javascript" src='<s:url value="/static/biz/js/company/main.js" />'></script>
	<script type="text/javascript" src='<s:url value="/static/biz/js/company/select.js" />'></script>
</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">机构管理</a>
			</div>
        </div>

        <div id="inner-bd">
        	
            <div class="kv-group-outer">
            	<form id="searchForm" action=<s:url value="/rbac/backend/company/search" />' onsubmit="company.search(); return false;">
			        <div class="kv-group clearfix">
					        <div class="kv-item-wrap" style="max-width: 900px;">
					        	<div class="kv-item kv-col-1">
					                <div class="item-lt">类型：</div>
					                <div class="item-rt">
										<select id="type" name="type">
											<option value="">请选择</option>
											<c:forEach items="${types}" var="item">
												<option value="${item.code}">${item.value}</option>
											</c:forEach>
										</select>
					                </div>
					            </div>
					            <div class="kv-item kv-col-2">
					                <div class="item-lt">名称：</div>
					                <div class="item-rt">
					                   <input type="text" placeholder="名称" name="name" id="name" autocomplete="off"/>
					                   <input type="hidden" name="childDisplay" id="childDisplay" value="0" />
					                </div>
					            </div>
					            <div class="kv-item kv-col-3">
					                <div class="item-rt">
									    <div class="button-group">
									        <div class="button current search" onclick="company.search();">
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
		        <div class="button current add" onclick="company.add();">
		            <i class="iconfont">&#xe620;</i>
		            <span class="button-label">新增</span>
		        </div>
		        <div class="button delete" onclick="company.batchDelete();">
		            <i class="iconfont">&#xe609;</i>
		            <span class="button-label">删除</span>
		        </div>
				<div class="button sort" onclick="company.sort();">
		            <i class="iconfont">&#xe61a;</i>
		            <span class="button-label">组织机构排序</span>
		        </div>
		    </div>
			
			<table class="kv-table" style="margin-bottom: 5px;">
				<tbody>
					<tr>
						<td id="companyListTd" class="kv-content" style="padding: 10px;vertical-align: top;">
							<jsp:include page="/WEB-INF/page/jsp/backend/company/list.jsp"></jsp:include>
						</td>
					</tr>
				</tbody>
			</table>
        </div>

        <div id="inner-ft">
            
        </div>
    </div>

	<jsp:include page="/WEB-INF/page/jsp/footer.jsp"></jsp:include>
</body> 
</html>
