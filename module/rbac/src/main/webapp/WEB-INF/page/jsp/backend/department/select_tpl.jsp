<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="white-space: nowrap;">
	<input type="text" name="departmentName" onclick="departmentDownTree.selectParent(event)" value="${result.departmentName}" style="width:150px;" rel="department" id="departmentName" autocomplete="off" />
	<input type="hidden" name="departmentId" id="departmentId" value="${result.departmentId}" />
	<div id="departmentDownTreeDiv" style="display: none; float: left; margin-left: -173px; margin-top: 35px; width: 160px; height: 0px;">
		<div style="background-color: white; position: absolute; width: 160px; min-height: 40px; max-height: 220px; overflow: auto; border: 1px solid red;">
			<div id="departmentDownTree" class="ztree"></div>
		</div>
	</div>
	<div class="button current">
		<span class="button-label" rel="department" onclick="departmentDownTree.selectByInput(event)">选择</span>
	</div>
</div>