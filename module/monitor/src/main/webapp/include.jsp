<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta http-equiv="pragram" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 

<script type="text/javascript" src='<s:url value="/static/js/jquery-3.2.1.min.js" />' ></script>
<script type="text/javascript" src='<s:url value="/static/layer/layer.js" />' ></script>
<script type="text/javascript" src='<s:url value="/static/js/mustache.min.js" />' ></script>
<script type="text/javascript" src='<s:url value="/static/jedate/jquery.jedate.min.js" />' ></script>
<script type="text/javascript" src='<s:url value="/static/bootstrap/js/bootstrap.min.js" />' ></script>
<script type="text/javascript" src='<s:url value="/static/switch/js/bootstrap-switch.min.js" />' ></script>
<script type="text/javascript" src='<s:url value="/static/js/common.js" />'></script>
<script type="text/javascript" src='<s:url value="/static/js/echarts.min.js" />'></script>


<link rel="stylesheet" href='<s:url value="/static/css/reset.css" />' />
<link rel="stylesheet" href='<s:url value="/static/bootstrap/css/bootstrap.min.css" />' />
<link rel="stylesheet" href='<s:url value="/static/jedate/skin/jedate.css" />' />
<link rel="stylesheet" href='<s:url value="/static/switch/css/bootstrap3/bootstrap-switch.css" />' />

<title>鲁班监控平台</title>

<style>
body {
	background-color: #f8f9fb;
}
.panel-group {
	max-height: 770px;
	overflow: auto;
}

.leftMenu {
	margin: 10px;
	margin-top: 5px;
	background-color: #f8f9fb;
}

.leftMenu .panel-heading {
	font-size: 14px;
	padding-left: 20px;
	height: 36px;
	line-height: 36px;
	position: relative;
	cursor: pointer;
} /*转成手形图标*/
.leftMenu .panel-heading span {
	position: absolute;
	right: 10px;
	top: 12px;
}

.leftMenu .menu-item-left {
	padding: 7px;
	border: 1px solid transparent;
	border-radius: 6px;
	height: 36px;
	cursor: pointer;
	margin: 0px;
}

.leftMenu .menu-item-left:hover {
	background: #1aad19;
	border: 1px solid #1E90FF;
	color : white;
}
.menu-item-list{
	padding-left: 10px;
}

table {
	border: 1px solid #ddd;
}
table thead tr {
	background: #f5f5f5;
}
table th {
	border: 1px solid #ddd;
}
table td {
	border: 1px solid #ddd;
}
.col-lg-1, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-md-1, .col-md-10, .col-md-11, .col-md-12, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-sm-1, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-xs-1, .col-xs-10, .col-xs-11, .col-xs-12, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9 {
    padding-right: 5px;
    padding-left: 5px;
}
.layui-layer-padding {
	line-height: 20px;
	background-color: #FF6347
}
@media(min-width:1200px){  
	.pc {
	}
	.phone {
		display:none;
	}
}
@media(max-width:480px){  
	.pc {
		display:none;
	}
	.phone {

	}
} 
</style>