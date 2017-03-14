<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<!DOCTYPE html> 
<html lang="zh-CN"> 
<head>
	<jsp:include page="/WEB-INF/page/jsp/include.jsp"></jsp:include>
	<link href='<s:url value="/static/css/backend/organ_manger.css" />' rel="stylesheet">
	<link href='<s:url value="/static/js/lib/ztree/css/metroStyle/metroStyle.css" />' rel="stylesheet">

	<script type="text/javascript" src='<s:url value="/static/js/lib/ztree/js/jquery.ztree.core.min.js" />'></script>
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
		        <div class="kv-group clearfix">
		            <div class="kv-item kv-col-1">
		                <div class="item-lt">数据导入：</div>
		                <div class="item-rt">
		                    <a href="javascript:;" class="a-upload">
								<input type="file" name="" id="">选择文件
							</a>
							<span class="text-tip">未选择任何文件</span>
							<div class="button current">
								<span class="button-label">导入全部</span>
							</div>
							
							<div class="tip-area">
								<a href="javascript:;">内设机构人员信息关系年底关系模板下载</a>
							</div>
		                </div>
		            </div>
		            
		        </div>
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
		            <span class="button-label">组织机构排序</span>
		        </div>
		    </div>
			
			<table class="kv-table" style="margin-bottom: 5px;">
				<tbody>
					<tr>
						<td class="kv-label" style="width:20%;vertical-align: top;">
							<div id="ztree1" class="ztree"></div>
						</td>
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
    
    <jsp:include page="/WEB-INF/page/jsp/backend/company/company_js.jsp"></jsp:include>
    <jsp:include page="/WEB-INF/page/jsp/backend/company/company_tree_self.jsp">
    	<jsp:param value="company" name="module"/>
    </jsp:include>
	<jsp:include page="/WEB-INF/page/jsp/backend/company/select_js.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/page/jsp/footer.jsp"></jsp:include>
</body> 
</html>
