<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html> 
<html lang="en"> 
<head>
	<jsp:include page="/WEB-INF/page/jsp/include.jsp"></jsp:include>
	
	<link href='<s:url value="/static/css/backend/person_manger.css" />' rel="stylesheet">
	<link href='<s:url value="/static/js/lib/ztree/css/metroStyle/metroStyle.css" />' rel="stylesheet">

	<script type="text/javascript" src='<s:url value="/static/js/lib/ztree/js/jquery.ztree.core.min.js" />'></script>
	
	<script type="text/javascript" src='<s:url value="/static/js/lib/My97DatePicker/WdatePicker.js" />'></script>
</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">人员管理</a>
			</div>
        </div>

        <div id="inner-bd">
        	<div class="tab-wraper">
        		<ul class="tab">
		           	<li class="current"><a href='<s:url value="/rbac/backend/person/main" />'>人员管理</a></li>
		           	<li><a href='<s:url value="/rbac/backend/account/main" />'>帐号管理</a></li>
		           	<li><a href='<s:url value="/rbac/backend/account/main" />'>角色管理</a></li>
		           	<li><a href='<s:url value="/rbac/backend/account/main" />'>权限管理</a></li>
		           	<li><a href='<s:url value="/rbac/backend/account/main" />'>帐号权限设置</a></li>
	            </ul>
        	</div>

            <div class="kv-group-outer">
            	<form id="searchForm" action=<s:url value="/rbac/backend/person/search" />'>
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
				                <div class="item-lt">岗位：</div>
				                <div class="item-rt">
				                	<input type="text" name="jobId" />
				                </div>
				            </div>
				            <div class="kv-item kv-col-3">
				                <div class="item-lt">职务：</div>
				                <div class="item-rt">
				                	<input type="text" name="titleId" />
				                </div>
				            </div>
				            <div class="kv-item">
				                <div class="item-lt">性别：</div>
				                <div class="item-rt">
				                   <select name="sex">
				                        <option value="0">不限</option>
				                        <option value="1">女</option>
				                        <option value="2">男</option>
				                    </select>
				                </div>
				            </div>
	
				            <div class="kv-item kv-col-1">
				                <div class="item-lt">人员名称：</div>
				                <div class="item-rt">
				                   <input type="text" onClick="WdatePicker({crossFrame:false})" placeholder="填写人员名称" name="name">
				                </div>
				            </div>
	
				            <div class="kv-item kv-col-2">
				                <div class="item-lt">职务：</div>
				                <div class="item-rt">
				                    <input type="text" placeholder="职务" name="titleName">
				                </div>
				            </div>
				            <div class="kv-item kv-col-3">
				                <div class="item-lt">登录账号：</div>
				                <div class="item-rt">
				                    <input type="text" placeholder="职务" name="">
				                </div>
				            </div>
				            <div class="kv-item kv-col-1">
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
		            <span class="button-label">新增</span>
		        </div>
		        <div class="button delete">
		            <i class="iconfont">&#xe609;</i>
		            <span class="button-label">删除</span>
		        </div>
				<div class="button sort">
		            <i class="iconfont">&#xe61a;</i>
		            <span class="button-label">人员排序</span>
		        </div>
		    </div>
			
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label" style="width:230px;vertical-align: top;">
							<div id="dptTree" class="ztree"></div>
						</td>
						<td id="personListTd" class="kv-content" valign="top" style="padding: 10px;width:auto;vertical-align: top;">
							<jsp:include page="/WEB-INF/page/jsp/backend/person/list.jsp"></jsp:include>
						</td>
					</tr>
				</tbody>
				
			</table>
			
			
        </div>

        <div id="inner-ft">
            
        </div>
    </div>
    
    <jsp:include page="/WEB-INF/page/jsp/backend/person/person_js.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/page/jsp/backend/department/department_tree_js.jsp">
    	<jsp:param value="person" name="module"/>
    </jsp:include>
	<jsp:include page="/WEB-INF/page/jsp/backend/company/select_js.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/page/jsp/backend/department/select_js.jsp"></jsp:include>
</body>
</html>
