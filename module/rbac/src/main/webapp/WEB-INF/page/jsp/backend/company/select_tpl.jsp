<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="white-space: nowrap;">
	<input type="text" name="companyName" onclick="companyDownTree.select(event)" value="${result.companyName}" style="width:150px;" rel="company" id="companyName" autocomplete="off" <c:if test="${result.companyId != 0}">check="notNull"</c:if>  />
	<input type="hidden" name="companyId" id="companyId" value="${result.companyId}" />
	<div id="companyDownTreeDiv" style="display: none; float: left; margin-left: -175px; margin-top: 35px; width: 160px; height: 0px;padding-left: 2px; ">
		<div style="background-color: white; position: absolute; width: 160px; min-height: 40px; max-height: 220px; overflow: auto; border: 1px solid red;">
			<div id="companyDownTree" class="ztree"></div>
		</div>
	</div>
	<div class="button current">
		<span class="button-label" rel="company" onclick="companyDownTree.clickInput(event)">选择</span>
	</div>
</div>