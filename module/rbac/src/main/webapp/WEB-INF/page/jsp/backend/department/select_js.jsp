<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<script type="text/javascript">
(function(){
	if(!window['departmentDownTree']){
		window['departmentDownTree'] = {};
	}
	
	var departmentDownTree = window['departmentDownTree'];
	
	// 隐藏所有父级选择面板
	$(document).click(function(){
		$("[id$='departmentDownTreeDiv']").each(function(){
			if($(this).is(":visible")){
				$(this).hide();	
			}
		});
	});
	
	// 按钮点击选择上级部门
	departmentDownTree.selectByInput = function(event){
		var id = $(event.target).attr("rel");
		$('#'+id+"Name").click();
		event.stopPropagation();
	};
	
	// 选择上级机构
	departmentDownTree.selectParent = function(event) {

		// 选择部门，必须先选择公司
		var companyId = $("[id$='companyId'").val();
		if(companyId == undefined || companyId == ""){
			parent.layer.alert("请先选择所属组织机构");
			$("#companyId").focus();
			return;
		}
		
		// 设置字体颜色
		function setFontCss(treeId, treeNode) {
			if(treeNode.isParent) {
				return {
					color : "red"
				};
			} else {
				return {
					color : "blue"
				};
			}
		};

		var targetId = $(event.target).attr("rel");
		
		// 点击设置所选
		function zTreeOnClick(event, treeId, treeNode) {
			$("#" + targetId + "Id").val(treeNode.id);
			$("#" + targetId + "Name").val(treeNode.name);
			$("#" + targetId + "DownTreeDiv").hide();
			$("#" + targetId + "Name").change();
		};

		
		$("#" + targetId + "DownTreeDiv").toggle();

		var setting = {
			check : {
				enable : false
			},
			callback : {
				onClick : zTreeOnClick
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				fontCss : setFontCss
			}
		};
		
    	// 成功回调函数
    	var success = function(data){
   			// 动态获取当前所选组织机构编码
   			var zNodes = eval(data);
   			$.fn.zTree.init($("#" + targetId + "DownTree"), setting, zNodes);
		};
		
		var searchData = {"companyId" : companyId};
		ajaxText('<s:url value="/rbac/backend/department/getDptTree" />',searchData,success);
		
		event.stopPropagation();
	};
})();
</script>