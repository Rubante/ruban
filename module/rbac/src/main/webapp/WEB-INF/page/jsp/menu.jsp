<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<script type="text/javascript">
	//菜单
	var menuJson = {
		"main" : [
				{
					"name" : "组织机构管理",
					"parent" : true,
					"children" : [ {
						"name" : "组织管理",
						"url" : '<s:url value="/rbac/backend/company/main" />',
					}, {
						"name" : "部门管理",
						"url" : '<s:url value="/rbac/backend/department/main" />',
					} ]
				},
				{
					"name" : "人员管理",
					"url" : '<s:url value="/rbac/backend/person/main" />'
				},
				{
					"name" : "电梯管理",
					"parent" : true,
					"children" : [ {
						"name" : "梯管理",
						"url" : ""
					}, {
						"name" : "梯控制器管理",
						"url" : ""
					} ]
				},
				{
					"name" : "权限模板管理",
					"url" : ""
				}, {
					"name" : "系统管理",
					"parent" : true,
					"children" : [ {
						"name" : "角色管理",
						"url" : ""
					}, {
						"name" : "操作员管理",
						"url" : ""
					} ]
				}]
	};

	$(document).ready(function() {
		jQuery.support.cors = true
		// 初始化菜单
		var template = $("#menuTemplate").html();
		var html = Mustache.to_html(template, menuJson);
		$("#menu").append(html);
		
		// 加载分页模板
		$("#commonTemplate").load("page.html");
	});
</script>