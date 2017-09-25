<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<meta http-equiv="pragram" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 

<script type="text/javascript" src='<s:url value="/static/js/jquery-1.12.0.min.js" />'></script>
<script type="text/javascript" src='<s:url value="/static/js/lib/layer/layer.js" />'></script>
<script type="text/javascript" src='<s:url value="/static/js/mustache.min.js" />'></script>
<script type="text/javascript" src='<s:url value="/static/js/jquery.form.js" />'></script>

<script type="text/javascript" src='<s:url value="/static/js/common.js" />'></script>

<script type="text/javascript" src='<s:url value="/static/js/ruban.page.js" />'></script>
<script type="text/javascript" src='<s:url value="/static/js/ruban.sort.js" />'></script>
<script type="text/javascript" src='<s:url value="/static/js/ruban.util.js" />'></script>
<script type="text/javascript" src='<s:url value="/static/js/ruban.validate.js" />'></script>

<script type="text/javascript" src='<s:url value="/static/js/pagination.js" />'></script>
<script type="text/javascript" src='<s:url value="/static/js/frame.js" />'></script>

<script type="text/javascript" src='<s:url value="/static/js/lib/jedate/jquery.jedate.min.js" />'></script>

<link href='<s:url value="/static/css/reset.css" />' rel="stylesheet">
<link href='<s:url value="/static/css/base.css" />' rel="stylesheet">
<link href='<s:url value="/static/css/style.css" />' rel="stylesheet">
<link href='<s:url value="/static/css/pagination.css" />' rel="stylesheet">
<link href='<s:url value="/static/css/validate.css" />' rel="stylesheet">
<link href='<s:url value="/static/css/platform/platform.css" />' rel="stylesheet">

<link href='<s:url value="/static/js/lib/jedate/skin/jedate.css" />' rel="stylesheet">

<script type="text/javascript">
	bizURL = {
		company : '<s:url value="/rbac/backend/company/" />',
		companyTree : '<s:url value="/rbac/backend/company/getTree" />',
		department : '<s:url value="/rbac/backend/department/" />',
		dptTree : '<s:url value="/rbac/backend/department/getDptTree" />',
		rubanRole : '<s:url value="/rbac/backend/role/" />',
		resourceTree : '<s:url value="/rbac/backend/resource/getTree" />',
		resource : '<s:url value="/rbac/backend/resource/" />',
		user : '<s:url value="/rbac/backend/user/" />',
		person : '<s:url value="/rbac/backend/person/" />'
	};
</script>