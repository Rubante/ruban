<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<!DOCTYPE html> 
<html lang="en"> 
<head>
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
            	<div class="kv-group clearfix">
			        <div class="button current save" onclick="rubanRole.grantSave();">
			            <i class="iconfont">&#xe62e;</i>
			            <span class="button-label">保存</span>
			        </div>
			        <div class="button current return" onclick="loadContent('<s:url value="/rbac/backend/role/main" />')">
			            <i class="iconfont">&#xe60e;</i>
			            <span class="button-label">返回</span>
			        </div>
            	</div>
            </div>
            <div>
				<form id="role_grant_form" action='<s:url value="/rbac/role/grantSave" />' method="post">
					<div class="add-manage">
						<table class="kv-table">
							<tbody>
								<tr>
									<td class="kv-label">
										名称：
									</td>
									<td class="kv-content">
										${result.name}
										<input type="hidden" id="roleId" name="id" value="${result.id}">
									</td>
									<td class="kv-label">
										拥有该角色的用户数：
									</td>
									<td class="kv-content">
										${result.count}
									</td>
								</tr>
								<tr>
									<td class="kv-label">
										继承的角色：
									</td>
									<td class="kv-content">
										
									</td>
									<td class="kv-label">
										互斥的角色：
									</td>
									<td class="kv-content">
									</td>
								</tr>
								<tr>
									<td class="kv-label">
										可授权的资源
									</td>
									<td class="kv-content" colspan="3">
										<div class="textarea-wrap">
											<div id="resourceTree"></div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="footer"></div>
</body>
</html>