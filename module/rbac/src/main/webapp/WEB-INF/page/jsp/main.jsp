<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<!DOCTYPE html> 
<html lang="en"> 
<head>
	<jsp:include page="/WEB-INF/page/jsp/include.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/page/jsp/menu.jsp"></jsp:include>
</head> 
<body>
<div class="top">
	<div class="title">
		<img alt="" src='<s:url value="/static/css/images/title.png" />'>
	</div>
	<div class="userInfo">
		您好,用户：<span id="userName">admin</span>
		<span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<div style="float:right;">
		</div>
	</div>
</div>
<div>
	<div class="left">
		<ul class="menu" id="menu">
		</ul>
	</div>
	<div id="content">
	</div>
</div>
<script id="menuTemplate" type="text/template">
	{{#main}}
			{{#parent}}
			 	<li class="menuList" onclick="setMenuCurrent(event);" onmouseenter="showSubMenu(event);" onmouseleave="hideSubMenu(event);">
			 		<div>{{name}}</div>
			 		<ul style="display: none;" class="sub_menu">
			 			{{#children}}
				 			{{#url}}
				 				<li><div onclick="javascript:loadContent('{{url}}')">{{name}}</div></li>
				 			{{/url}}
							{{^url}}
								<li><div>{{name}}</div>,
							{{/url}}
			 			{{/children}}
			 		</ul>
			 	</li>
			{{/parent}}
			{{^parent}}
				<li class="menuList">
				{{#url}}
					<div onclick="javascript:loadContent('{{url}}');setMenuCurrent(event);">{{name}}</div>
				{{/url}}
				{{^url}}
					<div onclick="javascript:setMenuCurrent(event);">{{name}}</div>
				{{/url}}
				</li>
			{{/parent}}
	{{/main}}
	<li style="height:1px;border-top:1px solid gray"></li>
</script>

<div id="loadPanel"></div>
</body> 
</html>